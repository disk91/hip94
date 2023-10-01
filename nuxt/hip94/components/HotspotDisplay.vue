<template>
    <div v-if="isInit">
      <b-overlay :show="isBusy" rounded="sm">
        <b-card 
            :header="getAnimalName()"
            class="m-2 HotspotDetails"
        >
        <b-row>
            <b-col cols="6">
                <p >
                    Hotspot PoC coverage
                </p>
                <LeafletMap/>
                <b-table striped hover :items="hsCaracteristics" style="font-size: 0.75rem;"></b-table>
            </b-col>
            <b-col cols="6">
                <b-row>
                    <b-col cols="6">
                      <apexchart type="donut" :options="hip83Option" :series="hip83Data"></apexchart>
                    </b-col>
                    <b-col cols="6">
                      <apexchart type="donut" :options="prehip83Option" :series="prehip83Data"></apexchart>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="6">
                      <apexchart type="donut" :options="win14Option" :series="win14Data"></apexchart>
                    </b-col>
                    <b-col cols="6">
                      <apexchart type="donut" :options="winOption" :series="winData"></apexchart>
                    </b-col>
                </b-row>
                <b-row class="my-2">
                    <b-col cols="12">
                        <apexchart type="bar" height="300" :options="hsAroundOptions" :series="hsAround"></apexchart>
                    </b-col>
                </b-row>
                <b-row class="my-2">
                    <b-col cols="12">
                        <apexchart type="bar" height="300" :options="hsDensityOptions" :series="hsDensity"></apexchart>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
        <b-row>
            <b-col cols="6" >
                <p class="mt-2 text-info">
                    Travel time is the time spent for the witness to be treated by the Oracle server, from the reception time as tagged by the hotspot. 
                    It includes the hotspot processing time, the network time, the oracle ingestion time.
                </p>
            </b-col>
            <b-col cols="6">
                <p class="mt-2 text-info">
                    Arrival time is the time difference between this hotspot witness compared to the first hotspot witness. This is a duration difference including difference is hotspot
                    processing, network transport, oracle ingestion. The arrival time is what matter on the current HIP-83 algorithm, first come first serve up to 14. It's also what matter 
                    with hip-94 proposed algorithm with a time window.
                </p>
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
        <b-row>
            <b-col cols="6">
                <apexchart type="bar" height="200" :options="travelHisOptions" :series="travelHisData"></apexchart>
            </b-col>
            <b-col cols="6">
                <apexchart type="bar" height="200" :options="arrivalHisOptions" :series="arrivalHisData"></apexchart>
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
  hsFull : Hotspot,
  isBusy : boolean,
  travelOptions : {},
  travelData: any[],
  arrivalOptions : {},
  arrivalData: any[],
  hip83Option: {},
  hip83Data: any[],
  prehip83Option: {},
  prehip83Data: any[],
  win14Option: {},
  win14Data: any[],
  winOption: {},
  winData: any[],
  travelHisOptions: {},
  travelHisData: any[],
  arrivalHisOptions: {},
  arrivalHisData: any[],
  hsCaracteristics: any[],
  hsAroundOptions: {},
  hsAround: any[],
  hsDensityOptions : {},
  hsDensity: any[],
}


