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


interface context {
  isInit : boolean,
  hotspot : HotspotLiteRespItf,
  hsFull : Hotspot,
  isBusy : boolean,
}

export default Vue.extend({
  name: 'HotspotDisplay',
  data() : context {
    return {
      isInit : false,
      hotspot : undefined as HotspotLiteRespItf,
      hsFull : undefined as Hotspot,
      isBusy : false,
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