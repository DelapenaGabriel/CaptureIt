<template>
  <div class="landing-page">
    <!-- Envelope Gate -->
    <EnvelopeGate
      v-if="!gateOpen && !store.weddingDayMode"
      @open="handleGateOpen"
    />

    <!-- Main Content -->
    <transition name="page-reveal">
      <div v-if="gateOpen" class="page-content">
        <!-- Navigation -->
        <nav class="top-nav">
          <a v-if="!store.weddingDayMode" href="#rsvp" class="nav-link">RSVP</a>
          <span v-if="!store.weddingDayMode" class="nav-dot">·</span>
          <router-link to="/guestbook" class="nav-link">Guest Book</router-link>
          <span class="nav-dot">·</span>
          <router-link to="/gallery" class="nav-link">Gallery</router-link>
        </nav>

        <!-- Hero -->
        <HeroSection />

        <!-- Countdown -->
        <CountdownTimer />

        <!-- Event Details -->
        <EventDetails />

        <!-- Our Story -->
        <OurStorySection />

        <!-- Carousel Gallery -->
        <CarouselGallerySection />

        <!-- Cinematic Photo Interlude -->
        <CinematicInterlude />

        <!-- The Proposal Video Section -->
        <ProposalSection />

        <!-- RSVP
        <RsvpForm v-if="!store.weddingDayMode" /> -->

        <!-- Guest Book CTA -->
        <section class="guestbook-cta section" data-aos="fade-up">
          <h2 class="section-title">Guest Book</h2>
          <div class="gold-divider">✦</div>
          <p class="cta-text">
            Leave your wishes, share a photo, or write a blessing for the
            newlyweds.
          </p>
          <router-link to="/guestbook" class="cta-btn">
            <span>Sign the Guest Book</span>
            <svg
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
            >
              <path d="M5 12h14m-7-7 7 7-7 7" />
            </svg>
          </router-link>
        </section>

        <!-- Gallery CTA -->
        <section class="gallery-cta section" data-aos="fade-up">
          <h2 class="section-title">Our Moments</h2>
          <div class="gold-divider">✦</div>
          <p class="cta-text">
            Browse and upload photos from our journey together. A living
            gallery, captured by you.
          </p>
          <router-link to="/gallery" class="cta-btn">
            <span>View the Gallery</span>
            <svg
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
            >
              <path d="M5 12h14m-7-7 7 7-7 7" />
            </svg>
          </router-link>
        </section>

        <!-- Footer -->
        <footer class="wedding-footer section">
          <div class="footer-monogram" data-aos="zoom-in">
            <span class="script-text">J</span>
            <span class="footer-amp">&amp;</span>
            <span class="script-text">N</span>
          </div>
          <p class="footer-date" data-aos="fade-up" data-aos-delay="100">
            April 25, 2026
          </p>
          <div class="gold-divider" data-aos="fade-up" data-aos-delay="200">
            ✦
          </div>
          <p class="footer-message" data-aos="fade-up" data-aos-delay="300">
            Made with love
          </p>
        </footer>
      </div>
    </transition>
  </div>
</template>

<script>
import EnvelopeGate from "@/components/EnvelopeGate.vue";
import HeroSection from "@/components/HeroSection.vue";
import CountdownTimer from "@/components/CountdownTimer.vue";
import OurStorySection from "@/components/OurStorySection.vue";
import EventDetails from "@/components/EventDetails.vue";
import CinematicInterlude from "@/components/CinematicInterlude.vue";
import CarouselGallerySection from "@/components/CarouselGallerySection.vue";
import ProposalSection from "@/components/ProposalSection.vue";
import RsvpForm from "@/components/RsvpForm.vue";
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "LandingView",
  components: {
    EnvelopeGate,
    HeroSection,
    CountdownTimer,
    OurStorySection,
    EventDetails,
    CarouselGallerySection,
    CinematicInterlude,
    ProposalSection,
    RsvpForm,
  },
  setup() {
    const store = useWeddingStore();
    return { store };
  },
  data() {
    return {
      gateOpen: false,
    };
  },
  mounted() {
    // Skip gate if wedding day mode is on or if it has been opened before
    const hasSeenGate = localStorage.getItem("wedding_gate_seen");
    
    if (this.store.weddingDayMode || hasSeenGate === "true") {
      this.gateOpen = true;
    }
  },
  methods: {
    handleGateOpen() {
      this.gateOpen = true;
    },
  },
};
</script>

<style scoped>
.landing-page {
  min-height: 100vh;
  position: relative;
}

/* Top Navigation */
.top-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1.25rem;
  padding: 1.25rem 1rem;
  z-index: 9000;
  background: linear-gradient(to bottom, rgba(26, 47, 37, 0.95), transparent);
}

.nav-link {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.25em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.7);
  text-decoration: none;
  transition: color 0.3s ease;
}

.nav-link:hover {
  color: #c5a059;
}

.nav-dot {
  color: rgba(197, 160, 89, 0.3);
  font-size: 0.5rem;
}

.section-title {
  font-family: var(--font-serif);
  font-size: clamp(1.5rem, 4vw, 2.5rem);
  color: #f0f0ee;
  letter-spacing: 0.05em;
}

/* Footer */
.wedding-footer {
  text-align: center;
  padding-bottom: 6rem;
}

.footer-monogram {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  font-size: 3rem;
  color: #c5a059;
}

.footer-amp {
  font-family: var(--font-script);
  font-size: 0.6em;
  opacity: 0.6;
}

.footer-date {
  font-family: var(--font-serif);
  font-size: 0.8rem;
  letter-spacing: 0.3em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
  margin-top: 1rem;
}

.footer-message {
  font-size: 0.75rem;
  color: rgba(240, 240, 238, 0.3);
  letter-spacing: 0.15em;
}

/* CTA Sections */
.guestbook-cta,
.gallery-cta {
  text-align: center;
  max-width: 550px;
  margin: 0 auto;
}

.cta-text {
  font-family: var(--font-serif);
  font-size: 0.9rem;
  color: rgba(240, 240, 238, 0.5);
  line-height: 1.8;
  margin-top: 1rem;
  margin-bottom: 2rem;
}

.cta-btn {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem 2.5rem;
  background: linear-gradient(135deg, #c5a059, #d4b978);
  color: #1a2f25;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  text-decoration: none;
  border: none;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: all 0.4s ease;
}

.cta-btn::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(255, 255, 255, 0.2),
    transparent
  );
  transition: left 0.5s;
}

.cta-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 30px rgba(197, 160, 89, 0.35);
}

.cta-btn:hover::before {
  left: 100%;
}

/* Page reveal transition */
.page-reveal-enter-active {
  transition: opacity 1.5s ease;
}

.page-reveal-enter-from {
  opacity: 0;
}
</style>
