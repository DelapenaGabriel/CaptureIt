<template>
  <section class="interlude section">
    <!-- Parallax Background -->
    <div class="interlude-bg" ref="parallaxBg">
      <img
        src="/media/sitting.jpeg"
        alt="A romantic moment"
        class="interlude-img"
      />
      <div class="interlude-overlay"></div>
    </div>

    <!-- Content -->
    <div class="interlude-content">
      <div
        class="interlude-ornament top"
        data-aos="fade-down"
        data-aos-delay="100"
      >
        ❧
      </div>
      <blockquote
        class="interlude-quote"
        data-aos="fade-up"
        data-aos-duration="1500"
      >
        <p class="quote-text">
          "Together is a beautiful <br>place to be."
        </p>
      
      </blockquote>
      <div
        class="interlude-ornament bottom"
        data-aos="fade-up"
        data-aos-delay="200"
      >
        ❧
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "CinematicInterlude",
  data() {
    return {
      scrollHandler: null,
    };
  },
  mounted() {
    this.scrollHandler = () => {
      const bg = this.$refs.parallaxBg;
      if (!bg) return;
      const rect = bg.parentElement.getBoundingClientRect();
      const vh = window.innerHeight;
      if (rect.top < vh && rect.bottom > 0) {
        const progress = (vh - rect.top) / (vh + rect.height);
        const offset = (progress - 0.5) * 80;
        bg.style.transform = `translateY(${offset}px)`;
      }
    };
    window.addEventListener("scroll", this.scrollHandler, { passive: true });
  },
  beforeUnmount() {
    if (this.scrollHandler) {
      window.removeEventListener("scroll", this.scrollHandler);
    }
  },
};
</script>

<style scoped>
.interlude {
  position: relative;
  height: 85vh;
  min-height: 500px;
  max-height: 900px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
}

/* Parallax Background */
.interlude-bg {
  position: absolute;
  inset: -80px 0;
  z-index: 0;
  will-change: transform;
}

.interlude-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  object-position: center 30%;
  filter: brightness(0.85) contrast(1.05) saturate(0.85);
}

.interlude-overlay {
  position: absolute;
  inset: 0;
  background:
    radial-gradient(circle at center, rgba(26, 47, 37, 0.1) 0%, rgba(15, 25, 20, 0.5) 100%),
    linear-gradient(to bottom, rgba(26, 47, 37, 0.15) 0%, rgba(15, 25, 20, 0.7) 100%);
}

/* Content */
.interlude-content {
  position: relative;
  z-index: 2;
  text-align: center;
  max-width: 650px;
  padding: 2rem;
}

.interlude-ornament {
  color: rgba(197, 160, 89, 0.3);
  font-size: 1.5rem;
  line-height: 1;
}
.interlude-ornament.top {
  margin-bottom: 2rem;
}
.interlude-ornament.bottom {
  margin-top: 2rem;
  transform: rotate(180deg);
}

.interlude-quote {
  margin: 0;
  padding: 0;
  border: none;
}

.quote-text {
  font-family: var(--font-script);
  font-size: clamp(1.6rem, 5vw, 3rem);
  color: #f0f0ee;
  line-height: 1.6;
  letter-spacing: 0.01em;
  text-shadow:
    0 2px 40px rgba(0, 0, 0, 0.4),
    0 0 80px rgba(197, 160, 89, 0.08);
  margin: 0;
}

.quote-cite {
  display: block;
  margin-top: 1.5rem;
  font-family: var(--font-serif);
  font-size: 0.8rem;
  font-style: normal;
  letter-spacing: 0.25em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.55);
}

/* Subtle golden border accents */
.interlude-content::before,
.interlude-content::after {
  content: "";
  position: absolute;
  width: 1px;
  height: 60px;
  background: linear-gradient(
    to bottom,
    transparent,
    rgba(197, 160, 89, 0.2),
    transparent
  );
  left: 50%;
  transform: translateX(-50%);
}
.interlude-content::before {
  top: -40px;
}
.interlude-content::after {
  bottom: -40px;
}

@media (max-width: 768px) {
  .interlude {
    height: 70vh;
    min-height: 400px;
  }
  .interlude-content::before,
  .interlude-content::after {
    height: 40px;
  }
}
</style>
