<template>
    <div id="map" :style="{ height: '400px' }">
        
    </div>
</template>

<script lang="ts">
    import Vue from 'vue';
    import L from 'leaflet';
    import { Hotspot } from 'vue/types/hotspots';
    import { cellToLatLng } from 'h3-js';


    interface context {
        map : any,
        markers : any[],
        isDestroyed : boolean,
        currentHs : Hotspot,
    }

    export default Vue.extend({
        name: 'LeafletMap',
        data() : context {
            return {
                map : undefined,
                markers : [],
                isDestroyed : true,
                currentHs : undefined,
            }
        },
        methods: {
            drawMap(msg:Hotspot) {
                // cf - https://github.com/pointhi/leaflet-color-markers
                var greenIcon = new L.Icon({
                    iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-green.png',
                    shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                    iconSize: [12, 20],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [10, 10]
                });

                if ( ! this.map ) {
                   this.map = L.map('map').setView([msg.position.lat, msg.position.lng], 11);
                } else {
                   this.map.setView([msg.position.lat, msg.position.lng], 11);  
                }
                this.isDestroyed = false;
                // L.tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
                L.tileLayer('https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png'+this.$config.mapApiKey, {
                    maxZoom: 19,
                    attribution: '&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
                   // attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }).addTo(this.map!);
                this.markers.push(L.marker([msg.position.lat, msg.position.lng]).addTo(this.map!));

                var uniqW : boolean[] = [];
                msg.witnesses.forEach( w => {
                    if ( w.h3hex != null && uniqW[w.h3hex] == undefined ) {
                        var h3hex : string = parseInt(w.h3hex,10).toString(16);
                        const [ lat, lng ] : [number,number] = cellToLatLng(h3hex);
                        // const [ lat, lng ] : [number,number] = h3ToGeo(w.h3hex);
                        var marker = L.marker([ lat, lng ],{icon: greenIcon}).addTo(this.map!);
                        // We don't have the hs name 
                        // marker.bindTooltip(w.hotspotId);
                        this.markers.push(marker); 
                    }
                    if ( w.h3hex != null ) {
                        uniqW[w.h3hex] = true; 
                    }                    
                }); 
                this.currentHs = msg;
            }
        },
        mounted() {
            console.log("mounted LeafMap");
            this.$root.$on("hotspot-map-display", (msg:Hotspot) => {
                this.drawMap(msg);
            });
            if ( this.currentHs != undefined ) {
                // manage reload for development phase
                this.drawMap(this.currentHs);
            }
        },
        beforeDestroy() {
            /*
            if (this.map && !this.isDestroyed ) {
               this.map.remove();
               this.map = undefined;
               this.isDestroyed = true;
            }
            */
        },
    })
</script>