package com.disk91.hip94.data.object;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;
import org.springframework.data.mongodb.core.mapping.ShardingStrategy;

public class Witness {

    protected String hotspotId;
    protected String h3hex;

    protected long hotspotRxTime;     // reception time on hotspot
    protected long heliumRXTime;      // reception time on Oracle

    protected long travelTime;        // difference with previous values in ms
    protected long deltaTime;         // difference with first arrived

    protected boolean currentlySelected;
    protected  boolean random14Selected; // --
    protected boolean windowsSelected; // --
    protected boolean windows14Selected; // --
    protected boolean windowsExtendedSelected; // ---

    protected long distance;
    protected int  witnessCompetitors;        // number of other witnesser for same poc


    // ---


    public long getHotspotRxTime() {
        return hotspotRxTime;
    }

    public void setHotspotRxTime(long hotspotRxTime) {
        this.hotspotRxTime = hotspotRxTime;
    }

    public long getHeliumRXTime() {
        return heliumRXTime;
    }

    public void setHeliumRXTime(long heliumRXTime) {
        this.heliumRXTime = heliumRXTime;
    }

    public long getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(long travelTime) {
        this.travelTime = travelTime;
    }

    public boolean isCurrentlySelected() {
        return currentlySelected;
    }

    public void setCurrentlySelected(boolean currentlySelected) {
        this.currentlySelected = currentlySelected;
    }

    public boolean isRandom14Selected() {
        return random14Selected;
    }

    public void setRandom14Selected(boolean random14Selected) {
        this.random14Selected = random14Selected;
    }

    public boolean isWindowsSelected() {
        return windowsSelected;
    }

    public void setWindowsSelected(boolean windowsSelected) {
        this.windowsSelected = windowsSelected;
    }

    public boolean isWindows14Selected() {
        return windows14Selected;
    }

    public void setWindows14Selected(boolean windows14Selected) {
        this.windows14Selected = windows14Selected;
    }

    public boolean isWindowsExtendedSelected() {
        return windowsExtendedSelected;
    }

    public void setWindowsExtendedSelected(boolean windowsExtendedSelected) {
        this.windowsExtendedSelected = windowsExtendedSelected;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public int getWitnessCompetitors() {
        return witnessCompetitors;
    }

    public void setWitnessCompetitors(int witnessCompetitors) {
        this.witnessCompetitors = witnessCompetitors;
    }

    public String getHotspotId() {
        return hotspotId;
    }

    public void setHotspotId(String hotspotId) {
        this.hotspotId = hotspotId;
    }

    public long getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(long deltaTime) {
        this.deltaTime = deltaTime;
    }

    public String getH3hex() {
        return h3hex;
    }

    public void setH3hex(String h3hex) {
        this.h3hex = h3hex;
    }
}
