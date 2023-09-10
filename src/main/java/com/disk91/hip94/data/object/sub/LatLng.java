package com.disk91.hip94.data.object.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class LatLng implements ClonnableObject<LatLng> {


    private long lastDatePosition;  // timestamp where change have been detected

    private double lat;

    private double lng;

    private String h3hex = "";


    // --------

    public LatLng clone() {
        LatLng c = new LatLng();
        c.setLat(lat);
        c.setLng(lng);
        c.setLastDatePosition(lastDatePosition);
        return c;
    }


    // ---------


    public long getLastDatePosition() {
        return lastDatePosition;
    }

    public void setLastDatePosition(long lastDatePosition) {
        this.lastDatePosition = lastDatePosition;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getH3hex() {
        return h3hex;
    }

    public void setH3hex(String h3hex) {
        this.h3hex = h3hex;
    }
}
