<template>
    <div>
        <b-row>
            <b-col cols="9" style="padding-right: 0px;">
                <div id="mapfront" :style="{ height: '600px' }"></div>
            </b-col>
            <b-col cols="3" style="padding: 5px 20px 1px 5px;">
                <b-card class="mb-2" style="font-size:0.7rem;">
                    <b-form-group label="Hip view (compared to random-14)">
                    <b-form-radio-group
                        id="hip-group-1"
                        v-model="hipSelection"
                        @change="onChangeHipSelection"
                    >
                        <b-form-radio value="83">Hip-83 winners</b-form-radio>
                        <b-form-radio value="94">Hip-94 winners</b-form-radio>
                    </b-form-radio-group>
                    </b-form-group>
                    <b-form-group label="Filters (keep checked)">
                        <b-form-checkbox-group
                            id="brand-filters"
                            v-model="brandSel"
                            :options="brandOptions"
                            @change="onChangeFilterSelection"
                        ></b-form-checkbox-group>
                    </b-form-group>
                    <b-form-group>
                        <b-form-checkbox-group
                            id="hotspot-filters"
                            v-model="filterSel"
                            :options="filterOptions"
                            @change="onChangeFilterSelection"
                        ></b-form-checkbox-group>
                    </b-form-group>
                </b-card>
                <b-card 
                    v-for="hotspot in visibleHs" :key="hotspot.hotspot.hotspotId" 
                    :class="hotspot.style"
                    @click="onCardClick(hotspot)"
                >
                <b-card-title style="font-size: 1.2rem;margin-bottom:4px;font-variant: small-caps;">
                    {{ hotspot.hotspot.animalName }}
                </b-card-title>
                <b-card-body style="padding: 0px 0px 0px 0px;font-size: 0.7rem;">
                        {{ hotspot.hotspot.brand }}<br/>
                        Rand-14: {{ Math.floor(100*hotspot.hotspot.random14Selection / hotspot.hotspot.participations ) }}%
                        <div :class="getStyleHip83(hotspot.hotspot)" style="display: inline-block;">Hip-83: {{ Math.floor(100*hotspot.hotspot.currentSelection / hotspot.hotspot.participations ) }}%</div>
                        <div :class="getStyleHip94(hotspot.hotspot)" style="display: inline-block;">Win-14: {{ Math.floor(100*hotspot.hotspot.inTimeWindows14Selection / hotspot.hotspot.participations ) }}%</div>
                        Win-All: {{ Math.floor(100*(hotspot.hotspot.inTimeWindowsSelection+hotspot.hotspot.inExtendedTWSelection) / hotspot.hotspot.participations ) }}%
                </b-card-body>
                </b-card>
            </b-col>
        </b-row>
    </div>
</template>
<style>
.HsList .card-body {
    padding : 5px 5px 5px 7px;
}
</style>

