package com.disk91.hip94.api.itf;

import com.disk91.hip94.data.object.Hotspot;
import com.disk91.hip94.data.object.Witness;
import com.disk91.hip94.data.object.sub.LatLng;
import com.disk91.hip94.data.object.sub.RespTimeHist;
import fr.ingeniousthings.tools.Animal;
import fr.ingeniousthings.tools.ClonnableObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "Liter hotspot description", description = "liter hotspot description for reducing network impact")
public class HotspotLiteRespItf {

    private String hotspotId;
    private String animalName;

    private LatLng position;

    private boolean positionChanged;

    private boolean inDenyList;


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


    protected Hotspot.HotspotBrand brand = Hotspot.HotspotBrand.UNKNOWN;

    protected RespTimeHist travelTimeHist;
    protected RespTimeHist arrivalPlaceHist;


    // --

    public void init(Hotspot h) {
        this.hotspotId = h.getHotspotId();
        this.animalName = h.getAnimalName();
        this.position = h.getPosition();
        this.inDenyList = h.isInDenyList();
        this.nwCentering = h.getNwCentering();
        this.neCentering = h.getNeCentering();
        this.swCentering = h.getSwCentering();
        this.seCentering = h.getSeCentering();
        this.centered = h.getCentered();
        this.utilCoverage = h.getUtilCoverage();

        this.density1km = h.getDensity1km();
        this.density5km = h.getDensity5km();
        this.density10km = h.getDensity10km();
        this.density30km = h.getDensity30km();
        this.densityOver = h.getDensityOver();
        this.hotspots1km = h.getHotspots1km();
        this.hotspots5km = h.getHotspots5km();
        this.hotspots10km = h.getHotspots10km();
        this.competitors = h.getCompetitors();
        this.avgWitComp = h.getAvgWitComp();
        this.minWitComp = h.getMinWitComp();
        this.maxWitComp = h.getMaxWitComp();

        this.participations = h.getParticipations();
        this.currentSelection = h.getCurrentSelection();
        this.random14Selection = h.getRandom14Selection();
        this.inTimeWindowsSelection = h.getInTimeWindowsSelection();
        this.inTimeWindows14Selection = h.getInTimeWindows14Selection();
        this.inExtendedTWSelection = h.getInExtendedTWSelection();
        this.maxDistance = h.getMaxDistance();
        this.brand = h.getBrand();
        this.positionChanged = h.isPositionChanged();

        this.travelTimeHist = h.getTravelTimeHist();
        this.arrivalPlaceHist = h.getArrivalPlaceHist();
    }



    // --


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

    public Hotspot.HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(Hotspot.HotspotBrand brand) {
        this.brand = brand;
    }
}
