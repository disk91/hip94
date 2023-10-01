<template>
  <div>
    <b-navbar toggleable="lg" type="primary" variant="primary" class="py-0">
      <img src="/static/front/helium_logo.svg" style="width: 22px; position: relative; top: 0px ; left: -5px; ; margin-right: 4px;"/>
      <b-navbar-brand to="/">
        <span class="text-light" style="font-size:1.2rem;height:30px;float:left;clear:left;font-weight: 400;position:relative; top:0px;">Hip94 Viewer</span>
      </b-navbar-brand>
      <b-navbar-nav class="ml-auto">
        <multiselect
          v-model="search_hs"
          class="mr-sm-2 my-2"
          placeholder="Search Hotspot"
          :options="search_results"
          :close-on-select="true"
          :clear-on-select="false"
          :taggable="true"
          :max-height="200"
          :custom-label="getLabel"
          :loading="isBusy"
          @search-change="onSearchChange"
          @select="onSelectHotspot"
          style="width:320px;"
        >
        </multiselect>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Multiselect from 'vue-multiselect';
import { HotspotLiteRespItf } from 'vue/types/hotspots';
import L from 'leaflet';
import leafletPip from '@mapbox/leaflet-pip';
import countries from '~/static/static/front/world.geo.json';
import CountriesService from '@/services/countries';


import { config } from 'vue/types/umd'
import { LayoutPlugin } from 'bootstrap-vue';


interface context {
  search_hs : HotspotLiteRespItf,
  isBusy : boolean,
  search_results : HotspotLiteRespItf[],
  countriesService : CountriesService,
}

export default Vue.extend({
  name: 'NavBar',
  // cf https://vue-multiselect.js.org/
  components: { Multiselect },
  data() : context {
    return {
      search_hs : undefined as HotspotLiteRespItf,
      isBusy : false,
      search_results: [],
      countriesService : new CountriesService(),
    }
  },
  methods: {
    findCountry(lat:number, lng:number):string {
      const layer = L.geoJSON(countries);
      const results = leafletPip.pointInLayer([lng, lat], layer);
      let country:string = '';
      if (results && results.length) {
        country = results[0].feature.id;
        country = this.countriesService.getCountryISO2(country);
        country = country.toUpperCase().replace(/./g, char => 
            String.fromCodePoint(127397 + char.charCodeAt(0)));
      } else {
        country = '';
      }
      return country;
    },
    onSelectHotspot() {
        this.$root.$emit("hotspot-selection", this.search_hs);
    },
    onSearchChange(searchText:string) {
      if ( this.isBusy ) return;
      if ( searchText.length < 3 ) return;

      this.isBusy = true;
      let config = {
        headers: {
          'Content-Type': 'application/json',
        }
      };
      this.search_results = [];
      let animalName = searchText.replace(/ /g,"-");
      this.$axios.get<HotspotLiteRespItf[]>(this.$config.apiHost+this.$config.searchHotspot+''+animalName+'/',config)
        .then((response) =>{
          if (response.status == 200 ) {
            response.data.forEach( element => {
              this.search_results.push(element)
            });
            this.isBusy = false;
          } else {
            // 204 no entry
            this.search_results = [];
            this.isBusy = false;
          }
        }).catch((err) =>{
          this.search_results = [];
          this.isBusy = false;
        })
    },
    getLabel(entry:HotspotLiteRespItf) : string {
      if ( entry != undefined && entry.position != undefined ) {
         return entry.animalName + ' ' + this.findCountry(entry.position.lat,entry.position.lng);
      } else return '';
    }
  },

})

</script>
<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>