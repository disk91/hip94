package com.disk91.hip94.data.object.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class LatLng implements ClonnableObject<LatLng> {


    private long lastDatePosition;  // timestamp where change have been detected

    private double lat;

    private double lng;

    private String country = "";

    private String city = "";


    private double alt;

    private double gain;

    private double hexScale = 0;

    // --------

    public LatLng clone() {
        LatLng c = new LatLng();
        c.setLat(lat);
        c.setLng(lng);
        c.setAlt(alt);
        c.setGain(gain);
        c.setCountry(country);
        c.setCity(city);
        c.setLastDatePosition(lastDatePosition);
        c.setHexScale(hexScale);
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

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getHexScale() { return this.hexScale; }

    public void setHexScale(double hexScale) { this.hexScale = hexScale; }


}