<script lang="ts">
    import Vue from 'vue';
    import L from 'leaflet';
    import { Hotspot } from 'vue/types/hotspots';
    import { cellToLatLng } from 'h3-js';
    import { HotspotLiteRespItf } from 'vue/types/hotspots';

    interface context {
        map : any,
        markers : any[],
        isDestroyed : boolean,
        isBusy: boolean,
        center : [number, number],
        currentBounds : { northLat : number, southLat : number, westLon : number, eastLon : number },
        currentHs : HotspotLiteRespItf[],
        visibleHs : VisibleHs[],
        greenIcon : any,
        redIcon : any,
        blueIcon : any,
        greyIcon : any,
        commonStyle : string,
        coverage : any,
        hipSelection : number,
        brandSel :any[],
        brandOptions : any[],
        filterSel :any[],
        filterOptions : any[],
    }

    interface VisibleHs {
        hotspot : HotspotLiteRespItf,
        style : string,
    }

    export default Vue.extend({
        name: 'NavMap',
        data() : context {
            return {
                map : L.Map,
                markers : [],
                isDestroyed : true,
                isBusy: false,
                center: [45.8, 3.1],
                currentBounds : { northLat : 0, southLat: 0, westLon: 0, eastLon : 0 },
                currentHs : [],
                visibleHs : [],
                greyIcon : undefined,
                greenIcon : undefined,
                redIcon : undefined,
                blueIcon : undefined,
                commonStyle : ' HsList',
                coverage : undefined,
                hipSelection : 83,
                filterSel : ['CENTERED', 'NOTCENTERED', 'DENSE', 'NOTDENSE', 'EXTCOV', 'NOEXTCOV', 'LOWCOV', 'NOLOWCOV' ],
                filterOptions: [
                    { text: 'centered', value : 'CENTERED' },
                    { text: 'peripherally', value : 'NOTCENTERED' },
                    { text: 'dense area', value : 'DENSE' },
                    { text: 'non dense area', value : 'NOTDENSE' },
                    { text: 'extends cov', value : 'EXTCOV' },
                    { text: 'not extends cov', value : 'NOEXTCOV' },
                    { text: 'low cov', value : 'LOWCOV' },
                    { text: 'not low cov', value : 'NOLOWCOV' },
                ],
                brandSel : [ 
                    'ALL'
                ],
                brandOptions : [
                    { text: 'all brand', value : 'ALL' },
                    { text: 'unknown brand', value : 'UNKNOWN' },
                    { text: 'diy / leg', value : 'HELIUM' },
                    { text: 'rak', value : 'RAK' },
                    { text: 'Nebra', value : 'NEBRA' },
                    { text: 'Kerlink', value : 'KERLINK' },
                    { text: 'Cotx', value : 'COTX' },
                    { text: 'Sensecap', value : 'SENSECAP' },
                    { text: 'Synchrobit', value : 'SYNCROBIT' },
                    { text: 'Bobcat', value : 'BOBCAT' },
                    { text: 'LongAp', value : 'LONGAP' },
                    { text: 'Smartmimic', value : 'SMARTMIMIC' },
                    { text: 'Calchip', value : 'CALCHIP' },
                    { text: 'Dewi', value : 'DEWI' },
                    { text: 'Pisces', value : 'PISCES' },
                    { text: 'CloudPi', value : 'CLODPI' },
                    { text: 'LinxDot', value : 'LINXDOT' },
                    { text: 'Controllino', value : 'CONTROLLINO' },
                    { text: 'Heltec', value : 'HELTEC' },
                    { text: 'FreedomFi', value : 'FREEDOMFI' },
                    { text: 'PantherX', value : 'PANTHERX' },
                    { text: 'Hummingbird', value : 'HUMMINGBIRD' },
                    { text: 'RisingHF', value : 'RISINGHF' },
                    { text: 'Browan', value : 'BROWAN' },
                    { text: 'Deeper', value : 'DEEPER' },
                    { text: 'Midas', value : 'MIDAS' },
                    { text: 'Dragino', value : 'DRAGINO' },
                    { text: 'Pycom', value : 'PYCOM' },
                ],

            }
        },
        methods: {
            initMap() {
                // cf - https://github.com/pointhi/leaflet-color-markers
                if ( this.isDestroyed ) { 
                  this.map = L.map('mapfront').setView(this.$store.state.centerPosition, this.$store.state.zoom); /* 4 */  
                  this.isDestroyed = false;
                }
                // L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
                L.tileLayer('https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png'+this.$config.mapApiKey, {
                    maxZoom: 19,
                    attribution: '&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
                   // attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(this.map!);
                this.map.on('moveend', this.onMapPositionUpdated);
                this.onMapPositionUpdated();
            },
            onMapPositionUpdated() {
                if ( this.isBusy ) return; // skip reentering event
                this.$store.commit('setCenterPosition', this.map.getCenter());
                this.$store.commit('setZoom', this.map.getZoom());
                if ( this.map.getZoom() > 11 ) {
                    // is that in the same previsou loaded zone ?
                    const bounds = this.map.getBounds();
                    const northEast = bounds.getNorthEast();
                    const southWest = bounds.getSouthWest();
                    if ( this.currentBounds.northLat < northEast.lat || this.currentBounds.southLat > southWest.lat
                      || this.currentBounds.westLon > southWest.lng || this.currentBounds.eastLon < northEast.lng ) {
                        this.$root.$emit("busy-search", null);
                        this.isBusy = true;
                        // Update area
                        this.currentBounds.northLat = northEast.lat;
                        this.currentBounds.southLat = southWest.lat;
                        this.currentBounds.westLon = southWest.lng;
                        this.currentBounds.eastLon = northEast.lng;
                        // New Area load Hotspot in that area
                        let config = {
                            headers: {
                                'Content-Type': 'application/json',
                            }
                        };
                        this.$axios.get<HotspotLiteRespItf[]>(this.$config.apiHost+this.$config.searchHsByZone+'/'+this.currentBounds.northLat+'/'+this.currentBounds.southLat+'/'+this.currentBounds.westLon+'/'+this.currentBounds.eastLon+'/',config)
                        .then((response) =>{
                            if (response.status == 200 ) {
                                this.removeHss(false);
                                this.addHss(response.data);
                                this.refreshVisibleHsList(northEast.lat,southWest.lat,southWest.lng,northEast.lng);
                                this.$root.$emit("busy-search-end", null);
                                this.isBusy = false;
                            } else {
                                // 204 no entry
                                this.removeHs();
                                this.$root.$emit("busy-search-end", null);
                                this.isBusy = false;
                            }
                        }).catch((err) =>{
                            this.$root.$emit("busy-search-end", null);
                            this.isBusy = false;
                        })
                    } else {
                        this.refreshVisibleHsList(northEast.lat,southWest.lat,southWest.lng,northEast.lng);
                    }
                }
            },
            removeHss(justMarker : boolean) {
                // remove the marker from the map & hotspot list
                this.markers.forEach( m => {
                    m.remove();
                });
                this.markers = [];
                if ( !justMarker ) {
                  this.currentHs = [];
                  this.visibleHs = [];
                }
            },
            addOneHs(hs : HotspotLiteRespItf) {
                    // filters
                    var filtered : boolean = false;
                    if ( !this.verifyBrandSel(hs.brand) ) filtered = true;
                    else {
                        if ( !this.verifyFilterSel('CENTERED') || !this.verifyFilterSel('NOTCENTERED') ) {
                            if ( this.verifyFilterSel('CENTERED') && hs.centered == 2 ) filtered = true;
                            if ( this.verifyFilterSel('NOTCENTERED') && hs.centered == 1 ) filtered = true;
                            if ( hs.centered == 0 ) filtered = true;
                        }
                        if ( !this.verifyFilterSel('DENSE') || !this.verifyFilterSel('NOTDENSE') ) {
                            if ( this.verifyFilterSel('DENSE') && !hs.denseArea ) filtered = true;
                            if ( this.verifyFilterSel('NOTDENSE') && hs.denseArea ) filtered = true;
                        }
                        if ( !this.verifyFilterSel('EXTCOV') || !this.verifyFilterSel('NOEXTCOV') ) {
                            if ( this.verifyFilterSel('EXTCOV') && !hs.extendCoverage ) filtered = true;
                            if ( this.verifyFilterSel('NOEXTCOV') && hs.extendCoverage ) filtered = true;
                        }
                        if ( !this.verifyFilterSel('LOWCOV') || !this.verifyFilterSel('NOLOWCOV') ) {
                            if ( this.verifyFilterSel('LOWCOV') && hs.lowCoverage == 2 ) filtered = true;
                            if ( this.verifyFilterSel('NOLOWCOV') && hs.lowCoverage == 1 ) filtered = true;
                            if ( hs.lowCoverage == 0 ) filtered = true;
                        }
                    }

                    if ( !filtered ) {
                        // color
                        var ref : number = hs.random14Selection / hs.participations;
                        var hip83 : number = hs.currentSelection / hs.participations;
                        var hip94 : number = hs.inTimeWindows14Selection / hs.participations;
                        var color : any = this.blueIcon; 
                        if ( this.hipSelection == 83 ) {
                            if  (hip83 >= (ref+(ref*0.2))) {
                                color = this.greenIcon;
                            } else if ( hip83 <= (ref-(ref*0.2)) ) {
                                color = this.redIcon;
                            }
                        }
                        if ( this.hipSelection == 94 ) {
                            if  (hip94 >= (ref+(ref*0.2))) {
                                color = this.greenIcon;
                            } else if ( hip94 <= (ref-(ref*0.2)) ) {
                                color = this.redIcon;
                            }
                        }
                    } else color = this.greyIcon;

                    // add some random to display multiple hs in the same hex
                    var randomPos : {lat : number, lng : number} = this.getRandomPosFrom(hs.position.lat,hs.position.lng);
                    var marker = L.marker([ randomPos.lat, randomPos.lng ],{icon: color, customData : { id : hs.hotspotId }}).addTo(this.map!);
                    marker.bindTooltip(hs.animalName);
                    marker.on('mouseover', this.onMouseOverMarker);
                    marker.on('mouseout', this.onMouseOutMarker);
                    marker.on('click',this.onClickMarker)
                    this.markers.push(marker); 

            },
            addHss(hss : HotspotLiteRespItf[]) {
                hss.forEach( hs => {
                    this.addOneHs(hs);
                });
                this.currentHs = hss;
            }, 
            refreshVisibleHsList(nlat : number, slat: number, wlng : number, elng : number){
                this.visibleHs = [];
                this.currentHs.forEach( hs => {
                    if ( hs.position.lat > slat && hs.position.lat < nlat
                      && hs.position.lng > wlng && hs.position.lng < elng ) {
                        this.visibleHs.push({ hotspot : hs, style : 'bg-light text-primary'+this.commonStyle });
                    }
                });
            },
            getRandomPosFrom(lat :number, lng : number) : {lat : number, lng : number} {
                var rlng = ((Math.random() * 20) - 10) / 10000.0;
                var rlat = ((Math.random() * 20) - 10) / 10000.0;
                return { lat : lat+rlat, lng : lng+rlng };
            },
            onMouseOverMarker(ev:any){
                var found : VisibleHs = undefined;
                var tVHs : VisibleHs[] = [];
                this.visibleHs.forEach( vh => {
                    if ( vh.hotspot.hotspotId == ev.target.options.customData.id ) {
                        vh.style='bg-dark text-white'+this.commonStyle;
                        found = vh;
                    } else {
                        vh.style='bg-light text-primary'+this.commonStyle;
                        tVHs.push(vh);
                    }
                });
                if ( found != undefined ) {
                    tVHs.unshift(found);
                    this.visibleHs = tVHs;
                    // display coverage
                    if ( this.coverage != undefined ) {
                        this.coverage.remove();
                    }
                    this.coverage = L.circle([found.hotspot.position.lat, found.hotspot.position.lng ], {
                        radius: found.hotspot.maxDistance,
                        color: 'green',       
                        fillColor: 'green',   
                        fillOpacity: 0.1, 
                    }).addTo(this.map);
                }

            },
            onMouseOutMarker(ev:any) {
                if ( this.coverage != undefined ) {
                   this.coverage.remove();
                   this.coerage = undefined;
                }
            },
            onClickMarker(ev:any) {
                this.$root.$emit("hotspot-selection-id", ev.target.options.customData.id);
            },
            onCardClick(hs:VisibleHs) {
                this.$root.$emit("hotspot-selection-id", hs.hotspot.hotspotId);
            },
            getStyleHip83(h:HotspotLiteRespItf) :string {
                var ref : number = Math.floor(100*h.random14Selection / h.participations );
                var hip83 : number = Math.floor(100*h.currentSelection / h.participations );
                return ( hip83 >= ref )?'text-success':'text-danger';
            },
            getStyleHip94(h:HotspotLiteRespItf) :string {
                var ref : number = Math.floor(100*h.random14Selection / h.participations );
                var hip94 : number = Math.floor(100*h.inTimeWindows14Selection / h.participations );
                return ( hip94 >= ref )?'text-success':'text-danger';
            },
            onChangeHipSelection() {
                this.removeHss(true);
                this.addHss(this.currentHs);
            },
            onChangeFilterSelection() {
                this.removeHss(true);
                this.addHss(this.currentHs);
            },
            verifyBrandSel(b : string) : boolean {
                // return true if the brand is in the selected one
                for ( var i = 0 ; i < this.brandSel.length ; i++ ) {
                    if ( this.brandSel[i] == 'ALL' ) {
                        return true;
                    }
                    if ( this.brandSel[i].toLowerCase() == b.toLowerCase() ) return true;
                }
                return false;
            },
            verifyFilterSel(o : string) : boolean {
                // return true if the option is selected
                for ( var i = 0 ; i < this.filterSel.length ; i++ ) {
                    if ( this.filterSel[i].toLowerCase() == o.toLowerCase() ) {
                        return true;
                    }
                }    
                return false;
            }
        },
        mounted() {
            this.initMap();
            this.greenIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });
            this.redIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });
            this.blueIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-blue.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });
            this.greyIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-grey.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
            });
        },
        beforeDestroy() {
            if (this.map && !this.isDestroyed ) {
               this.map.remove();
               this.isDestroyed = true;
            }
        },
    })
</script>

