<template>
  <div class="gate-overlay" :class="{ 'is-exiting': isExiting }">
    <canvas ref="ambientCanvas" class="ambient-particles"></canvas>

    <!-- Phase 1: Welcome Text -->
    <transition name="text-fade">
      <div
        v-if="phase === 'welcome'"
        class="welcome-phase"
        @click="goToEnvelope"
      >
        <div class="welcome-ornament top">❧</div>
        <p class="welcome-prelude">Together with their families</p>
        <h1 class="welcome-names">
          <span class="name-line" :style="{ animationDelay: '0.8s' }"
            >Jonathan</span
          >
          <span class="name-amp" :style="{ animationDelay: '1.4s' }"
            >&amp;</span
          >
          <span class="name-line" :style="{ animationDelay: '2.0s' }"
            >Norma</span
          >
        </h1>
        <p class="welcome-invite" :style="{ animationDelay: '2.8s' }">
          request the pleasure of your company
        </p>
        <div class="welcome-date-badge" :style="{ animationDelay: '3.4s' }">
          <span class="date-day">25</span>
          <div class="date-details">
            <span>April</span>
            <span>2026</span>
          </div>
        </div>
        <button
          class="open-envelope-btn"
          :style="{ animationDelay: '4.0s' }"
          @click.stop="goToEnvelope"
        >
          <span class="btn-text">Open Your Invitation</span>
          <span class="btn-arrow">↓</span>
        </button>
        <div class="welcome-ornament bottom">❧</div>
      </div>
    </transition>

    <!-- Phase 2: Envelope -->
    <transition name="envelope-entrance">
      <div v-if="phase === 'envelope'" class="envelope-phase">
        <p class="tap-hint" :class="{ visible: showHint }">
          Tap the seal to open
        </p>

        <div class="envelope-scene">
          <div class="envelope-wrapper" :class="{ 'is-open': envelopeOpen }">
            <div class="envelope-body">
              <div class="envelope-liner"></div>
              <!-- Card peek inside -->
              <div class="card-peek" :class="{ lifting: cardLifting }">
                <div class="peek-frame">
                  <p class="peek-label">Wedding Invitation</p>
                  <p class="peek-names">Jonathan &amp; Norma</p>
                </div>
              </div>
            </div>
            <div class="flap flap-left"></div>
            <div class="flap flap-right"></div>
            <div class="flap flap-bottom"></div>
            <div
              class="flap flap-top"
              :class="{ 'flap-opening': envelopeOpen }"
            >
              <div
                class="wax-seal"
                :class="{
                  shaking: sealShaking,
                  cracking: sealCracking,
                  gone: sealGone,
                }"
                @click.stop="breakSeal"
              >
                <div class="seal-glow"></div>
                <div class="seal-body">
                  <span class="seal-monogram">J&amp;N</span>
                  <div class="seal-ring"></div>
                </div>
                <div class="seal-shimmer"></div>
                <svg
                  v-if="sealCracking"
                  class="seal-cracks"
                  viewBox="0 0 70 70"
                >
                  <line
                    x1="20"
                    y1="15"
                    x2="50"
                    y2="55"
                    stroke="#0f1f18"
                    stroke-width="1.5"
                    class="crack crack-1"
                  />
                  <line
                    x1="45"
                    y1="10"
                    x2="25"
                    y2="60"
                    stroke="#0f1f18"
                    stroke-width="1"
                    class="crack crack-2"
                  />
                  <line
                    x1="10"
                    y1="35"
                    x2="60"
                    y2="40"
                    stroke="#0f1f18"
                    stroke-width="1"
                    class="crack crack-3"
                  />
                </svg>
              </div>
            </div>
          </div>

          <!-- Sparkle burst -->
          <div v-if="showSparkles" class="sparkle-burst">
            <span
              v-for="n in 16"
              :key="n"
              class="sparkle"
              :style="{
                '--angle': n * 22.5 + 'deg',
                '--dist': 35 + Math.random() * 40 + 'px',
                '--delay': Math.random() * 0.25 + 's',
                '--size': 0.4 + Math.random() * 0.5 + 'rem',
              }"
              >✦</span
            >
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
const STORAGE_KEY = "wedding_gate_seen";

