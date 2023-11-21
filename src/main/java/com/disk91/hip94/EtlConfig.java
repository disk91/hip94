package com.disk91.hip94;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@PropertySource(value = {"file:${config.file}"}, ignoreResourceNotFound = true)
public class EtlConfig {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // =====================================
    // AWS
    // =====================================
    @Value("${aws.access.key}")
    private String awsAccessKey;

    @Value("${aws.secret.key}")
    private String awsSecretKey;

    @Value("${aws.bucket.name}")
    private String awsBucketName;

    // on restart force resync history
    @Value("${aws.force.resync:true}")
    private boolean awsForceResync;


    public String getAwsAccessKey() {
        return awsAccessKey;
    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

    public String getAwsBucketName() {
        return awsBucketName;
    }

    public boolean isAwsForceResync() {
        return awsForceResync;
    }

    // ====================================
    // Internal settings
    // ====================================

    @Value("${cache.hotspot.size}")
    private int cacheHotspotSize;

    @Value("${iotpoc.history.startdate}")
    private long iotpocHistoryStartDate;

    @Value("${iotpoc.history.stopdate}")
    private long iotpocHistoryStopDate;

    public long getIotpocHistoryStartDate() {
        return iotpocHistoryStartDate;
    }

    public int getCacheHotspotSize() {
        return cacheHotspotSize;
    }

    public long getIotpocHistoryStopDate() {
        return iotpocHistoryStopDate;
    }

    // ==============================
    // api backend
    // ==============================

    @Value ("${helium.position.url}")
    private String HeliumPositionUrl;

    @Value ("${helium.brand.url}")
    private String HeliumBrandUrl;

    @Value ("${helium.position.user}")
    private String HeliumPositionUser;

    @Value ("${helium.position.pass}")
    private String HeliumPositionPass;

    public String getHeliumPositionUrl() {
        return HeliumPositionUrl;
    }

    public String getHeliumPositionUser() {
        return HeliumPositionUser;
    }

    public String getHeliumPositionPass() {
        return HeliumPositionPass;
    }

    public String getHeliumBrandUrl() {
        return HeliumBrandUrl;
    }

    // SelectionParam

    @Value("${helium.max.wit.wait.window:200}")
    protected int heliumMaxWitWaitWindow;

    @Value("${helium.max.wit.per.poc:14}")
    protected int heliumMaxWitPerPoc;

    @Value("${helium.ext.wit.wait.window:500}")
    protected int heliumExtWitWaitWindow;

    public int getHeliumMaxWitWaitWindow() {
        return heliumMaxWitWaitWindow;
    }

    public int getHeliumMaxWitPerPoc() {
        return heliumMaxWitPerPoc;
    }

    public int getHeliumExtWitWaitWindow() {
        return heliumExtWitWaitWindow;
    }

    // zone
    @Value("${helium.zone.latnw:0.0}")
    protected double heliumZoneLatNW;

    @Value("${helium.zone.lngnw:0.0}")
    protected double heliumZoneLonNW;

    @Value("${helium.zone.latse:0.0}")
    protected double heliumZoneLatSE;

    @Value("${helium.zone.lngse:0.0}")
    protected double heliumZoneLonSE;

    @Value("${helium.center.ratio:0.5}")
    protected double heliumCenterRatio;

    @Value("${helium.center.limit:50}")
    protected int heliumCenterLimit;


    @Value("${helium.dense.limit:10}")
    protected int heliumDenseLimit;

    @Value("${helium.extending.limit:10}")
    protected int heliumExtendingLimit;


    public double getHeliumZoneLatNW() {
        return heliumZoneLatNW;
    }

    public double getHeliumZoneLonNW() {
        return heliumZoneLonNW;
    }

    public double getHeliumZoneLatSE() {
        return heliumZoneLatSE;
    }

    public double getHeliumZoneLonSE() {
        return heliumZoneLonSE;
    }

    public double getHeliumCenterRatio() {
        return heliumCenterRatio;
    }

    public int getHeliumCenterLimit() {
        return heliumCenterLimit;
    }

    public int getHeliumDenseLimit() {
        return heliumDenseLimit;
    }

    public int getHeliumExtendingLimit() {
        return heliumExtendingLimit;
    }
}
