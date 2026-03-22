<template>
  <section class="story-section section" ref="sectionRef">
    <h2 class="section-title" data-aos="fade-up">Our Story</h2>
    <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>
    <div class="story-text-container" data-aos="fade-up" data-aos-delay="200">
      <p class="story-text">
        <span
          v-for="(char, index) in chars"
          :key="index"
          class="char"
          :class="{
            revealed: index <= currentIndex,
            glowing: index === currentIndex,
          }"
          >{{ char }}</span
        >
        <span
          class="cursor"
          :class="{
            hidden: !showCursor,
            writing: currentIndex > -1 && currentIndex < chars.length - 1,
          }"
          >|</span
        >
      </p>
    </div>
  </section>
</template>

<script>
export default {
  name: "OurStorySection",
  data() {
    return {
      fullText:
        "We met in a season of life where neither of us was looking for perfection — just something real. What started as simple conversations quickly turned into a deep and natural connection. Being together felt easy. It felt right. As our relationship grew, so did our lives. Along the way, we didn’t just fall in love with each other — we also opened our hearts to the beautiful journey of blending our families. Our children became a part of our story, teaching us patience, strength, and a deeper kind of love. Together, we’ve built a foundation rooted in friendship, trust, and choosing one another every single day. Through life’s changes and new beginnings, we continue to grow side by side. Now, surrounded by the people who have supported us and witnessed our journey, we step forward with gratitude and excitement for the future we are creating — not just as partners, but as a family",
      currentIndex: -1,
      showCursor: false,
      hasStarted: false,
      observer: null,
    };
  },
  computed: {
    chars() {
      return this.fullText.split("");
    },
  },
  mounted() {
    this.observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && !this.hasStarted) {
          this.hasStarted = true;
          // Add a delay to coordinate with AOS fade-up animation
          setTimeout(() => {
            this.startWriting();
          }, 800);
        }
      },
      { threshold: 0.2 },
    );

    if (this.$refs.sectionRef) {
      this.observer.observe(this.$refs.sectionRef);
    }
  },
  beforeUnmount() {
    if (this.observer && this.$refs.sectionRef) {
      this.observer.unobserve(this.$refs.sectionRef);
    }
  },
  methods: {
    startWriting() {
      this.showCursor = true;

      const typeNextChar = () => {
        if (this.currentIndex < this.chars.length - 1) {
          const baseSpeed = 40;
          const variance = Math.random() * 30; // Randomize typing speed for human feel
          let delay = baseSpeed + variance;

          this.currentIndex++;
          const char = this.chars[this.currentIndex];

          // Pause slightly on punctuation for dramatic effect
          if ([".", ",", "!"].includes(char)) {
            delay += 350;
          }

          setTimeout(typeNextChar, delay);
        } else {
          // Finished typing
          setTimeout(() => {
            this.showCursor = false;
          }, 4000); // Hide cursor after 4 seconds of blinking
        }
      };

      // Initial short pause before typing begins
      setTimeout(typeNextChar, 500);
    },
  },
};
</script>

<style scoped>
.story-section {
  text-align: center;
  max-width: 750px;
  margin: 0 auto;
}

.section-title {
  font-family: var(--font-serif);
  font-size: clamp(1.5rem, 4vw, 2.5rem);
  color: #f0f0ee;
  letter-spacing: 0.05em;
}

.story-text-container {
  min-height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  padding: 3rem 2.5rem;
  background: rgba(197, 160, 89, 0.03);
  border: 1px solid rgba(197, 160, 89, 0.15);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  border-radius: 4px;
  transition: all 0.5s var(--ease-elegant);
}

.story-text-container:hover {
  border-color: rgba(197, 160, 89, 0.4);
  background: rgba(212, 185, 120, 0.05);
  box-shadow:
    0 12px 40px rgba(0, 0, 0, 0.3),
    0 0 20px rgba(197, 160, 89, 0.1);
  transform: translateY(-2px);
}

.story-text {
  font-family: var(--font-serif);
  font-size: clamp(1.1rem, 3vw, 1.6rem);
  line-height: 1.8;
  color: rgba(240, 240, 238, 0.95);
  margin: 0;
  text-align: center;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.char {
  opacity: 0;
  transition:
    opacity 0.5s ease,
    text-shadow 0.5s ease,
    color 0.5s ease;
  will-change: opacity, text-shadow, color;
}

.char.revealed {
  opacity: 1;
}

/* Bright gold flash as the character appears, simulating wet ink drying */
.char.glowing {
  text-shadow:
    0 0 15px rgba(212, 185, 120, 1),
    0 0 30px rgba(212, 185, 120, 0.8);
  color: #fff;
  transition: none; /* Instant glow on appearance */
}

/* Cursor styling */
.cursor {
  display: inline-block;
  color: #c5a059;
  font-size: 1em; /* Matches text size */
  font-weight: 300;
  margin-left: 2px;
  text-shadow: 0 0 8px rgba(197, 160, 89, 0.6);
  transition: opacity 0.5s ease;
  vertical-align: text-bottom;
}

.cursor:not(.hidden):not(.writing) {
  animation: blink 1s step-end infinite;
}

/* While writing, keep cursor solid and slightly glowing */
.cursor.writing {
  opacity: 0.9;
  text-shadow: 0 0 12px rgba(212, 185, 120, 1);
}

.cursor.hidden {
  opacity: 0 !important;
  animation: none;
}

@keyframes blink {
  0%,
  100% {
    opacity: 0;
  }
  50% {
    opacity: 1;
  }
}

@media (max-width: 768px) {
  .story-text-container {
    padding: 2rem 1.5rem;
    min-height: 280px;
  }
}
</style>
