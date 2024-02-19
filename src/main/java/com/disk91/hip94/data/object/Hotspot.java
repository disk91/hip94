package com.disk91.hip94.data.object;

import com.disk91.hip94.data.object.sub.LatLng;
import com.disk91.hip94.data.object.sub.RespTimeHist;
import fr.ingeniousthings.tools.Animal;
import fr.ingeniousthings.tools.ClonnableObject;
import fr.ingeniousthings.tools.Now;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "etl_hotspots")
@CompoundIndexes({
        @CompoundIndex(name = "hotspotId", def = "{'hotspotId' : 1 }"),
        @CompoundIndex(name = "animalName", def = "{'animalName' : 'text'}"),
        @CompoundIndex(name = "hotspotId_Id", def = "{'hotspotId' : 1, 'id' : 1}")
})
public class Hotspot implements ClonnableObject<Hotspot> {

    @Id
    private String id;
    private String hotspotId;
    private String animalName;

    private LatLng position;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint mongoPosition;

    private boolean positionChanged;

    private boolean inDenyList;


    // List of hotspots receiving this hotspot
    private List<Witness> witnesses;

    int nwCentering;    // quantity of beacon on north west side
    int neCentering;    // qantity of beacon on north east side
    int swCentering;  // quantity of beacon on south west side
    int seCentering; // quantity of beacon on south east side

    int centered;   // 0 unknown, 1 true when the hotspot is centered vs excentred

    int density1km;     // number of hotspot-beaconing 1km around
    int hotspots1km;    // number of hotspot 1km around
    int density5km;     // number of hotspot-beaconing 1-5km around
    int hotspots5km;    // number of hotspot 1km to 5km around
    int density10km;    // number of hotspot-beaconing 5-10km around
    int hotspots10km;    // number of hotspot 5km to 10km around
    int density30km;    // number of hotspot-beaconing 10-30km around
    int densityOver;    // number of hotspot-beaconing above 30km

    int competitors;    // number of hotspot-beaconing around (sum of density)
    double avgWitComp;  // average number of competitor during poc
    int minWitComp;     // min seen competitor during poc
    int maxWitComp;     // max seen competitor during pc

    boolean denseArea;   // true when more than 10 hotspot-beaconing in 1km around
    boolean extendCoverage; // true when the density is mostly more than 10km+ or excentered with 10km+ coverage

    int lowCoverage;    // 0 unknown, 1 when true ( no hs above 1km ), 2 when false
    int utilCoverage;   // 0 unknow, 1 when this HS may add a usefull coverage, 2 when it's not

    // selection ratio
    long participations;    // number of participations to PoC
    long currentSelection;  // number of time it is currently selected
    long random14Selection; // number of time it should have been selected with a random approach
    long inTimeWindowsSelection; // number of time it should selected as part of the time windows
    long inTimeWindows14Selection; // number of time it should selected as part of the time windows with 14 random
    long inExtendedTWSelection; // number of time it is selected in the extended TW instead of normal TW

    // Other coverage score
    long maxDistance;   // covered distance base on witness


    public enum HotspotBrand {
        UNKNOWN, HELIUM, RAK, NEBRA, KERLINK, COTX, SENSECAP, SYNCROBIT, BOBCAT, LONGAP,
        SMARTMIMIC, CALCHIP, DEWI, PISCES, CLODPI, LINXDOT, CONTROLLINO, HELTEC, FREEDOMFI, PANTHERX,
        HUMMINGBIRD, RISINGHF, BROWAN, MILESIGHT, DEEPER, MIDAS, DRAGINO, PYCOM
    };
    protected HotspotBrand brand = HotspotBrand.UNKNOWN;

    protected RespTimeHist travelTimeHist;
    protected RespTimeHist arrivalPlaceHist;

    protected long lastUpdate = 0;
    protected long lastBeacon = -1;
    protected boolean statsOk = false;

    @Transient
    private long lastKnownBeacon = 0;

    // --