import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "EnvelopeGate",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
  data() {
    return {
      phase: "welcome",
      showHint: false,
      sealShaking: false,
      sealCracking: false,
      sealGone: false,
      showSparkles: false,
      envelopeOpen: false,
      cardLifting: false,
      isExiting: false,
      particles: [],
      animFrame: null,
    };
  },
  mounted() {
    this.initAmbientParticles();
  },
  beforeUnmount() {
    cancelAnimationFrame(this.animFrame);
  },
  methods: {
    goToEnvelope() {
      this.phase = "envelope";
      setTimeout(() => {
        this.showHint = true;
      }, 1500);
    },

    breakSeal() {
      if (this.sealGone) return;
      this.showHint = false;

      // Initialize music IMMEDIATELY on click to satisfy browser autoplay rules
      this.store.initMusic();

      // Step 1: Seal shakes intensely
      this.sealShaking = true;

      setTimeout(() => {
        // Step 2: Cracks draw across the seal
        this.sealShaking = false;
        this.sealCracking = true;

        setTimeout(() => {
          // Step 3: Seal shatters with sparkle burst
          this.sealGone = true;
          this.showSparkles = true;
          setTimeout(() => {
            this.showSparkles = false;
          }, 1000);

          // Step 4: Flap opens with 3D rotation
          setTimeout(() => {
            this.envelopeOpen = true;

            // Step 5: Card peeks out
            setTimeout(() => {
              this.cardLifting = true;

              // Step 6: After a brief moment, fade everything into the home
              setTimeout(() => {
                this.isExiting = true;
                localStorage.setItem(STORAGE_KEY, "true");

                setTimeout(() => {
                  this.$emit("open");
                }, 1800);
              }, 1400);
            }, 800);
          }, 400);
        }, 600);
      }, 800);
    },

    initAmbientParticles() {
      const canvas = this.$refs.ambientCanvas;
      if (!canvas) return;
      const ctx = canvas.getContext("2d");
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;

      this.particles = [];
      const count = Math.floor(canvas.width / 22);
      for (let i = 0; i < count; i++) {
        this.particles.push({
          x: Math.random() * canvas.width,
          y: Math.random() * canvas.height,
          r: Math.random() * 1.8 + 0.4,
          dx: (Math.random() - 0.5) * 0.12,
          dy: -Math.random() * 0.12 - 0.03,
          o: Math.random() * 0.35 + 0.08,
          phase: Math.random() * Math.PI * 2,
        });
      }

      const animate = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        const t = Date.now() * 0.001;
        this.particles.forEach((p) => {
          p.x += p.dx;
          p.y += p.dy;
          if (p.y < -10) {
            p.y = canvas.height + 10;
            p.x = Math.random() * canvas.width;
          }
          if (p.x < -10) p.x = canvas.width + 10;
          if (p.x > canvas.width + 10) p.x = -10;
          const flicker = Math.sin(t * 2 + p.phase) * 0.3 + 0.7;
          const a = p.o * flicker;
          ctx.beginPath();
          ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
          ctx.fillStyle = `rgba(212, 185, 120, ${a})`;
          ctx.fill();
          // Soft glow
          ctx.beginPath();
          ctx.arc(p.x, p.y, p.r * 3.5, 0, Math.PI * 2);
          ctx.fillStyle = `rgba(212, 185, 120, ${a * 0.06})`;
          ctx.fill();
        });
        this.animFrame = requestAnimationFrame(animate);
      };
      animate();

      window.addEventListener("resize", () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
      });
    },
  },
};
</script>

<style scoped>
/* ─── Overlay ─── */
.gate-overlay {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: #0f1f18;
  display: flex;
  align-items: center;
  justify-content: center;
  transition:
    opacity 2s cubic-bezier(0.4, 0, 0, 1),
    transform 2s cubic-bezier(0.4, 0, 0, 1);
}

.gate-overlay.is-exiting {
  opacity: 0;
  transform: scale(1.06);
  pointer-events: none;
}

.ambient-particles {
  position: absolute;
  inset: 0;
  pointer-events: none;
  z-index: 0;
}

/* ─── Welcome Phase ─── */
.welcome-phase {
  position: relative;
  z-index: 2;
  text-align: center;
  padding: 2rem;
  cursor: pointer;
  max-width: 500px;
}

