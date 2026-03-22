<template>
  <div class="countdown section">
    <!-- Wedding Day: Celebration Message -->
    <template v-if="store.weddingDayMode">
      <h2 class="section-title celebration-title" data-aos="zoom-in">
        ✦ Today is the Day ✦
      </h2>
      <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>
      <p class="celebration-text" data-aos="fade-up" data-aos-delay="200">
        The moment we've been waiting for is finally here.
      </p>
    </template>

    <!-- Pre-Wedding: Countdown -->
    <template v-else>
      <h2 class="section-title" data-aos="fade-up">Counting the Days</h2>
      <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>

      <div class="countdown-grid" data-aos="fade-up" data-aos-delay="200">
        <div class="countdown-card" v-for="unit in units" :key="unit.label">
          <div class="countdown-value">
            <span
              class="digit pulse-tick"
              v-for="(d, i) in padValue(unit.value)"
              :key="i"
              >{{ d }}</span
            >
          </div>
          <span class="countdown-label">{{ unit.label }}</span>
        </div>
      </div>
    </template>
  </div>
</template>

<script>
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "CountdownTimer",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
  data() {
    return {
      days: 0,
      hours: 0,
      minutes: 0,
      seconds: 0,
      intervalId: null,
    };
  },
  computed: {
    units() {
      return [
        { label: "Days", value: this.days },
        { label: "Hours", value: this.hours },
        { label: "Minutes", value: this.minutes },
        { label: "Seconds", value: this.seconds },
      ];
    },
  },
  methods: {
    padValue(val) {
      return String(val).padStart(2, "0").split("");
    },
    updateCountdown() {
      const target = new Date(this.store.weddingDate).getTime();
      const now = Date.now();
      const diff = Math.max(0, target - now);

      this.days = Math.floor(diff / (1000 * 60 * 60 * 24));
      this.hours = Math.floor((diff / (1000 * 60 * 60)) % 24);
      this.minutes = Math.floor((diff / (1000 * 60)) % 60);
      this.seconds = Math.floor((diff / 1000) % 60);
    },
  },
  mounted() {
    this.updateCountdown();
    this.intervalId = setInterval(this.updateCountdown, 1000);
  },
  beforeUnmount() {
    clearInterval(this.intervalId);
  },
};
</script>

<style scoped>
.countdown {
  text-align: center;
}

.section-title {
  font-family: var(--font-serif);
  font-size: clamp(1.5rem, 4vw, 2.5rem);
  color: #f0f0ee;
  letter-spacing: 0.05em;
  margin-bottom: 0.5rem;
}

.countdown-grid {
  display: flex;
  justify-content: center;
  gap: 1rem;
  flex-wrap: wrap;
  margin-top: 2rem;
}

.countdown-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  padding: 1.5rem 1rem;
  min-width: 90px;
  border: 1px solid rgba(197, 160, 89, 0.2);
  background: rgba(197, 160, 89, 0.03);
  backdrop-filter: blur(8px);
}

.countdown-value {
  display: flex;
  gap: 4px;
}

.digit {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 2.5rem;
  height: 3.5rem;
  font-family: var(--font-serif);
  font-size: 2rem;
  color: #c5a059;
  border: 1px solid rgba(197, 160, 89, 0.15);
  background: rgba(26, 47, 37, 0.6);
  position: relative;
  overflow: hidden;
}

/* Ticking pulse animation */
.pulse-tick {
  animation: digitPulse 1s infinite;
}

@keyframes digitPulse {
  0% {
    text-shadow: 0 0 0 rgba(212, 185, 120, 0);
    border-color: rgba(197, 160, 89, 0.15);
  }
  15% {
    text-shadow: 0 0 10px rgba(212, 185, 120, 0.8);
    border-color: rgba(197, 160, 89, 0.4);
    transform: scale(1.02);
  }
  100% {
    text-shadow: 0 0 0 rgba(212, 185, 120, 0);
    border-color: rgba(197, 160, 89, 0.15);
    transform: scale(1);
  }
}

.countdown-label {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.3em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
}

@media (min-width: 768px) {
  .countdown-grid {
    gap: 2rem;
  }
  .countdown-card {
    padding: 2rem 1.5rem;
    min-width: 120px;
  }
  .digit {
    width: 3rem;
    height: 4rem;
    font-size: 2.5rem;
  }
}

/* Wedding Day Celebration */
.celebration-title {
  font-size: clamp(1.8rem, 5vw, 3rem) !important;
  background: linear-gradient(135deg, #c5a059, #e8d5a3, #c5a059);
  background-size: 200% auto;
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  animation: shimmer 3s ease-in-out infinite;
}

.celebration-text {
  font-family: var(--font-serif);
  font-size: 1.1rem;
  line-height: 1.8;
  color: rgba(240, 240, 238, 0.6);
  font-style: italic;
  margin-top: 1.5rem;
}

@keyframes shimmer {
  0%,
  100% {
    background-position: 0% center;
  }
  50% {
    background-position: 200% center;
  }
}
</style>
