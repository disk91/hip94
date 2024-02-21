package com.disk91.hip94.service;

import com.disk91.hip94.EtlConfig;
import com.disk91.hip94.data.object.Hotspot;
import com.disk91.hip94.data.object.Witness;
import com.helium.grpc.lora_poc_v1;
import com.helium.grpc.lora_valid_beacon_report_v1;
import com.helium.grpc.lora_verified_witness_report_v1;
import com.uber.h3core.H3Core;
import com.uber.h3core.util.LatLng;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;


@Service
public class WitnessService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HotspotService hotspotService;

    @Autowired
    protected EtlConfig etlConfig;

    protected  H3Core h3;

    @PostConstruct
    private void init() {
        try {
            h3 = H3Core.newInstance();
        } catch (IOException x) {
            log.error("## Impossible de initialize h3 library");
            h3 = null;
        }

    }

    public void commitCache() {
        long start = Now.NowUtcMs();
        hotspotService.commitCache();
        log.info("Commit duration: "+(Now.NowUtcMs()-start)+"ms");
    }

    public void commitStats() {
        long start = Now.NowUtcMs();
        hotspotService.commitStats();
        log.info("Stat Refresh duration: "+(Now.NowUtcMs()-start)+"ms");
    }

    // returns true when the beacon has been proceeded vs skipped
    public boolean processWitness(lora_poc_v1 p) {

        if (h3 == null) return false;

        long start = Now.NowUtcMs();
        // contains POC and Witness information
        lora_valid_beacon_report_v1 beacon = p.getBeaconReport();

        // Update the beaconner
        LatLng pos = h3.cellToLatLng(Long.parseLong(beacon.getLocation()));
        if (pos == null || ! Gps.isAValidCoordinate(pos.lat, pos.lng) ) {
            return false;
        }
        // only process beacon in a given zone if valid
        if (   Gps.isAValidCoordinate(etlConfig.getHeliumZoneLatNW(), etlConfig.getHeliumZoneLonNW() )
            && Gps.isAValidCoordinate(etlConfig.getHeliumZoneLatSE(), etlConfig.getHeliumZoneLonSE() ) ) {
            if ( pos.lat < etlConfig.getHeliumZoneLatSE() || pos.lat > etlConfig.getHeliumZoneLatNW() ) return false;
            if ( pos.lng < etlConfig.getHeliumZoneLonNW() || pos.lng > etlConfig.getHeliumZoneLonSE() ) return false;
        }
        String hsBeaconerId = HeliumHelper.pubAddressToName(beacon.getReport().getPubKey());
        long beaconTimeMs = beacon.getReport().getTimestamp() / 1_000_000;

        Hotspot beaconner = null;
        try {
            beaconner = hotspotService.getOneHotspot(hsBeaconerId, beacon.getLocation(), pos.lat, pos.lng, beaconTimeMs);
        } catch (ITNotFoundException x) {
            return false;
        }

        // verify the beacon does not have already been proceeded
        if ( beaconner.getLastBeacon() > beaconTimeMs ) {
            // already processed
            return false;
        }
        beaconner.setLastBeacon(beaconTimeMs);

        int competitors = p.getSelectedWitnessesCount();
        for ( lora_verified_witness_report_v1 v : p.getUnselectedWitnessesList() ) {
            if (v.hasInvalidDetails()) {
                if (!v.getInvalidDetails().hasDenylistTag()) {
                    competitors++;
                }
            } else competitors++;
        }

        ArrayList<Witness> witnesses = new ArrayList<>();

        // Update the Witness information
        for ( lora_verified_witness_report_v1 v : p.getSelectedWitnessesList() ) {
            Witness w = new Witness();
            LatLng wpos = null;
            try {
                wpos = h3.cellToLatLng(Long.parseLong(v.getLocation()));
            } catch ( NumberFormatException x ) {
                continue;
            }
            if (wpos == null || ! Gps.isAValidCoordinate(wpos.lat, wpos.lng) ) {
                continue;
            }
            try {
                Hotspot hw = hotspotService.getOneHotspot(HeliumHelper.pubAddressToName(v.getReport().getPubKey()), v.getLocation(), wpos.lat, wpos.lng, beaconTimeMs);
                // Not needed anymore
                // hw.cleanWitness(beaconner.getHotspotId(),beaconTimeMs);

                w.setHotspotId(beaconner.getHotspotId());
                w.setH3hex(beacon.getLocation());
                w.setHotspotRxTime(v.getReport().getTimestamp() / 1_000_000); // Ts at Hostpot Rx
                w.setHeliumRXTime(v.getReceivedTimestamp()); // Ts at ingest
                w.setTravelTime(w.getHeliumRXTime() - w.getHotspotRxTime()); // travel time
                w.setCurrentlySelected(true);
                w.setRandom14Selected(false);
                w.setWindows14Selected(false);
                w.setWindowsSelected(false);
                w.setWindowsExtendedSelected(false);
                w.setDistance(
                    (long)Gps.distance(beaconner.getPosition().getLat(),hw.getPosition().getLat(),
                        beaconner.getPosition().getLng(),hw.getPosition().getLng(), 0,0)
                );
                w.setWitnessCompetitors(competitors);
                hw.getWitnesses().add(w);
                witnesses.add(w);
            } catch (ITNotFoundException x) {
                continue;
            }
        }
        for ( lora_verified_witness_report_v1 v : p.getUnselectedWitnessesList() ) {
            // pass the denied
            if (v.hasInvalidDetails()) {
                if (v.getInvalidDetails().hasDenylistTag()) {
                    continue;
                }
            }
            Witness w = new Witness();
            LatLng wpos = null;
            try {
                wpos = h3.cellToLatLng(Long.parseLong(v.getLocation()));
            } catch ( NumberFormatException x ) {
                continue;
            }
            if (wpos == null || ! Gps.isAValidCoordinate(wpos.lat, wpos.lng) ) {
                continue;
            }
            try {
                Hotspot hw = hotspotService.getOneHotspot(HeliumHelper.pubAddressToName(v.getReport().getPubKey()), v.getLocation(), wpos.lat, wpos.lng, beaconTimeMs);
                // not needed anymore
                // hw.cleanWitness(beaconner.getHotspotId(),beaconTimeMs);

                w.setHotspotId(beaconner.getHotspotId());
                w.setH3hex(beacon.getLocation());
                w.setHotspotRxTime(v.getReport().getTimestamp() / 1_000_000); // Ts at Hostpot Rx
                w.setHeliumRXTime(v.getReceivedTimestamp()); // Ts at ingest
                w.setTravelTime(w.getHeliumRXTime() - w.getHotspotRxTime()); // travel time
                w.setCurrentlySelected(false);
                w.setRandom14Selected(false);
                w.setWindows14Selected(false);
                w.setWindowsSelected(false);
                w.setWindowsExtendedSelected(false);
                w.setDistance(
                    (long)Gps.distance(
                        beaconner.getPosition().getLat(),hw.getPosition().getLat(),
                        beaconner.getPosition().getLng(),hw.getPosition().getLng(),
                        0,0)
                );
                w.setWitnessCompetitors(competitors);
                hw.getWitnesses().add(w);
                witnesses.add(w);
            } catch (ITNotFoundException x) {
                continue;
            }
        }

        // Lets make the new computations
        // select 14 of total randomly
        if ( witnesses.size() > 14 ) {
            for (int i = 0; i < etlConfig.getHeliumMaxWitPerPoc(); i++) {
                boolean found = false;
                while (!found) {
                    int rand = (int) (Math.random() * witnesses.size());
                    if (!witnesses.get(rand).isRandom14Selected()) {
                        witnesses.get(rand).setRandom14Selected(true);
                        found = true;
                    }
                }
            }
        } else {
            witnesses.forEach(w -> {
                w.setRandom14Selected(true);
            });
        }

        // Select all in the time windows (200) from first received
        Witness first = null;
        for ( Witness w : witnesses ) {
            // search the first arrival
            if ( first == null ) first = w;
            else {
                if ( first.getHeliumRXTime() > w.getHeliumRXTime() ) {
                    first = w;
                }
            }
        }
        int selected = 0;
        ArrayList<Witness> inWindow = new ArrayList<>();
        for ( Witness w : witnesses ) {
            // update the arrival delta time
            w.setDeltaTime(w.getHeliumRXTime() - first.getHeliumRXTime());

            // identify within time window
            if ( (w.getHeliumRXTime() - first.getHeliumRXTime()) < etlConfig.getHeliumMaxWitWaitWindow() ) {
                w.setWindowsSelected(true);
                inWindow.add(w);
            }
        }

        if ( inWindow.size() < etlConfig.getHeliumMaxWitPerPoc() ) {
            // not enough witnesser, go to extended windows
            for ( Witness w : witnesses ) {
                if ( ! w.isWindowsSelected()
                     && (w.getHeliumRXTime() - first.getHeliumRXTime()) < etlConfig.getHeliumExtWitWaitWindow() ) {
                    w.setWindowsSelected(true);
                    w.setWindowsExtendedSelected(true);
                    inWindow.add(w);
                }
            }
        }

        // select 14 of total randomly in the Window
        if ( inWindow.size() > etlConfig.getHeliumMaxWitPerPoc() ) {
            for (int i = 0; i < etlConfig.getHeliumMaxWitPerPoc(); i++) {
                boolean found = false;
                while (!found) {
                    int rand = (int) (Math.random() * inWindow.size());
                    if (!inWindow.get(rand).isWindows14Selected()) {
                        inWindow.get(rand).setWindows14Selected(true);
                        found = true;
                    }
                }
            }
        } else {
            inWindow.forEach(w -> {
                w.setWindows14Selected(true);
            });
        }

        hotspotService.updateHostspot(beaconner);

        // Need to recompute all the witnesses but do it only every 30 minutes...
        // by the way, this is done between files so here we should never really execute this.
        witnesses.parallelStream().forEach( w -> {
            hotspotService.updateStats(w.getHotspotId(),false);
        });

        return true;
    }

}