.welcome-ornament {
  font-size: 1.5rem;
  color: rgba(197, 160, 89, 0.3);
  margin-bottom: 2rem;
  animation: fadeSlideUp 1s ease forwards;
  opacity: 0;
}
.welcome-ornament.bottom {
  margin-bottom: 0;
  margin-top: 2.5rem;
  animation-delay: 4.5s;
}

.welcome-prelude {
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.35em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.4);
  margin-bottom: 2rem;
  animation: fadeSlideUp 1s ease forwards;
  animation-delay: 0.3s;
  opacity: 0;
}

.welcome-names {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.25rem;
}

.name-line {
  font-family: var(--font-script);
  font-size: clamp(3rem, 12vw, 5.5rem);
  color: #f0f0ee;
  display: block;
  animation: nameReveal 1.2s ease forwards;
  opacity: 0;
  text-shadow: 0 0 60px rgba(197, 160, 89, 0.15);
}

.name-amp {
  font-family: var(--font-script);
  font-size: clamp(1.5rem, 5vw, 2.5rem);
  color: #c5a059;
  display: block;
  animation: ampReveal 0.8s ease forwards;
  opacity: 0;
}

.welcome-invite {
  font-family: var(--font-serif);
  font-size: 0.8rem;
  letter-spacing: 0.2em;
  color: rgba(240, 240, 238, 0.5);
  margin-top: 2rem;
  animation: fadeSlideUp 1s ease forwards;
  opacity: 0;
}

.welcome-date-badge {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem 1.5rem;
  border: 1px solid rgba(197, 160, 89, 0.25);
  margin-top: 2rem;
  animation: fadeSlideUp 1s ease forwards;
  opacity: 0;
}
.date-day {
  font-family: var(--font-serif);
  font-size: 2.5rem;
  color: #c5a059;
  line-height: 1;
}
.date-details {
  display: flex;
  flex-direction: column;
  text-align: left;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
  line-height: 1.6;
}

.open-envelope-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  margin-top: 2.5rem;
  padding: 1rem 2.5rem;
  background: transparent;
  border: 1px solid rgba(197, 160, 89, 0.3);
  color: #c5a059;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.25em;
  text-transform: uppercase;
  cursor: pointer;
  animation: fadeSlideUp 1s ease forwards;
  opacity: 0;
  transition: all 0.4s ease;
}
.open-envelope-btn:hover {
  border-color: rgba(197, 160, 89, 0.6);
  background: rgba(197, 160, 89, 0.05);
  box-shadow: 0 0 30px rgba(197, 160, 89, 0.1);
}
.btn-arrow {
  animation: bounceDown 2s ease-in-out infinite;
  font-size: 1rem;
}

/* ─── Envelope Phase ─── */
.envelope-phase {
  position: relative;
  z-index: 2;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2rem;
  width: 100%;
  max-width: 700px;
  padding: 0 1rem;
}

.tap-hint {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.3em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0);
  transition: color 1s ease;
  height: 1.2em;
}
.tap-hint.visible {
  color: rgba(197, 160, 89, 0.5);
  animation: hintPulse 2s ease-in-out infinite;
}

/* ─── Envelope ─── */
.envelope-scene {
  position: relative;
  width: 100%;
  display: flex;
  justify-content: center;
}

.envelope-wrapper {
  position: relative;
  width: min(520px, 92vw);
  height: min(350px, 58vw);
  perspective: 2000px;
  cursor: pointer;
}

