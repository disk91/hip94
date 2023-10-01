package com.disk91.hip94.service;

import com.disk91.hip94.EtlConfig;
import com.disk91.hip94.api.itf.HotspotLiteRespItf;
import com.disk91.hip94.data.object.Hotspot;
import com.disk91.hip94.data.object.Witness;
import com.disk91.hip94.data.repository.HotspotsRepository;
import fr.ingeniousthings.tools.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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

    // search Hotspot in a zone
    public List<HotspotLiteRespItf> getHotspotInAZone(double latN, double latS, double lonW, double lonE)
    throws ITNotFoundException {
        ArrayList<HotspotLiteRespItf> ret = new ArrayList<>();
        if ( latN < latS || lonW > lonE ) throw new ITNotFoundException("Invalid coordinate");

        if ( Gps.distance(latN,latS,lonW,lonE,0,0) > 50_000 ) {
            throw new ITNotFoundException("Search area is too large");
        }
        log.info("Search hotspot around ("+latN+", "+lonW+") and ("+latS+", "+lonE+")");

        List<Hotspot> hs = hotspotsRepository.findByMongoPositionNearbyBox(lonW,latS,lonE,latN);
        log.info("Found "+hs.size()+" hotspots around");
        hs.parallelStream().forEach(h -> {
            HotspotLiteRespItf hl = new HotspotLiteRespItf();
            hl.init(h);
            ret.add(hl);
        });

        return ret;
    }

    public List<HotspotLiteRespItf> getHotspotsByAnimal(String name)
    throws ITNotFoundException {

        name = name.replace(' ', '-');
        PageRequest pageRequest = PageRequest.of(0,10);

        ArrayList<HotspotLiteRespItf> ret = new ArrayList<>();
        List<Hotspot> his = hotspotsRepository.findHotspotByAnimalNameLike(name, pageRequest);
        if ( his.size() == 0 ) {
            his = hotspotsRepository.findHotspotByAnimalNameStarts(name,pageRequest);
        }
        for ( Hotspot hi : his ) {
            HotspotLiteRespItf id = new HotspotLiteRespItf();
            id.init(hi);
            ret.add(id);
        }
        if ( ret.size() == 0 ) throw new ITNotFoundException();
        return ret;

    }


    public Hotspot getOneExistingHotspot(String hotspotId)
    throws ITNotFoundException {
        Hotspot hs = heliumHotspotCache.get(hotspotId);
        if (hs == null) {
            hs = hotspotsRepository.findOneHotspotByHotspotId(hotspotId);
            if (hs == null) {
                throw new ITNotFoundException();
            }
        }
        return hs;
    }

    public Hotspot getOneHotspot(String hotspotId, String h3, double lat, double lng, long timeRef)
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
                    hs.getPosition().setH3hex(h3);
                    hs.setMongoPosition(new GeoJsonPoint(lng,lat));
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
            Hotspot h = getOneHotspot(hsId, "",0, 0, 0);

            // identify unique hotspots
            HashMap<String,Witness> hss = new HashMap<>();
            for ( Witness _w : h.getWitnesses() ) {
                hss.putIfAbsent(_w.getHotspotId(),_w);
            }

            // compute the location of the witnesses
            AtomicInteger nw = new AtomicInteger(0); AtomicInteger ne = new AtomicInteger(0); AtomicInteger sw = new AtomicInteger(0); AtomicInteger se = new AtomicInteger(0);
            h.getWitnesses().parallelStream().forEach( w -> {
                try {
                    Hotspot hw = getOneHotspot(w.getHotspotId(), "",0, 0, 0);
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
                    }
                } catch ( ITNotFoundException x ) {
                    log.warn("Wit Hotspot "+hsId+" not found");
                }
            });
            h.setNwCentering(nw.get());
            h.setNeCentering(ne.get());
            h.setSwCentering(sw.get());
            h.setSeCentering(se.get());

            // compute the location of the hotspots around and distance
            AtomicInteger hnw = new AtomicInteger(0); AtomicInteger hne = new AtomicInteger(0); AtomicInteger hsw = new AtomicInteger(0); AtomicInteger hse = new AtomicInteger(0);
            hss.values().parallelStream().forEach( w -> {
                try {
                    Hotspot hw = getOneHotspot(w.getHotspotId(), "",0, 0, 0);
                    if ( Gps.isAValidCoordinate(hw.getPosition().getLat(),hw.getPosition().getLng())) {

                        if (h.getPosition().getLat() > hw.getPosition().getLat()) {
                            if (h.getPosition().getLng() > hw.getPosition().getLng()) {
                                //sw
                                hsw.getAndIncrement();
                            } else {
                                hse.getAndIncrement();
                            }
                        } else {
                            if (h.getPosition().getLng() > hw.getPosition().getLng()) {
                                //nw
                                hnw.getAndIncrement();
                            } else {
                                hne.getAndIncrement();
                            }
                        }
                    }
                } catch ( ITNotFoundException x ) {
                    log.warn("Wit Hotspot "+hsId+" not found 2");
                }
            });

            // assuming centered when difference between N & S lower than 50% idem between W & E
            // based on witnesses. If more than large number of HS all direction, directly consider centered
            if ( h.getWitnesses().size() > 10 ) {
                h.setCentered(1); // by default
                if ( hne.get() < etlConfig.getHeliumCenterLimit() ||    // if we have plenty of HS all round, we are centered de facto
                    hse.get() < etlConfig.getHeliumCenterLimit() ||
                    hnw.get() < etlConfig.getHeliumCenterLimit() ||
                    hne.get() < etlConfig.getHeliumCenterLimit()
                ) {
                    if ((Math.abs((h.getNeCentering() + h.getNwCentering()) - (h.getSeCentering() + h.getSwCentering())) / (double) h.getWitnesses().size()) > etlConfig.getHeliumCenterRatio()) {
                        h.setCentered(2); // excentered
                    }
                    if ((Math.abs((h.getNeCentering() + h.getSeCentering()) - (h.getNwCentering() + h.getSwCentering())) / (double) h.getWitnesses().size()) > etlConfig.getHeliumCenterRatio()) {
                        h.setCentered(2); // excentrered
                    }
                }
            } else h.setCentered(0); // not enough data


            // density around
            AtomicInteger d1 = new AtomicInteger(0); AtomicInteger d5 = new AtomicInteger(0); AtomicInteger d10 = new AtomicInteger(0); AtomicInteger d30= new AtomicInteger(0); AtomicInteger dO= new AtomicInteger(0);
            hss.values().parallelStream().forEach( w -> {
                try {
                    Hotspot hw = getOneHotspot(w.getHotspotId(),"", 0, 0, 0);
                    if ( Gps.isAValidCoordinate(hw.getPosition().getLat(),hw.getPosition().getLng())) {
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
            h.setDensity1km(d1.get());
            h.setDensity5km(d5.get());
            h.setDensity10km(d10.get());
            h.setDensity30km(d30.get());
            h.setDensityOver(dO.get());

            h.setParticipations(h.getWitnesses().size());
            if ( h.getCompetitors() != hss.size() ) {
                // new hotspot detected, rescan the hotspot physically around
                // @Todo apparently Mongo JPA does not know how to manage a count request and return all, so need to pass to the object
                //       may be slow with big dataset
                h.setHotspots1km(hotspotsRepository.findMongoPositionByNearbyDistance(h.getPosition().getLng(),h.getPosition().getLat(),1_000).size());
                h.setHotspots5km(hotspotsRepository.findMongoPositionByNearbyDistance(h.getPosition().getLng(),h.getPosition().getLat(),5_000).size());
                h.setHotspots10km(hotspotsRepository.findMongoPositionByNearbyDistance(h.getPosition().getLng(),h.getPosition().getLat(),10_000).size());
                h.setHotspots10km(h.getHotspots10km()-h.getHotspots5km());
                h.setHotspots5km(h.getHotspots5km()-h.getHotspots1km());
            }
            h.setCompetitors(hss.size());
            h.setCurrentSelection(0);
            h.setRandom14Selection(0);
            h.setInTimeWindowsSelection(0);
            h.setInTimeWindows14Selection(0);
            h.setInExtendedTWSelection(0);
            long mDist = 0;
            double totCompet = 0.0;
            h.setMinWitComp(1_000_000);
            h.setMaxWitComp(0);
            h.getTravelTimeHist().init();
            h.getArrivalPlaceHist().init();
            for ( Witness w : h.getWitnesses() ) {
                if ( w.isCurrentlySelected() ) h.setCurrentSelection(h.getCurrentSelection()+1);
                if ( w.isRandom14Selected() ) h.setRandom14Selection(h.getRandom14Selection()+1);
                if ( w.isWindowsSelected() ) h.setInTimeWindowsSelection(h.getInTimeWindowsSelection()+1);
                if ( w.isWindows14Selected() ) h.setInTimeWindows14Selection(h.getInTimeWindows14Selection()+1);
                if ( w.isWindowsExtendedSelected() ) h.setInExtendedTWSelection(h.getInExtendedTWSelection()+1);
                if ( w.getDistance() > mDist ) mDist = w.getDistance();
                if ( w.getWitnessCompetitors() > h.getMaxWitComp() ) h.setMaxWitComp(w.getWitnessCompetitors());
                if ( w.getWitnessCompetitors() < h.getMinWitComp() ) h.setMinWitComp(w.getWitnessCompetitors());
                totCompet += w.getWitnessCompetitors();
                // compute travel time histogram
                h.getArrivalPlaceHist().addOneTime(w.getDeltaTime());
                h.getTravelTimeHist().addOneTime(w.getTravelTime());
            }
            h.setAvgWitComp(totCompet/h.getWitnesses().size());
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
                    // coverage larger than 1 km, we have PoC with hotspot around and over 1km
                    h.setLowCoverage(2);
                } else {
                    // low coverage hotspot
                    if ( h.getHotspots5km()+ h.getHotspots10km() > 0 ) {
                        // we know some hotspots around exists even if no interaction
                        h.setLowCoverage(1);
                    } else {
                        // we don't know so we can't really measure the coverage
                        h.setLowCoverage(0);
                    }
                }
            } else {
                // need more data
                h.setLowCoverage(0);
            }

            h.setUtilCoverage(0);
            if ( h.getLowCoverage() != 1 ) {
                if (
                    // hotspot cover a zone where we don't have other hotspot
                    ( h.getSeCentering() == 0 || h.getNeCentering() == 0 || h.getNwCentering() == 0 || h.getSwCentering() == 0 )
                    // hotspot does not have a redundancy around
                 || ( h.getHotspots5km() < 3)
                    // @Todo - we could search for hotspot in the quarter to get coverage redundancy instead of unique coverage
                ) {
                    h.setUtilCoverage(1);
                }
            } else {
                // low coverage with other hotspot around...
                h.setUtilCoverage(2);
            }


            this.updateHostspot(h);
        } catch ( ITNotFoundException x ) {
            log.warn("Hotspot "+hsId+" not found");
        }
    }


}
