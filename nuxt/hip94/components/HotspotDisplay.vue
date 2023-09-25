<template>
    <div v-if="isInit">
      <b-overlay :show="isBusy" rounded="sm">
        <b-card 
            :header="hotspot.animalName"
            class="m-2 HotspotDetails"
        >
        <b-row>
            <b-col cols="6">
                <LeafletMap/>
            </b-col>
            <b-col cols="6">
                <b-row>
                </b-row>
                <b-row>
                </b-row>
            </b-col>
        </b-row>
        <b-row>
            <b-col cols="6">
                <apexchart type="scatter" height="200" :options="travelOptions" :series="travelData" ></apexchart>
            </b-col>
            <b-col cols="6">
                <apexchart type="scatter" height="200" :options="arrivalOptions" :series="arrivalData"></apexchart>
            </b-col>
        </b-row>
        </b-card>
      </b-overlay>

    </div>
</template>
<style>
.HotspotDetails .card-header {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
import Vue from 'vue'
import { HotspotLiteRespItf, Hotspot } from 'vue/types/hotspots';
import VueApexCharts from 'vue-apexcharts';


interface context {
  isInit : boolean,
  hotspot : HotspotLiteRespItf,
  hsFull : Hotspot,
  isBusy : boolean,
  travelOptions : {},
  travelData: any[],
  arrivalOptions : {},
  arrivalData: any[],
}


export default Vue.extend({
  name: 'HotspotDisplay',
  components: {
    apexchart: VueApexCharts,
  },
  data() : context {
    return {
      isInit : false,
      hotspot : undefined as HotspotLiteRespItf,
      hsFull : undefined as Hotspot,
      isBusy : false,
      travelOptions: {
        chart: { height: 200,type: 'scatter', zoom: { type: 'xy' } },
        dataLabels: { enabled: false },
        markers: {size : 1, },
        grid: { xaxis: { lines: { show: true } },
                yaxis: { lines: { show: true } },
             },
        xaxis: { type: 'datetime', },
        yaxis: { logarithmic: true, min : 0, max : 10000 },
        title: { text: "Travel Time", align: "left" },
      },
      arrivalOptions: {
        chart: { height: 200,type: 'scatter', zoom: { type: 'xy' } },
        dataLabels: { enabled: false },
        markers: {size : 1, colors :'#ea43de'},
        grid: { xaxis: { lines: { show: true } },
                yaxis: { lines: { show: true } },
             },
        xaxis: { type: 'datetime', },
        yaxis: { logarithmic: true, min : 0, max : 10000 },
        title: { text: "Arrival Time", align: "left" },
      },
      travelData: [],
      arrivalData: [],
    }
  },
  methods: {
  },
  mounted() {
    this.$root.$on("hotspot-selection", (msg:HotspotLiteRespItf) => {
        this.hotspot = msg;
        if ( this.isBusy ) return;
        this.isBusy = true;
        let config = {
            headers: {
            'Content-Type': 'application/json',
            }
        };
        this.$axios.get<Hotspot>(this.$config.apiHost+this.$config.getOneHotspot+''+this.hotspot.hotspotId+'/',config)
        .then((response) =>{
          if (response.status == 200 ) {
            this.hsFull = response.data;
            this.$root.$emit("hotspot-map-display", this.hsFull);
            // build travel & arrival Time graph
            this.travelData = [];
            this.travelData[0] = {
                name : "travel time (ms)",
                data : [],
            };
            this.arrivalData = [];
            this.arrivalData[0] = {
                name : "arrival time (ms)",
                data : [],
            };
            this.hsFull.witnesses.forEach ( w => {
                this.travelData[0].data.push([w.heliumRXTime, w.travelTime])
                this.arrivalData[0].data.push([w.heliumRXTime, w.deltaTime])
            });
            this.isBusy = false;
          } else {
            // 204 no entry
            this.hsFull = undefined;
            this.isBusy = false;
          }
        }).catch((err) =>{
          this.hsFull = undefined;
          this.isBusy = false;
        })
        this.isInit = true;
    });
  }
})

</script>