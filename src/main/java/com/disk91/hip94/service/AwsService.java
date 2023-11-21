package com.disk91.hip94.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.disk91.hip94.EtlConfig;
import com.disk91.hip94.Hip94Application;
import com.disk91.hip94.data.object.Param;
import com.disk91.hip94.data.repository.ParamRepository;
import com.helium.grpc.lora_poc_v1;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.zip.GZIPInputStream;

@Service
public class AwsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //static final String IOTPOC_FIRST_OBJECT = "foundation-iot-verified-rewards/iot_poc.1674766530034.gz";
    static final String IOTPOC_FIRST_OBJECT = "foundation-iot-verified-rewards/iot_poc.1693526306914.gz";


    @Autowired
    protected EtlConfig etlConfig;

    @Autowired
    protected ParamRepository paramRepository;

    protected AWSCredentials awsCredentials = null;
    protected AmazonS3 s3Client = null;

    Param iotPocFile = null;


    @PostConstruct
    private void initAwsService() {

        if ( etlConfig.getAwsAccessKey().length() < 2 || etlConfig.getAwsSecretKey().length() < 2) {
            log.error("========= CONFIG ERROR ========");
            log.error(">> You need to setup your AWS credentials");
            Hip94Application.requestingExitForStartupFailure = true;
            return;
        }

        iotPocFile = paramRepository.findOneParamByParamName("aws_last_iotpoc_sync");
        if ( iotPocFile == null ) {
            iotPocFile = new Param();
            iotPocFile.setParamName("aws_last_iotpoc_sync");
            iotPocFile.setStringValue(IOTPOC_FIRST_OBJECT);
        } else if ( etlConfig.isAwsForceResync() ) {
            iotPocFile.setStringValue(IOTPOC_FIRST_OBJECT);
        }

        this.awsCredentials = new BasicAWSCredentials(
                etlConfig.getAwsAccessKey(),
                etlConfig.getAwsSecretKey()
        );

        if ( awsCredentials != null ) {
            this.s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withCredentials(new AWSCredentialsProvider() {
                        @Override
                        public AWSCredentials getCredentials() {
                            return awsCredentials;
                        }

                        @Override
                        public void refresh() {
                            awsCredentials = new BasicAWSCredentials(
                                    etlConfig.getAwsAccessKey(),
                                    etlConfig.getAwsSecretKey()
                            );
                        }
                    })
                    .withRegion(Regions.US_WEST_2)
                    .build();
            if ( this.s3Client != null ) {
                this.readyToSync = true;
                this.serviceEnable = true;
                this.runningJobs = 0;
                //lor.putCustomRequestHeader("x-amz-request-payer", "requester");
            } else {
                log.error("Impossible to connect to AWS, no sync will be possible");
            }
        }

    }


    protected int runningJobs;
    protected boolean serviceEnable = false; // false to stop the services

    public void stopService() {
        this.serviceEnable = false;
    }
    public void restartService() { this.serviceEnable = true; }
    public boolean hasStopped() {
        return (this.serviceEnable == false && this.runningJobs == 0);
    }


    private int getFileType(String fileName) {
        if ( fileName.startsWith("iot_beacon_ingest_report") ) {
            return 1;
        } else if ( fileName.startsWith("iot_witness_ingest_report") ) {
            return 2;
        } else if ( fileName.startsWith("iot_poc") ) {
            return 3;
        } else if ( fileName.startsWith("iot_reward") ) {
            return 4;
        } else if ( fileName.startsWith("gateway_reward") ) {
           // log.info("Found gateway_reward file "+fileName);
        } else if ( fileName.startsWith("solana-migration-bad-data") ) {
           // log.info("Found solana_migration bad data file "+fileName);
        } else if ( fileName.startsWith("reward_manifest") ) {
            // log.info("Found reward manifest file "+fileName);
        } else if ( fileName.startsWith("non_rewardable") ) {
            // log.info("Found non rewardable packets "+fileName);
        } else {
            log.warn("Unknown type of file discovered "+fileName);
        }
        return 0;
    }

    private boolean readyToSync = false;


    // ---------------------------------------
    // IoT Poc
    // ---------------------------------------

    @Autowired
    protected WitnessService witnessService;

    protected boolean pocThreadEnable = true;
    protected volatile boolean firstFile = true;


    private long currentHour = 0;

    @Scheduled(fixedDelay = 60_000, initialDelay = 3_000)
    protected void AwsIoTPocSync() {
        if ( ! readyToSync || !serviceEnable ) return;
        log.info("Running AwsIoTPoc Sync");

        synchronized (this) {
            this.runningJobs++;
        }
        long start = Now.NowUtcMs();
        long lastLog = start;
        this.pocThreadEnable = true;
        int retry = 0;
        long totalBeacon = 0;


        try {
            final ListObjectsV2Request lor = new ListObjectsV2Request();
            lor.setBucketName(etlConfig.getAwsBucketName());
            lor.setPrefix("foundation-iot-verified-rewards");
            lor.setStartAfter(iotPocFile.getStringValue());
            lor.setRequesterPays(true);
            ListObjectsV2Result list;
            long totalObject = 0;
            long totalSize = 0;
            long totalWitness = 0;
            do {
                list = this.s3Client.listObjectsV2(lor);
                List<S3ObjectSummary> objects = list.getObjectSummaries();
                for (S3ObjectSummary object : objects) {

                    totalObject++;
                    totalSize += object.getSize();
                    if (object.getSize() == 0) continue;
                    long fileStart = Now.NowUtcMs();

                    // Identify the type of objects
                    //  iot_poc => valid poc
                    if (!object.getKey().contains(".gz")) continue; // not a file
                    String fileName = object.getKey().split("/")[1];
                    int fileType = getFileType(fileName);
                    long fileDate = Long.parseLong(object.getKey().split("\\.")[1]);
                    if (fileType != 3) continue;
                    if (fileDate / 1000 < etlConfig.getIotpocHistoryStartDate()) {
                        iotPocFile.setStringValue(object.getKey());
                        paramRepository.save(iotPocFile);
                        continue;
                    }
                    if (fileDate / 1000 > etlConfig.getIotpocHistoryStopDate()) {
                        log.info("Stop date reached");
                        this.serviceEnable = false; // avoid to restart
                        return;
                    }

                    // prometheus update for getting time to process 1 hour as indicator
                    // round file date to 1 hour
                    long fileHour = fileDate / 3_600_000;
                    fileHour = fileHour * 3_600_000;
                    if (currentHour == 0) {
                        currentHour = fileHour;
                    }
                    if (currentHour != fileHour) {
                        // new hour
                        currentHour = fileHour;
                    }

                    log.debug("Processing type " + fileType + ": " + fileName + "(" + (Now.NowUtcMs() - fileDate) / (Now.ONE_FULL_DAY) + ") days");

                    boolean readOk = false;
                    retry = 0;
                    ArrayList<lora_poc_v1> toProcess = new ArrayList<>();

                    // search for file locally
                    while (!readOk && retry < 5) {
                        File localFile = new File("./files/" + fileName);
                        if (!localFile.exists() || localFile.length() != object.getSize() ) {
                            if ( localFile.exists() ) {
                                log.debug("Re-Download from S3: "+fileName);
                                localFile.delete();
                            } else {
                                log.debug("Download from S3: "+fileName);
                            }
                            // read it on Amazon, then it exists
                            final GetObjectRequest or = new GetObjectRequest(object.getBucketName(), object.getKey());
                            or.setRequesterPays(true);
                            this.s3Client.getObject(or, localFile);
                        } else {
                            log.debug("Use local file: "+fileName);
                        }

                        GZIPInputStream stream = null;
                        BufferedInputStream bufferedInputStream = null;
                        try {
                            FileInputStream localfileInputStream = new FileInputStream(localFile);
                            // File is GZiped Version of a stream of protobuf messages
                            // each protobuf messages is encapsulated with a header
                            // int4 containing the length of the protobuf message following.
                            stream = new GZIPInputStream(localfileInputStream);
                            bufferedInputStream = new BufferedInputStream(stream);
                            while (bufferedInputStream.available() > 0) {
                                try {
                                    byte[] sz = bufferedInputStream.readNBytes(4);
                                    long len = Stuff.getLongValueFromBytes(sz);
                                    //rSize += len + 4;
                                    if (len > 0) {
                                        byte[] r = bufferedInputStream.readNBytes((int) len);
                                        lora_poc_v1 w = lora_poc_v1.parseFrom(r);
                                        toProcess.add(w);
                                    } else {
                                        log.error("Found 0 len entry " + HexaConverters.byteToHexStringWithSpace(sz));
                                    }
                                } catch (IOException x) {
                                    // in case of IOException Better skip the file
                                    log.error("Failed to process file " + object.getKey() + " at entry " + toProcess.size() + "(" + x.getMessage() + ")");
                                    if (!serviceEnable) return;
                                    toProcess.clear();
                                    retry++;
                                    break;
                                } catch (Exception x) {
                                    log.error("AwsIoTPocSync - " + x.getMessage());
                                    if (!serviceEnable) return;
                                    x.printStackTrace();
                                    toProcess.clear();
                                    retry++;
                                    break;
                                }
                            }
                            readOk = true;
                        } catch (FileNotFoundException x) {
                            log.error("File " + fileName + " not found locally");
                            toProcess.clear();
                            retry++;
                        } catch (SecurityException x) {
                            log.error("File " + fileName + " not accessible");
                            toProcess.clear();
                            retry++;
                        } catch (IOException x) {
                            log.error("Failed to gunzip for Key " + object.getKey() + " " + x.getMessage());
                            toProcess.clear();
                            retry++;
                        } catch (Exception x) {
                            log.error("Failed to process file " + object.getKey() + " " + x.getMessage());
                            toProcess.clear();
                            retry++;
                        } finally {
                            if (bufferedInputStream != null) bufferedInputStream.close();
                            if (stream != null) stream.close();
                        }
                    }

                    if (retry == 5) {
                        log.warn("Impossible to process file " + object.getKey() + " skip it");
                        continue;
                    }

                    log.info("IotPoc from "+new Date(new Timestamp(fileDate).getTime())+" has "+toProcess.size()+" objects" );
                    int current = 0;
                    long currentBeacon = 0;
                    for (lora_poc_v1 w : toProcess) {
                        try {
                            totalWitness++;
                            if ( witnessService.processWitness(w) ) {
                                currentBeacon++;
                                totalBeacon++;
                            }
                            current++;

                            // print progress log on regular basis
                            if ((Now.NowUtcMs() - lastLog) > 5_000) {
                                long distance = etlConfig.getIotpocHistoryStopDate()*1000 - fileDate;
                                log.info("IoTPoc Dist: " + Math.floor(distance / (60*Now.ONE_MINUTE)) + " hours" +
                                         " ,file prog : "+current+"/"+toProcess.size()+" files: " + totalObject + " tSize: " + totalSize / (1024 * 1024) + "MB,"+
                                         " tPoc: " + totalWitness + " tPoc selected: "+totalBeacon+" file Poc sel: "+currentBeacon+
                                         " Duration: " + (Now.NowUtcMs() - start) / 60_000 + "m");
                                lastLog = Now.NowUtcMs();
                            }
                        } catch ( Exception x ) {
                            log.error("Failed to process IoTPoc "+object.getKey()+" at "+current+"/"+toProcess.size()+" ["+x.getMessage()+"]");
                            x.printStackTrace();
                            current++;
                        }
                    } // end of current file
                    witnessService.commitCache();

                    // end of file trace
                    long distance = etlConfig.getIotpocHistoryStopDate()*1000 - fileDate;
                    log.info("IoTPoc Dist: " + Math.floor(distance / (60*Now.ONE_MINUTE)) + " hours" +
                            " ,file prog : "+current+"/"+toProcess.size()+" files: " + totalObject + " tSize: " + totalSize / (1024 * 1024) + "MB,"+
                            " tPoc: " + totalWitness + " tPoc selected: "+totalBeacon+" file Poc sel: "+currentBeacon+
                            " Duration: " + (Now.NowUtcMs() - start) / 60_000 + "m");
                    lastLog = Now.NowUtcMs();

                    toProcess.clear();
                    this.firstFile = false;

                    iotPocFile.setStringValue(object.getKey());
                    paramRepository.save(iotPocFile);

                    if ( serviceEnable == false ) {
                        // we had a request to quit and at this point we can make it
                        // clean
                        log.info("IoTPoc - exit ready");
                        return;
                    }
                }
                lor.setContinuationToken(list.getNextContinuationToken());

            } while (list.isTruncated());
        } catch (AmazonServiceException x) {
            log.error("AwsIoTPocSync - Service "+x.getMessage());
            //x.printStackTrace();
        } catch (AmazonClientException x) {
            log.error("AwsIoTPocSync - Client "+x.getMessage());
            //x.printStackTrace();
        } catch (Exception x) {
            log.error("IoTPoc Batch Failure "+x.getMessage());
            x.printStackTrace();
        } finally {
            // wait the parallel Thread to stop max 5 minutes
            this.pocThreadEnable = false;
            synchronized (this) {
                runningJobs--;
            }
            log.info("IoTPoc - exit completed");
        }
    }
}
