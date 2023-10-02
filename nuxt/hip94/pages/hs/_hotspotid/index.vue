<template>
  <div>
    <Navbar />
    <HotspotDisplay/>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  name: 'IndexPage',
  data() :  any {
    return {
    }
  },
  methods: {
  },
  mounted() {
    setTimeout(() => {
      // make sure reception is ready...
      this.$root.$emit("hotspot-update", this.$route.params.hotspotid);
    }, 200);
  },
})
</script>