export default Vue.extend({
  name: 'HotspotDisplay',
  components: {
    apexchart: VueApexCharts,
  },
  data() : context {
    return {
      isInit : false,
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
      hip83Option: {
        chart: { type: 'donut',},
        plotOptions: { pie: { startAngle: -90, endAngle: 90, offsetY: 10 } },
        grid: { padding: { bottom: -100 } },
        responsive: [{ breakpoint: 480, options: { chart: { width: 200 },legend: { position: 'bottom' } }}],
        labels: ['Selected','Not Selected'],
        colors:['#017b04', '#8b0202'],
        title: { text:"HIP83 Selection", align: "left" },
        legend: { position: 'bottom', },
      },
      hip83Data:[],
      prehip83Option: {
        chart: { type: 'donut',},
        plotOptions: { pie: { startAngle: -90, endAngle: 90, offsetY: 10 } },
        grid: { padding: { bottom: -100 } },
        responsive: [{ breakpoint: 480, options: { chart: { width: 200 },legend: { position: 'bottom' } }}],
        labels: ['Selected','Not Selected'],
        colors:['#017b04', '#8b0202'],
        title: { text:"Pre-HIP83 Selection (Random 14)", align: "left" },
        legend: { position: 'bottom', },
      },
      prehip83Data:[],
      win14Option: {
        chart: { type: 'donut',},
        plotOptions: { pie: { startAngle: -90, endAngle: 90, offsetY: 10 } },
        grid: { padding: { bottom: -100 } },
        responsive: [{ breakpoint: 480, options: { chart: { width: 200 },legend: { position: 'bottom' } }}],
        labels: ['Selected','Not Selected'],
        colors:['#017b04', '#8b0202'],
        title: { text:"Window Selection (with random 14)", align: "left" },
        legend: { position: 'bottom', },
      },
      win14Data:[],
      winOption: {
        chart: { type: 'donut',},
        plotOptions: { pie: { startAngle: -90, endAngle: 90, offsetY: 10 } },
        grid: { padding: { bottom: -100 } },
        responsive: [{ breakpoint: 480, options: { chart: { width: 200 },legend: { position: 'bottom' } }}],
        labels: ['In time Window','In Extended TW','Not Selected'],
        colors:['#017b04', '#017b04', '#8b0202'],
        title: { text:"Window Selection (all)", align: "left" },
        legend: { position: 'bottom', },
      },
      winData:[],
      travelHisOptions : {
            chart: { type: 'bar', height: 200, stacked: true, stackType: '100%'},
            plotOptions: { bar: { horizontal: true, }, },
            stroke: { width: 1, colors: ['#fff'] },
            title: { text: 'Travel time distribution' },
            fill: { opacity: 1 },
            legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 }
      },
      travelHisData: [],
      arrivalHisOptions : {
            chart: { type: 'bar', height: 200, stacked: true, stackType: '100%'},
            plotOptions: { bar: { horizontal: true, }, },
            stroke: { width: 1, colors: ['#fff'] },
            title: { text: 'Arrival time distribution' },
            fill: { opacity: 1 },
            legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 }
      },
      arrivalHisData: [],
      hsCaracteristics : [],
      hsAroundOptions: {
            chart: { type: 'bar', height: 300 },
            plotOptions: { bar: { borderRadius: 4, horizontal: false, } },
            dataLabels: { enabled: false },
            title: { text: 'Distribution of Hotspot Around (including the active it is not interacting with)' },
            xaxis: {
              categories: ['0-1km', '1-5km', '5-10km' ],
            }
      },
      hsAround: [],
      hsDensityOptions: {
            chart: { type: 'bar', height: 300 },
            plotOptions: { bar: { borderRadius: 4, horizontal: false, } },
            dataLabels: { enabled: false },
            title: { text: 'Distribution of interacting Hotspot Around vs Existing (0-10km)' },
            xaxis: {
              categories: ['0-1km', '1-5km', '5-10km', '10-30km', '> 30km' ],
            },
      },
      hsDensity: [],
    }
  },
  methods: {
    getAnimalName() : string {
        return ( this.hsFull != undefined )?this.hsFull.animalName:"Unknown";
    },
    updateHotspot(hsid:string) {
        if ( this.isBusy ) return;
        this.isBusy = true;
        let config = {
            headers: {
            'Content-Type': 'application/json',
            }
        };
        this.$axios.get<Hotspot>(this.$config.apiHost+this.$config.getOneHotspot+''+hsid+'/',config)
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
            // show selection ratio on different type
            this.hip83Data=[this.hsFull.currentSelection, (this.hsFull.participations - this.hsFull.currentSelection)];
            this.prehip83Data=[this.hsFull.random14Selection, (this.hsFull.participations - this.hsFull.random14Selection)];
            this.win14Data=[this.hsFull.inTimeWindows14Selection, (this.hsFull.participations - this.hsFull.inTimeWindows14Selection)];
            this.winData=[this.hsFull.inTimeWindowsSelection, this.hsFull.inExtendedTWSelection, (this.hsFull.participations - this.hsFull.inTimeWindowsSelection - this.hsFull.inExtendedTWSelection)];
            // show travel & arrival time distributino
            this.travelHisData = [
                { name: '< 10ms ', data : [ this.hsFull.travelTimeHist.lower10ms ] },
                { name: '< 30ms ', data : [ this.hsFull.travelTimeHist.lower30ms ] },
                { name: '< 50ms ', data : [ this.hsFull.travelTimeHist.lower50ms ] },
                { name: '< 100ms ', data : [ this.hsFull.travelTimeHist.lower100ms ] },
                { name: '< 250ms ', data : [ this.hsFull.travelTimeHist.lower250ms ] },
                { name: '< 500ms ', data : [ this.hsFull.travelTimeHist.lower500ms ] },
                { name: '< 1s ', data : [ this.hsFull.travelTimeHist.lower1s ] },
                { name: '< 2s ', data : [ this.hsFull.travelTimeHist.lower2s ] },
                { name: '< 3s ', data : [ this.hsFull.travelTimeHist.lower3s ] },
                { name: '> 3s ', data : [ this.hsFull.travelTimeHist.over3s ] },
                ];
            this.arrivalHisData = [
                { name: '< 10ms ', data : [ this.hsFull.arrivalPlaceHist.lower10ms ] },
                { name: '< 30ms ', data : [ this.hsFull.arrivalPlaceHist.lower30ms ] },
                { name: '< 50ms ', data : [ this.hsFull.arrivalPlaceHist.lower50ms ] },
                { name: '< 100ms ', data : [ this.hsFull.arrivalPlaceHist.lower100ms ] },
                { name: '< 250ms ', data : [ this.hsFull.arrivalPlaceHist.lower250ms ] },
                { name: '< 500ms ', data : [ this.hsFull.arrivalPlaceHist.lower500ms ] },
                { name: '< 1s ', data : [ this.hsFull.arrivalPlaceHist.lower1s ] },
                { name: '< 2s ', data : [ this.hsFull.arrivalPlaceHist.lower2s ] },
                { name: '< 3s ', data : [ this.hsFull.arrivalPlaceHist.lower3s ] },
                { name: '> 3s ', data : [ this.hsFull.arrivalPlaceHist.over3s ] },
            ];
            // Hostpot Characteristics
            var centered:string = (this.hsFull.centered == 0)?'Undetermined':((this.hsFull.centered == 1)?'Yes':'No');
            var lowCoverage:string = (this.hsFull.lowCoverage == 0)?'Undetermined':((this.hsFull.lowCoverage == 1)?'Yes':'No');
            var utilCoverage:string = (this.hsFull.utilCoverage == 0)?'Undetermined':((this.hsFull.utilCoverage == 1)?'Yes':'No');
            this.hsCaracteristics = [
                { Insight: 'Number of beaconner around', Value: this.hsFull.competitors, Description: 'This is the number of different hospots source of beacon' },
                { Insight: 'Average / Max Witness competitor', Value: Math.floor(this.hsFull.avgWitComp) +' / '+ this.hsFull.maxWitComp, Description: 'This is the average and the maximum number of other Hotspots contributing to the same PoC' },
                { Insight: 'Max proven distance in km', Value: Math.floor(this.hsFull.maxDistance / 1000)+' km', Description: 'This is the max coverage distance proven from the PoC mechanism. The longer distance to a hotspot involved in same PoC'},
                { Insight: 'Hostpot brand', Value: this.hsFull.brand, Description: 'The hotspot manufacturer when known'},
                { Insight: 'In deny list', Value: this.hsFull.inDenyList, Description: 'The hotspot was in deny list at the period of the analysis'},
                { Insight: 'Centered', Value: centered, Description: 'When more than 10 witness, a hotspot with less than 50 hostpot in all thz directions and a N-S or E-W hotspot ratio unbalanced is excentred. Non centered hotspot extends the network.'},
                { Insight: 'In a dense Area', Value: this.hsFull.denseArea, Description: 'The hostpot is in a dense area when more than 10 hostpots are interracting in the 1km around'},
                { Insight: 'Low Coverage', Value: lowCoverage, Description: 'The hotspot is low coverage when there are hotspots >1km around but no interaction with them'},
                { Insight: 'Valuable Coverage', Value: utilCoverage, Description: 'The hotspot coverage is valuable when reduncancy around is under 3 or not centered (to be improved)'},
            ];
            // Hotspot around (remove itself)
            this.hsAround = [ {
                data : [this.hsFull.hotspots1km-1, this.hsFull.hotspots5km, this.hsFull.hotspots10km]
            }];
            this.hsDensity = [ {
                name : 'Interacting hotspots',
                data : [this.hsFull.density1km, this.hsFull.density5km, this.hsFull.density10km, this.hsFull.density30km, this.hsFull.densityOver ]
            },{
                name: 'Active hotspot',
                data : [this.hsFull.hotspots1km-1, this.hsFull.hotspots5km, this.hsFull.hotspots10km, 0, 0]
            }
            ];


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
    }
  },
  mounted() {
    this.$root.$on("hotspot-update", (msg:string) => {
        this.updateHotspot(msg);
    });
  }
})

</script>