<template>
  <div class="hero-section">
    <FloatingParticles />

    <div class="hero-content">
      <!-- Decorative top ornament -->
      <div class="hero-ornament" data-aos="zoom-in" data-aos-duration="1200">
        <svg width="120" height="30" viewBox="0 0 120 30" fill="none">
          <path
            d="M0 15 Q30 0 60 15 Q90 30 120 15"
            stroke="rgba(197,160,89,0.3)"
            stroke-width="0.5"
            fill="none"
          />
          <circle cx="60" cy="15" r="3" fill="rgba(197,160,89,0.4)" />
          <circle cx="30" cy="8" r="1.5" fill="rgba(197,160,89,0.2)" />
          <circle cx="90" cy="22" r="1.5" fill="rgba(197,160,89,0.2)" />
        </svg>
      </div>

      <p
        class="cordially"
        data-aos="fade-up"
        data-aos-delay="200"
        data-aos-duration="1200"
      >
        You are cordially invited
      </p>

      <p
        class="to-celebrate"
        data-aos="fade-up"
        data-aos-delay="400"
        data-aos-duration="1200"
      >
        to celebrate the union of
      </p>

      <h1
        class="couple-names"
        data-aos="fade-up"
        data-aos-delay="600"
        data-aos-duration="1500"
      >
        <span class="groom-name script-text">{{ store.groomFirstName }}</span>
        <span class="name-ampersand">&amp;</span>
        <span class="bride-name script-text">{{ store.brideFirstName }}</span>
      </h1>

      <div
        class="hero-date-container"
        data-aos="fade-up"
        data-aos-delay="900"
        data-aos-duration="1200"
      >
        <div class="date-line"></div>
        <p class="hero-date">{{ store.formattedDate }}</p>
        <div class="date-line"></div>
      </div>

      <p
        class="hero-venue"
        data-aos="fade-up"
        data-aos-delay="1100"
        data-aos-duration="1000"
      >
        {{ store.venueName }}
      </p>

      <!-- Decorative bottom ornament -->
      <div
        class="hero-ornament bottom"
        data-aos="zoom-in"
        data-aos-delay="1300"
        data-aos-duration="1200"
      >
        <svg width="120" height="30" viewBox="0 0 120 30" fill="none">
          <path
            d="M0 15 Q30 30 60 15 Q90 0 120 15"
            stroke="rgba(197,160,89,0.3)"
            stroke-width="0.5"
            fill="none"
          />
          <circle cx="60" cy="15" r="3" fill="rgba(197,160,89,0.4)" />
          <circle cx="30" cy="22" r="1.5" fill="rgba(197,160,89,0.2)" />
          <circle cx="90" cy="8" r="1.5" fill="rgba(197,160,89,0.2)" />
        </svg>
      </div>
    </div>

    <!-- Scroll indicator -->
    <div class="scroll-cue" data-aos="fade-up" data-aos-delay="2000">
      <span>Scroll</span>
      <div class="scroll-line-container">
        <div class="scroll-line-moving"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { useWeddingStore } from "@/stores/wedding";
import FloatingParticles from "./FloatingParticles.vue";

export default {
  name: "HeroSection",
  components: { FloatingParticles },
  setup() {
    const store = useWeddingStore();
    return { store };
  },
};
</script>

<style scoped>
.hero-section {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 3rem 1.5rem;
  position: relative;
  overflow: hidden;
  background-image: 
    radial-gradient(circle at center, rgba(26, 47, 37, 0.1) 0%, rgba(15, 25, 20, 0.5) 100%),
    linear-gradient(to bottom, rgba(26, 47, 37, 0.15) 0%, rgba(15, 25, 20, 0.7) 100%),
    url('/media/streetshot.jpeg');
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

.hero-content {
  position: relative;
  z-index: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.hero-ornament {
  margin-bottom: 2rem;
  opacity: 0.7;
}

.hero-ornament.bottom {
  margin-bottom: 0;
  margin-top: 2rem;
}

.cordially {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.4em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.7);
}

.to-celebrate {
  font-family: var(--font-serif);
  font-size: 0.8rem;
  letter-spacing: 0.15em;
  color: rgba(240, 240, 238, 0.4);
  margin-top: 0.75rem;
  font-style: italic;
}

.couple-names {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 1.5rem 0;
  gap: 0;
}

.groom-name,
.bride-name {
  font-size: clamp(3rem, 12vw, 7.5rem);
  line-height: 1;
  color: #f0f0ee;
  text-shadow:
    0 0 80px rgba(197, 160, 89, 0.1),
    0 2px 4px rgba(0, 0, 0, 0.1);
  position: relative;
}

.groom-name::after,
.bride-name::after {
  content: "";
  position: absolute;
  bottom: 0;
  left: 10%;
  right: 10%;
  height: 1px;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(197, 160, 89, 0.15),
    transparent
  );
}

.name-ampersand {
  font-family: var(--font-script);
  font-size: clamp(2rem, 5vw, 3.5rem);
  color: #c5a059;
  margin: 0.25rem 0;
  position: relative;
  animation: breathe 4s ease-in-out infinite;
}

@keyframes breathe {
  0%,
  100% {
    transform: scale(1);
    opacity: 0.8;
  }
  50% {
    transform: scale(1.08);
    opacity: 1;
  }
}

.hero-date-container {
  display: flex;
  align-items: center;
  gap: 1.5rem;
  margin-top: 1.5rem;
}

.date-line {
  width: 60px;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(197, 160, 89, 0.3));
}

.date-line:last-child {
  background: linear-gradient(90deg, rgba(197, 160, 89, 0.3), transparent);
}

.hero-date {
  font-family: var(--font-serif);
  font-size: 0.85rem;
  letter-spacing: 0.25em;
  text-transform: uppercase;
  color: #8a9a5b;
}

.hero-venue {
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.2em;
  color: rgba(240, 240, 238, 0.35);
  margin-top: 0.75rem;
  text-transform: uppercase;
}

/* Scroll Cue */
.scroll-cue {
  position: absolute;
  bottom: 2.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.75rem;
  z-index: 2;
}

.scroll-cue span {
  font-family: var(--font-serif);
  font-size: 0.6rem;
  letter-spacing: 0.35em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.4);
}

.scroll-line-container {
  width: 1px;
  height: 40px;
  background: rgba(197, 160, 89, 0.1);
  position: relative;
  overflow: hidden;
}

.scroll-line-moving {
  width: 1px;
  height: 15px;
  background: rgba(197, 160, 89, 0.5);
  position: absolute;
  top: -15px;
  animation: scrollLine 2s ease-in-out infinite;
}

@keyframes scrollLine {
  0% {
    top: -15px;
  }
  100% {
    top: 40px;
  }
}

/* Responsive */
@media (max-width: 767px) {
  .hero-section {
    background-attachment: scroll;
  }
}

@media (min-width: 768px) {
  .couple-names {
    flex-direction: row;
    gap: 0;
  }
  .name-ampersand {
    margin: 0 1rem;
  }
  .groom-name::after,
  .bride-name::after {
    display: none;
  }
  .date-line {
    width: 100px;
  }
}
</style>
