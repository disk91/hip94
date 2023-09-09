package com.disk91.hip94.service.itf;

import com.disk91.hip94.data.object.Hotspot;
import fr.ingeniousthings.tools.ClonnableObject;

public class HotspotBrand implements ClonnableObject<HotspotBrand> {
    private Hotspot.HotspotBrand brand;


    // ---------------------------------------------------------
    // init

    public HotspotBrand clone() {
        HotspotBrand h = new HotspotBrand();
        h.setBrand(this.brand);
        return h;
    }

    // -------------------
    // Getter & Setters


    public Hotspot.HotspotBrand getBrand() {
        return brand;
    }

    public void setBrand(Hotspot.HotspotBrand brand) {
        this.brand = brand;
    }
}
