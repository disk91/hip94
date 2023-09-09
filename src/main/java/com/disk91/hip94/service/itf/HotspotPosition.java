package com.disk91.hip94.service.itf;

import com.disk91.hip94.service.itf.sub.LatLng;
import fr.ingeniousthings.tools.ClonnableObject;

public class HotspotPosition implements ClonnableObject<HotspotPosition> {
    private String hotspotId;
    private String animalName;
    private LatLng position;


    // ---------------------------------------------------------
    // init

    public HotspotPosition clone() {
        HotspotPosition h = new HotspotPosition();
        h.setHotspotId(this.getHotspotId());
        h.setAnimalName(this.getAnimalName());
        h.setPosition(position.clone());
        return h;
    }

    // -------------------
    // Getter & Setters


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
}
