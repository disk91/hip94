<template>
  <div>
    <b-overlay :show="isBusy" rounded="sm">
    <Navbar />
    <NavMap />
    </b-overlay>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

interface context {
  isBusy : boolean,
}


export default Vue.extend({
  name: 'IndexPage',
  data() : context {
    return {
      isBusy : false,
    }
  },
  methods: {
  },
  mounted() {
    this.$root.$on("hotspot-selection", (msg:any) => {
      this.isBusy = true;
      this.$router.push('/hotspot/'+msg.hotspotId+'/');
    });
    this.$root.$on("hotspot-selection-id", (msg:any) => {
      this.isBusy = true;
      this.$router.push('/hotspot/'+msg+'/');
    });
    this.$root.$on("busy-search", (msg:any) => {
      this.isBusy = true;
    });
    this.$root.$on("busy-search-end", (msg:any) => {
      this.isBusy = false;
    });
  },
})
</script>

