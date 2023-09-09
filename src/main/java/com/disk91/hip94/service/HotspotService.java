package com.disk91.hip94.service;

import com.disk91.hip94.EtlConfig;
import com.disk91.hip94.data.object.Hotspot;
import com.disk91.hip94.data.object.Witness;
import com.disk91.hip94.data.repository.HotspotsRepository;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HotspotService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ObjectCache<String, Hotspot> heliumHotspotCache;

    @Autowired
    protected HotspotsRepository hotspotsRepository;

    @Autowired
    protected LocationService locationService;

    @Autowired
    protected EtlConfig etlConfig;

    @PostConstruct
    private void initHotspotService() {
        this.heliumHotspotCache = new ObjectCache<String, Hotspot>(
            "HotspotCache",
            etlConfig.getCacheHotspotSize(),
            1* Now.ONE_HOUR
        ) {

            @Override
            public void onCacheRemoval(String key, Hotspot obj, boolean batch, boolean last) {
                if ( obj != null )
                    hotspotsRepository.save(obj);
            }

            @Override
            public void bulkCacheUpdate(List<Hotspot> objects) {
                if ( objects != null )
                    objects.forEach(h -> hotspotsRepository.save(h));
            }

        };

    }

    public void commitCache() {
        heliumHotspotCache.commit(false,5000);
    }



    public Hotspot getOneHotspot(String hotspotId, double lat, double lng, long timeRef)
    throws ITNotFoundException {
        Hotspot hs = heliumHotspotCache.get(hotspotId);
        if ( hs == null ) {
            // try db
            hs = hotspotsRepository.findOneHotspotByHotspotId(hotspotId);
            if (hs == null) {
                // create a new one
                hs = new Hotspot();
                hs.init(hotspotId);
                // get location
                if ( Gps.isAValidCoordinate(lat, lng ) ) {
                    // beacon basically
                    hs.getPosition().setLat(lat);
                    hs.getPosition().setLng(lng);
                    hs.getPosition().setLastDatePosition(timeRef);
                } else {
                    /*
                    if ( this.notFoundHs.get(hotspotId) != null)
                        throw new ITNotFoundException();
                    // get it from ETL
                    try {
                        locationService.loadHotspotPosition(hotspotId);
                    } catch (ITNotFoundException x) {
                        this.notFoundHs.put(hotspotId,hotspotId);
                        throw new ITNotFoundException();
                    } catch (ITParseException x) {
                        throw new ITNotFoundException();
                    }
                    */
                    throw new ITNotFoundException();
                }
                try {
                    hs.setBrand(locationService.loadHotspotBrand(hotspotId));
                } catch (ITNotFoundException | ITParseException x) {
                    hs.setBrand(Hotspot.HotspotBrand.UNKNOWN);
                }
                hs = hotspotsRepository.save(hs);       // save & get ID
                heliumHotspotCache.put(hs,hotspotId);
            }
        }
        return hs;
    }

    //HashMap<String,String> hsCache = new HashMap<>();
    public void updateHostspot(Hotspot h) {
        //this.hsCache.computeIfAbsent(h.getHotspotId(), k -> h.getHotspotId());
        heliumHotspotCache.put(h, h.getHotspotId());
    }

    /*
    public boolean isHostpotKnown(String hotpotId) {
        return (this.hsCache.get(hotpotId) != null );
    }
    */

    // ----
    // UPDATE
    // ----

    public void updateStats(String hsId) {
        try {
            Hotspot h = getOneHotspot(hsId, 0, 0, 0);
            AtomicInteger nw = new AtomicInteger(0); AtomicInteger ne = new AtomicInteger(0); AtomicInteger sw = new AtomicInteger(0); AtomicInteger se = new AtomicInteger(0);
            // density around
            AtomicInteger d1 = new AtomicInteger(0); AtomicInteger d5 = new AtomicInteger(0); AtomicInteger d10 = new AtomicInteger(0); AtomicInteger d30= new AtomicInteger(0); AtomicInteger dO= new AtomicInteger(0);

            h.getWitnesses().parallelStream().forEach( w -> {
                try {
                    Hotspot hw = getOneHotspot(w.getHotspotId(), 0, 0, 0);
                    if ( Gps.isAValidCoordinate(hw.getPosition().getLat(),hw.getPosition().getLng())) {

                        if (h.getPosition().getLat() > hw.getPosition().getLat()) {
                            if (h.getPosition().getLng() > hw.getPosition().getLng()) {
                                //sw
                                sw.getAndIncrement();
                            } else {
                                se.getAndIncrement();
                            }
                        } else {
                            if (h.getPosition().getLng() > hw.getPosition().getLng()) {
                                //nw
                                nw.getAndIncrement();
                            } else {
                                ne.getAndIncrement();
                            }
                        }

                        double dist = Gps.distance(
                            h.getPosition().getLat(), hw.getPosition().getLat(),
                            h.getPosition().getLng(), hw.getPosition().getLng(),
                            0, 0);

                        //log.info("hlat: "+h.getPosition().getLat()+" hwlat: "+hw.getPosition().getLat()+" hlng: "+h.getPosition().getLng()+" hwlng: "+hw.getPosition().getLng()+ " dist: "+dist );

                        if (dist < 1_000.0) {
                            d1.getAndIncrement();
                        } else if (dist < 5_000.0) {
                            d5.getAndIncrement();
                        } else if (dist < 10_000.0) {
                            d10.getAndIncrement();
                        } else if (dist < 30_000.0) {
                            d30.getAndIncrement();
                        } else {
                            dO.getAndIncrement();
                        }
                    }
                } catch ( ITNotFoundException x ) {
                    log.warn("Wit Hotspot "+hsId+" not found");
                }
            });
            h.setNwCentering(nw.get());
            h.setNeCentering(ne.get());
            h.setSwCentering(sw.get());
            h.setSeCentering(se.get());
            h.setDensity1km(d1.get());
            h.setDensity5km(d5.get());
            h.setDensity10km(d10.get());
            h.setDensity30km(d30.get());
            h.setDensityOver(dO.get());

            // assuming centered when difference between N & S lower than 30% idem between W & E
            if ( h.getWitnesses().size() > 10 ) {
                h.setCentered(1); // by default
                if ( h.getSwCentering() < etlConfig.getHeliumCenterLimit() ||
                     h.getSeCentering() < etlConfig.getHeliumCenterLimit() ||
                     h.getNwCentering() < etlConfig.getHeliumCenterLimit() ||
                     h.getNeCentering() < etlConfig.getHeliumCenterLimit()
                ) {
                    if ((Math.abs((h.getNeCentering() + h.getNwCentering()) - (h.getSeCentering() + h.getSwCentering())) / (double) h.getWitnesses().size()) > etlConfig.getHeliumCenterRatio()) {
                        h.setCentered(2); // excentered
                    }
                    if ((Math.abs((h.getNeCentering() + h.getSeCentering()) - (h.getNwCentering() + h.getSwCentering())) / (double) h.getWitnesses().size()) > etlConfig.getHeliumCenterRatio()) {
                        h.setCentered(2); // excentrered
                    }
                }
            } else h.setCentered(0); // not enough data

            h.setParticipations(h.getWitnesses().size());
            h.setCurrentSelection(0);
            h.setRandom14Selection(0);
            h.setInTimeWindowsSelection(0);
            h.setInTimeWindows14Selection(0);
            h.setInExtendedTWSelection(0);
            long mDist = 0;
            for ( Witness w : h.getWitnesses() ) {
                 if ( w.isCurrentlySelected() ) h.setCurrentSelection(h.getCurrentSelection()+1);
                 if ( w.isRandom14Selected() ) h.setRandom14Selection(h.getRandom14Selection()+1);
                 if ( w.isWindowsSelected() ) h.setInTimeWindowsSelection(h.getInTimeWindowsSelection()+1);
                 if ( w.isWindows14Selected() ) h.setInTimeWindows14Selection(h.getInTimeWindows14Selection()+1);
                 if ( w.isWindowsExtendedSelected() ) h.setInExtendedTWSelection(h.getInExtendedTWSelection()+1);
                 if ( w.getDistance() > mDist ) mDist = w.getDistance();
            }
            h.setMaxDistance(mDist);

            h.setDenseArea((h.getDensity1km() > etlConfig.getHeliumDenseLimit()));
            h.setExtendCoverage(
                // covering more than a km
                ( h.getDensity5km()+h.getDensity10km()+h.getDensity30km()+h.getDensityOver() > 0 )
                && (
                     // have more than (10) hotspot far and less than (10) under 10km
                      ( ( h.getDensity1km()+h.getDensity5km()+h.getDensity10km() ) < etlConfig.getHeliumExtendingLimit() && ( h.getDensity30km() + h.getDensityOver() ) > etlConfig.getHeliumExtendingLimit() )
                     // have less than 10 hotspots around
                     || ( (h.getDensity1km()+h.getDensity5km()+h.getDensity10km() + h.getDensity30km() + h.getDensityOver() )  < etlConfig.getHeliumExtendingLimit() )
                     // is excentered with a capacity to cover 10km around at least
                     || ( (h.getCentered() == 2 && ( h.getDensity30km() + h.getDensityOver() ) > 1 ) )
                )
            );

            if ( h.getParticipations() > 20 ) {
                if ( h.getDensity5km()+h.getDensity10km()+h.getDensity30km()+h.getDensityOver() > 0 ) {
                    // coverage larger than 1 km
                    h.setLowCoverage(2);
                } else {
                    // low coverage hotspot
                    h.setLowCoverage(1);
                }
            } else {
                // need more data
                h.setLowCoverage(0);
            }

            this.updateHostspot(h);
        } catch ( ITNotFoundException x ) {
            log.warn("Hotspot "+hsId+" not found");
        }
    }


}
