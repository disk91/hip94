declare module "vue/types/hotspots" {

    enum HotspotBrand {
        UNKNOWN, HELIUM, RAK, NEBRA, KERLINK, COTX, SENSECAP, SYNCROBIT, BOBCAT, LONGAP,
        SMARTMIMIC, CALCHIP, DEWI, PISCES, CLODPI, LINXDOT, CONTROLLINO, HELTEC, FREEDOMFI, PANTHERX,
        HUMMINGBIRD, RISINGHF, BROWAN, MILESIGHT, DEEPER, MIDAS, DRAGINO, PYCOM
    }


    interface LatLng {
        lastDatePosition: number;  // timestamp where change have been detected
        lat: number;
        lng: number;
        h3hex: string;
    }

    interface RespTimeHist {
        lower10ms: number;
        lower30ms: number;
        lower50ms: number;
        lower100ms: number;
        lower250ms: number;
        lower500ms: number;
        lower1s: number;
        lower2s: number;
        lower3s: number;
        over3s: number;
    }
    
    interface Witness {
        hotspotId: string;
        h3hex: string;
        hotspotRxTime: number; // reception time on hotspot
        heliumRXTime: number;  // reception time on Oracle
        travelTime: number;    // difference with previous values in ms
        deltaTime: number;     // difference with first arrived
        currentlySelected: boolean;
        random14Selected: boolean;
        windowsSelected: boolean;
        windows14Selected: boolean;
        windowsExtendedSelected: boolean;
        distance: number;
        witnessCompetitors: number;  // number of other witnesser for same poc
    }

    interface Hotspot {
        hotspotId: string;
        animalName: string;
        position: LatLng;
        positionChanged: boolean;
        inDenyList: boolean;
        witnesses: Witness[]; 
        nwCentering: number;
        neCentering: number;
        swCentering: number;
        seCentering: number;
        centered: number;
        density1km: number;
        hotspots1km: number;
        density5km: number;
        hotspots5km: number;
        density10km: number;
        hotspots10km: number;
        density30km: number;
        densityOver: number;
        competitors: number;
        avgWitComp: number;
        minWitComp: number;
        maxWitComp: number;
        denseArea: boolean;
        extendCoverage: boolean;
        lowCoverage: number;
        utilCoverage: number;
        participations: number;
        currentSelection: number;
        random14Selection: number;
        inTimeWindowsSelection: number;
        inTimeWindows14Selection: number;
        inExtendedTWSelection: number;
        maxDistance: number;
        brand: HotspotBrand;
        travelTimeHist: RespTimeHist; 
        arrivalPlaceHist: RespTimeHist; 
    }

    interface HotspotLiteRespItf {
        hotspotId: string;
        animalName: string;
        position: LatLng;
        positionChanged: boolean;
        inDenyList: boolean;
        nwCentering: number;
        neCentering: number;
        swCentering: number;
        seCentering: number;
        centered: number;
        density1km: number;
        hotspots1km: number;
        density5km: number;
        hotspots5km: number;
        density10km: number;
        hotspots10km: number;
        density30km: number;
        densityOver: number;
        competitors: number;
        avgWitComp: number;
        minWitComp: number;
        maxWitComp: number;
        denseArea: boolean;
        extendCoverage: boolean;
        lowCoverage: number;
        utilCoverage: number;
        participations: number;
        currentSelection: number;
        random14Selection: number;
        inTimeWindowsSelection: number;
        inTimeWindows14Selection: number;
        inExtendedTWSelection: number;
        maxDistance: number;
        brand: HotspotBrand; 
        travelTimeHist: RespTimeHist;
        arrivalPlaceHist: RespTimeHist;
    }


}