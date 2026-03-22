<template>
  <div class="timeline section">
    <h2 class="section-title" data-aos="fade-up">Our Day Together</h2>
    <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>

    <div class="timeline-track">
      <div class="timeline-line"></div>

      <div
        v-for="(event, index) in store.timelineEvents"
        :key="index"
        class="timeline-item"
        :class="{ right: index % 2 !== 0 }"
        data-aos="fade-up"
        :data-aos-delay="200 + index * 150"
      >
        <div class="timeline-dot"></div>
        <div class="timeline-content">
          <span class="timeline-time">{{ event.time }}</span>
          <div class="timeline-icon">{{ event.icon }}</div>
          <h3 class="timeline-title">{{ event.title }}</h3>
          <p class="timeline-desc">{{ event.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "TimelineSection",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
};
</script>

<style scoped>
.timeline {
  text-align: center;
  max-width: 800px;
  margin: 0 auto;
}

.section-title {
  font-family: var(--font-serif);
  font-size: clamp(1.5rem, 4vw, 2.5rem);
  color: #f0f0ee;
  letter-spacing: 0.05em;
}

.timeline-track {
  position: relative;
  margin-top: 3rem;
  padding: 2rem 0;
}

.timeline-line {
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  width: 1px;
  background: linear-gradient(
    to bottom,
    transparent,
    rgba(212, 185, 120, 0.8),
    rgba(212, 185, 120, 0.8),
    transparent
  );
  transform: translateX(-50%);
  box-shadow: 0 0 15px rgba(212, 185, 120, 0.4);
}

.timeline-item {
  position: relative;
  display: flex;
  justify-content: flex-end;
  padding-right: calc(50% + 2rem);
  margin-bottom: 3rem;
}

.timeline-item.right {
  justify-content: flex-start;
  padding-right: 0;
  padding-left: calc(50% + 2rem);
}

.timeline-dot {
  position: absolute;
  left: 50%;
  top: 1rem;
  width: 12px;
  height: 12px;
  background: #c5a059;
  border-radius: 50%;
  transform: translateX(-50%);
  box-shadow: 0 0 12px rgba(197, 160, 89, 0.4);
  z-index: 2;
}

.timeline-content {
  text-align: center;
  padding: 1.5rem;
  border: 1px solid rgba(197, 160, 89, 0.4);
  background: rgba(197, 160, 89, 0.05);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  max-width: 280px;
  border-radius: 4px;
  transition: all 0.4s var(--ease-elegant);
}

.timeline-content:hover {
  border-color: rgba(197, 160, 89, 0.8);
  background: rgba(212, 185, 120, 0.08);
  transform: translateY(-4px);
  box-shadow:
    0 12px 30px rgba(0, 0, 0, 0.4),
    0 0 20px rgba(197, 160, 89, 0.15);
}

.timeline-time {
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: #c5a059;
}

.timeline-icon {
  font-size: 1.5rem;
  margin: 0.75rem 0;
}

.timeline-title {
  font-family: var(--font-serif);
  font-size: 1.1rem;
  color: #f0f0ee;
  letter-spacing: 0.05em;
}

.timeline-desc {
  font-size: 0.8rem;
  color: rgba(240, 240, 238, 0.5);
  margin-top: 0.5rem;
  line-height: 1.5;
}

/* Mobile: stack vertically, no alternating */
@media (max-width: 768px) {
  .timeline-line {
    left: 1.5rem;
  }
  .timeline-item,
  .timeline-item.right {
    justify-content: flex-start;
    padding-left: 3.5rem;
    padding-right: 0;
  }
  .timeline-dot {
    left: 1.5rem;
  }
  .timeline-content {
    text-align: left;
    max-width: 100%;
  }
}
</style>