    public void init(String hotspotId) {
        this.hotspotId = hotspotId;
        this.animalName = Animal.getAnimalName(hotspotId,'-');
        this.position = new LatLng();
        this.position.setLat(0.0);
        this.position.setLng(0.0);
        this.position.setLastDatePosition(0);
        this.mongoPosition = new GeoJsonPoint(0.0,0.0);
        this.inDenyList = false;
        this.witnesses = new ArrayList<>();
        this.nwCentering = 0;
        this.neCentering = 0;
        this.swCentering = 0;
        this.seCentering = 0;
        this.centered = 0;
        this.utilCoverage = 0;

        this.density1km = 0;
        this.density5km = 0;
        this.density10km = 0;
        this.density30km = 0;
        this.densityOver = 0;
        this.hotspots1km = 0;
        this.hotspots5km = 0;
        this.hotspots10km = 0;
        this.competitors = 0;
        this.avgWitComp = 0.0;
        this.minWitComp = 0;
        this.maxWitComp = 0;

        this.participations = 0;
        this.currentSelection = 0;
        this.random14Selection = 0;
        this.inTimeWindowsSelection = 0;
        this.inTimeWindows14Selection = 0;
        this.inExtendedTWSelection = 0;
        this.maxDistance = 0;
        this.brand = HotspotBrand.UNKNOWN;
        this.positionChanged = false;

        this.travelTimeHist = new RespTimeHist();
        this.travelTimeHist.init();
        this.arrivalPlaceHist = new RespTimeHist();
        this.arrivalPlaceHist.init();

        this.lastUpdate= Now.NowUtcMs() - 20*Now.ONE_MINUTE; // so the first update may be in 10 minutes
    }


    @Override
    public Hotspot clone() {
        return null;
    }


    public void cleanWitness(String beaconner, long timeRef) {
        if ( lastKnownBeacon == 0 || lastKnownBeacon > timeRef ) {
            // clean the witness list removing all the entries older tha given one for that hotspot
            ArrayList<Witness> ws = new ArrayList<>();
            for (Witness w : this.witnesses) {
                if (w.getHotspotId().compareToIgnoreCase(beaconner) != 0 || w.getHeliumRXTime() < (timeRef + 30_000)) {
                    // keep that one
                    ws.add(w);
                    if ( ( w.getHeliumRXTime() - 30_000 ) > lastKnownBeacon ) lastKnownBeacon = ( w.getHeliumRXTime() - 30_000 );
                }
            }
            this.witnesses = ws;
        }
    }

    // --


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public boolean isInDenyList() {
        return inDenyList;
    }

    public void setInDenyList(boolean inDenyList) {
        this.inDenyList = inDenyList;
    }

    public List<Witness> getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(List<Witness> witnesses) {
        this.witnesses = witnesses;
    }

    public int getNwCentering() {
        return nwCentering;
    }

    public void setNwCentering(int nwCentering) {
        this.nwCentering = nwCentering;
    }

    public int getNeCentering() {
        return neCentering;
    }

    public void setNeCentering(int neCentering) {
        this.neCentering = neCentering;
    }

    public int getSwCentering() {
        return swCentering;
    }

    public void setSwCentering(int swCentering) {
        this.swCentering = swCentering;
    }

    public int getSeCentering() {
        return seCentering;
    }

    public void setSeCentering(int seCentering) {
        this.seCentering = seCentering;
    }

    public int getCentered() {
        return centered;
    }

    public void setCentered(int centered) {
        this.centered = centered;
    }

    public int getDensity1km() {
        return density1km;
    }

    public void setDensity1km(int density1km) {
        this.density1km = density1km;
    }

    public int getDensity5km() {
        return density5km;
    }

    public void setDensity5km(int density5km) {
        this.density5km = density5km;
    }

    public int getDensity10km() {
        return density10km;
    }

    public void setDensity10km(int density10km) {
        this.density10km = density10km;
    }

    public long getParticipations() {
        return participations;
    }

    public void setParticipations(long participations) {
        this.participations = participations;
    }

    public long getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(long currentSelection) {
        this.currentSelection = currentSelection;
    }

    public long getRandom14Selection() {
        return random14Selection;
    }

    public void setRandom14Selection(long random14Selection) {
        this.random14Selection = random14Selection;
    }

    public long getInTimeWindowsSelection() {
        return inTimeWindowsSelection;
    }