.envelope-body {
  position: absolute;
  inset: 0;
  background: linear-gradient(165deg, #2a4535, #1a2f25);
  box-shadow:
    0 25px 70px rgba(0, 0, 0, 0.5),
    0 0 0 1px rgba(197, 160, 89, 0.06),
    inset 0 1px 0 rgba(255, 255, 255, 0.05);
  z-index: 1;
  overflow: hidden;
}

.envelope-liner {
  position: absolute;
  inset: 6px;
  background: repeating-linear-gradient(
    45deg,
    transparent,
    transparent 20px,
    rgba(197, 160, 89, 0.03) 20px,
    rgba(197, 160, 89, 0.03) 21px
  );
  border: 1px solid rgba(197, 160, 89, 0.06);
  pointer-events: none;
}

/* Card inside envelope */
.card-peek {
  position: absolute;
  top: 10px;
  left: 14px;
  right: 14px;
  height: 55%;
  background: linear-gradient(170deg, #faf8f5, #f0ece3);
  border: 1px solid rgba(197, 160, 89, 0.12);
  z-index: 2;
  transition: transform 1.4s cubic-bezier(0.16, 1, 0.3, 1);
  overflow: hidden;
}

.card-peek.lifting {
  transform: translateY(-65%);
}

.peek-frame {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  border: 1px solid rgba(197, 160, 89, 0.08);
  margin: 4px;
}

.peek-label {
  font-family: var(--font-serif);
  font-size: 0.5rem;
  letter-spacing: 0.4em;
  text-transform: uppercase;
  color: #c5a059;
}

.peek-names {
  font-family: var(--font-script);
  font-size: clamp(1.2rem, 4vw, 1.8rem);
  color: #2c2c2c;
}

/* ─── Flaps ─── */
.flap {
  position: absolute;
  z-index: 3;
}
.flap-left {
  left: 0;
  top: 0;
  width: 50%;
  height: 100%;
  clip-path: polygon(0 0, 100% 50%, 0 100%);
  background: linear-gradient(165deg, #2f4a3b, #243d30);
}
.flap-right {
  right: 0;
  top: 0;
  width: 50%;
  height: 100%;
  clip-path: polygon(100% 0, 0 50%, 100% 100%);
  background: linear-gradient(165deg, #2a4535, #213b2c);
}
.flap-bottom {
  bottom: 0;
  left: 0;
  width: 100%;
  height: 58%;
  clip-path: polygon(0 100%, 50% 0, 100% 100%);
  background: linear-gradient(to top, #3a5c4a, #2f4a3b);
  z-index: 4;
}
.flap-top {
  top: 0;
  left: 0;
  width: 100%;
  height: 62%;
  clip-path: polygon(0 0, 50% 100%, 100% 0);
  background: linear-gradient(to bottom, #355242, #2a4535);
  z-index: 5;
  transform-origin: top center;
  transition: transform 1.4s cubic-bezier(0.34, 1.56, 0.64, 1);
  display: flex;
  justify-content: center;
  align-items: flex-end;
  padding-bottom: 8%;
}

.flap-top.flap-opening {
  transform: rotateX(180deg);
  z-index: 0;
}

/* ─── Wax Seal ─── */
.wax-seal {
  width: 68px;
  height: 68px;
  border-radius: 50%;
  position: relative;
  cursor: pointer;
  transition: transform 0.3s ease;
  z-index: 10;
  animation: sealPulseAmbient 3s infinite ease-in-out;
}
.wax-seal:hover:not(.gone) {
  transform: scale(1.1);
  animation: none;
}

.seal-glow {
  position: absolute;
  inset: -12px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(197, 160, 89, 0.4), transparent 70%);
  animation: sealGlow 3s ease-in-out infinite;
}

.seal-body {
  position: absolute;
  inset: 0;
  border-radius: 50%;
  background: radial-gradient(circle at 35% 35%, #dcc080, #c5a059, #a68a3e);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow:
    0 4px 14px rgba(0, 0, 0, 0.45),
    inset 0 2px 4px rgba(255, 255, 255, 0.25),
    inset 0 -2px 4px rgba(0, 0, 0, 0.2);
}

.seal-ring {
  position: absolute;
  inset: 7px;
  border-radius: 50%;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.seal-monogram {
  font-family: var(--font-script);
  font-size: 1.15rem;
  color: rgba(255, 255, 255, 0.9);
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
  z-index: 1;
}

.seal-shimmer {
  position: absolute;
  inset: 0;
  border-radius: 50%;
  background: linear-gradient(
    135deg,
    transparent 40%,
    rgba(255, 255, 255, 0.18) 50%,
    transparent 60%
  );
  animation: shimmer 4s linear infinite;
}

/* Cracks */
.seal-cracks {
  position: absolute;
  inset: 0;
  z-index: 5;
  pointer-events: none;
}
.crack {
  opacity: 0;
}
.crack-1 {
  animation: crackDraw 0.35s 0s ease forwards;
}
.crack-2 {
  animation: crackDraw 0.35s 0.12s ease forwards;
}
.crack-3 {
  animation: crackDraw 0.35s 0.24s ease forwards;
}

.wax-seal.shaking {
  animation: sealShake 0.7s ease;
}
.wax-seal.cracking {
  animation: sealPulse 0.6s ease;
}
.wax-seal.gone {
  animation: sealShatter 0.6s ease forwards;
  pointer-events: none;
}

/* ─── Sparkles ─── */
.sparkle-burst {
  position: absolute;
  top: 50%;
  left: 50%;
  z-index: 20;
  pointer-events: none;
}

.sparkle {
  position: absolute;
  font-size: var(--size);
  color: #c5a059;
  animation: sparkleFly 0.9s var(--delay) ease-out forwards;
  opacity: 0;
  text-shadow: 0 0 6px rgba(197, 160, 89, 0.5);
}

/* ─── Keyframes ─── */
@keyframes fadeSlideUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
@keyframes nameReveal {
  0% {
    opacity: 0;
    transform: translateY(30px) scale(0.95);
    filter: blur(4px);
  }
  100% {
    opacity: 1;
    transform: translateY(0) scale(1);
    filter: blur(0);
  }
}
@keyframes ampReveal {
  0% {
    opacity: 0;
    transform: scale(0.5);
  }
  50% {
    opacity: 1;
    transform: scale(1.15);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}
@keyframes bounceDown {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(5px);
  }
}
@keyframes hintPulse {
  0%,
  100% {
    opacity: 0.5;
  }
  50% {
    opacity: 1;
  }
}
@keyframes sealGlow {
  0%,
  100% {
    opacity: 0.5;
    transform: scale(1);
  }
  50% {
    opacity: 1;
    transform: scale(1.15);
  }
}
@keyframes sealPulseAmbient {
  0%,
  100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
}
@keyframes shimmer {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
@keyframes sealShake {
  0%,
  100% {
    transform: rotate(0deg);
  }
  10% {
    transform: rotate(-12deg) scale(1.05);
  }
  20% {
    transform: rotate(12deg);
  }
  30% {
    transform: rotate(-10deg);
  }
  40% {
    transform: rotate(10deg);
  }
  50% {
    transform: rotate(-8deg);
  }
  60% {
    transform: rotate(8deg);
  }
  70% {
    transform: rotate(-5deg);
  }
  80% {
    transform: rotate(5deg);
  }
  90% {
    transform: rotate(-2deg);
  }
}
@keyframes sealPulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.08);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes crackDraw {
  from {
    opacity: 0;
    stroke-dasharray: 80;
    stroke-dashoffset: 80;
  }
  to {
    opacity: 1;
    stroke-dasharray: 80;
    stroke-dashoffset: 0;
  }
}
@keyframes sealShatter {
  0% {
    opacity: 1;
    transform: scale(1);
    filter: brightness(1);
  }
  25% {
    transform: scale(1.5);
    filter: brightness(2);
  }
  100% {
    opacity: 0;
    transform: scale(0);
    filter: brightness(3);
  }
}
@keyframes sparkleFly {
  0% {
    opacity: 1;
    transform: translate(0, 0) scale(1) rotate(0deg);
  }
  100% {
    opacity: 0;
    transform: translate(
        calc(cos(var(--angle)) * var(--dist)),
        calc(sin(var(--angle)) * var(--dist))
      )
      scale(0) rotate(180deg);
  }
}

/* ─── Transitions ─── */
.text-fade-enter-active {
  transition: all 0.8s ease;
}
.text-fade-leave-active {
  transition: all 0.6s ease;
}
.text-fade-enter-from {
  opacity: 0;
}
.text-fade-leave-to {
  opacity: 0;
  transform: scale(0.97);
}

.envelope-entrance-enter-active {
  transition: all 0.8s ease;
}
.envelope-entrance-enter-from {
  opacity: 0;
  transform: translateY(30px);
}

/* ─── Responsive ─── */
@media (max-width: 480px) {
  .envelope-wrapper {
    width: 92vw;
    height: 62vw;
  }
  .wax-seal {
    width: 52px;
    height: 52px;
  }
  .seal-monogram {
    font-size: 0.9rem;
  }
}
</style>
