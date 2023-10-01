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
    this.$root.$emit("hotspot-update", this.$route.params.hotspotid);
  },
})
</script>