    public void setInTimeWindowsSelection(long inTimeWindowsSelection) {
        this.inTimeWindowsSelection = inTimeWindowsSelection;
    }

    public long getInTimeWindows14Selection() {
        return inTimeWindows14Selection;
    }

    public void setInTimeWindows14Selection(long inTimeWindows14Selection) {
        this.inTimeWindows14Selection = inTimeWindows14Selection;
    }

    public long getInExtendedTWSelection() {
        return inExtendedTWSelection;
    }

    public void setInExtendedTWSelection(long inExtendedTWSelection) {
        this.inExtendedTWSelection = inExtendedTWSelection;
    }

    public long getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(long maxDistance) {
        this.maxDistance = maxDistance;
    }

    public HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(HotspotBrand brand) {
        this.brand = brand;
    }


    public boolean isPositionChanged() {
        return positionChanged;
    }

    public void setPositionChanged(boolean positionChanged) {
        this.positionChanged = positionChanged;
    }

    public int getDensity30km() {
        return density30km;
    }

    public void setDensity30km(int density30km) {
        this.density30km = density30km;
    }

    public int getDensityOver() {
        return densityOver;
    }

    public void setDensityOver(int densityOver) {
        this.densityOver = densityOver;
    }

    public boolean isDenseArea() {
        return denseArea;
    }

    public void setDenseArea(boolean denseArea) {
        this.denseArea = denseArea;
    }

    public boolean isExtendCoverage() {
        return extendCoverage;
    }

    public void setExtendCoverage(boolean extendCoverage) {
        this.extendCoverage = extendCoverage;
    }

    public int getLowCoverage() {
        return lowCoverage;
    }

    public void setLowCoverage(int lowCoverage) {
        this.lowCoverage = lowCoverage;
    }

    public int getCompetitors() {
        return competitors;
    }

    public void setCompetitors(int competitors) {
        this.competitors = competitors;
    }

    public double getAvgWitComp() {
        return avgWitComp;
    }

    public void setAvgWitComp(double avgWitComp) {
        this.avgWitComp = avgWitComp;
    }

    public int getMinWitComp() {
        return minWitComp;
    }

    public void setMinWitComp(int minWitComp) {
        this.minWitComp = minWitComp;
    }

    public int getMaxWitComp() {
        return maxWitComp;
    }

    public void setMaxWitComp(int maxWitComp) {
        this.maxWitComp = maxWitComp;
    }

    public RespTimeHist getTravelTimeHist() {
        return travelTimeHist;
    }

    public void setTravelTimeHist(RespTimeHist travelTimeHist) {
        this.travelTimeHist = travelTimeHist;
    }

    public RespTimeHist getArrivalPlaceHist() {
        return arrivalPlaceHist;
    }

    public void setArrivalPlaceHist(RespTimeHist arrivalPlaceHist) {
        this.arrivalPlaceHist = arrivalPlaceHist;
    }

    public GeoJsonPoint getMongoPosition() {
        return mongoPosition;
    }

    public void setMongoPosition(GeoJsonPoint mongoPosition) {
        this.mongoPosition = mongoPosition;
    }

    public int getHotspots1km() {
        return hotspots1km;
    }

    public void setHotspots1km(int hotspots1km) {
        this.hotspots1km = hotspots1km;
    }

    public int getHotspots5km() {
        return hotspots5km;
    }

    public void setHotspots5km(int hotspots5km) {
        this.hotspots5km = hotspots5km;
    }

    public int getHotspots10km() {
        return hotspots10km;
    }

    public void setHotspots10km(int hotspots10km) {
        this.hotspots10km = hotspots10km;
    }

    public int getUtilCoverage() {
        return utilCoverage;
    }

    public void setUtilCoverage(int utilCoverage) {
        this.utilCoverage = utilCoverage;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isStatsOk() {
        return statsOk;
    }

    public void setStatsOk(boolean statsOk) {
        this.statsOk = statsOk;
    }

    public long getLastBeacon() {
        return lastBeacon;
    }

    public void setLastBeacon(long lastBeacon) {
        this.lastBeacon = lastBeacon;
    }
}
