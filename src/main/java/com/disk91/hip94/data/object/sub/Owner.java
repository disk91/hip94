package com.disk91.hip94.data.object.sub;

import fr.ingeniousthings.tools.ClonnableObject;

public class Owner implements ClonnableObject<Owner> {

    private String hntOwner = "";

    private String solOwner = "";

    private long timeMs = 0;

    // --------

    public Owner clone() {
        Owner c = new Owner();
        c.setHntOwner(hntOwner);
        c.setSolOwner(solOwner);
        c.setTimeMs(timeMs);
        return c;
    }


    // ---------


    public String getHntOwner() {
        return hntOwner;
    }

    public void setHntOwner(String hntOwner) {
        this.hntOwner = hntOwner;
    }

    public String getSolOwner() {
        return solOwner;
    }

    public void setSolOwner(String solOwner) {
        this.solOwner = solOwner;
    }

    public long getTimeMs() {
        return timeMs;
    }

    public void setTimeMs(long timeMs) {
        this.timeMs = timeMs;
    }
}
