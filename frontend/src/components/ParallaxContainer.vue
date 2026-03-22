<template>
  <div class="parallax-layer" :style="layerStyle">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "ParallaxContainer",
  props: {
    speed: {
      type: Number,
      default: 0.5,
    },
  },
  data() {
    return {
      scrollY: 0,
    };
  },
  computed: {
    layerStyle() {
      return {
        transform: `translateY(${this.scrollY * this.speed}px)`,
      };
    },
  },
  methods: {
    handleScroll() {
      this.scrollY = window.scrollY;
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  },
};
</script>

<style scoped>
.parallax-layer {
  position: relative;
  transition: transform 0.1s linear; /* Smooth out small jitters */
  will-change: transform;
  z-index: 1;
}
</style>
