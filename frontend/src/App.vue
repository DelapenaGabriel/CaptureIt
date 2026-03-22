<template>
  <router-view />
  <MusicToggle />
</template>

<script>
import MusicToggle from "@/components/MusicToggle.vue";
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "App",
  components: { MusicToggle },
  setup() {
    const store = useWeddingStore();
    store.initMusic();
  },
};
</script>
