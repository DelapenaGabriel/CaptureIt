<template>
  <div class="text-reveal-container" :class="{ visible: isVisible }">
    <svg
      class="text-svg"
      viewBox="0 0 800 100"
      preserveAspectRatio="xMidYMid meet"
    >
      <text
        x="50%"
        y="50%"
        text-anchor="middle"
        dominant-baseline="middle"
        class="svg-text"
      >
        {{ text }}
      </text>
    </svg>
  </div>
</template>

<script>
export default {
  name: "TextReveal",
  props: {
    text: {
      type: String,
      required: true,
    },
    delay: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      isVisible: false,
    };
  },
  mounted() {
    // Simple intersection observer to trigger when in view
    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          setTimeout(() => {
            this.isVisible = true;
          }, this.delay);
          observer.disconnect();
        }
      });
    });

    observer.observe(this.$el);
  },
};
</script>

<style scoped>
.text-reveal-container {
  width: 100%;
  height: 100px; /* Adjust based on font size */
  display: flex;
  justify-content: center;
  align-items: center;
}

.svg-text {
  font-family: var(--font-script);
  font-size: 60px;
  fill: transparent;
  stroke: var(--color-charcoal);
  stroke-width: 1px;
  stroke-dasharray: 1000;
  stroke-dashoffset: 1000;
  transition:
    stroke-dashoffset 3s ease-in-out,
    fill 2s ease-in-out 1.5s;
}

/* Specific styling for the names logic if needed, or stick to generic */
.text-reveal-container.visible .svg-text {
  stroke-dashoffset: 0;
  fill: var(--color-charcoal);
}
</style>
