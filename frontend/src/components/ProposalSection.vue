<template>
  <section class="proposal-section section">
    <div class="proposal-container" data-aos="fade-up">
      <!-- Left side: The Story/Quote -->
      <div class="proposal-content">
        

        <h2 class="section-title">The Proposal</h2>
        <div class="gold-divider">✦</div>

        
      </div>

      <!-- Right side: The Video -->
      <div
        class="proposal-video-wrapper"
        data-aos="fade-left"
        data-aos-delay="300"
      >
        <div class="video-frame">
          <div class="video-overlay"></div>
          <video
            class="proposal-video"
            src="@/assets/proposal.mov"
            autoplay
            loop
            muted
            playsinline
          ></video>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "ProposalSection",
};
</script>

<style scoped>
.proposal-section {
  padding: 8rem 2rem;
  background: var(--color-bg);
  position: relative;
  overflow: hidden;
}

/* Add a subtle glowing background light behind the section */
.proposal-section::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  max-width: 800px;
  height: 60vh;
  background: radial-gradient(
    circle,
    rgba(197, 160, 89, 0.05) 0%,
    transparent 70%
  );
  pointer-events: none;
  z-index: 0;
}

.proposal-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 4rem;
  align-items: center;
  position: relative;
  z-index: 1;
}

/* ─── Content Side ─── */
.proposal-content {
  text-align: center;
  padding: 0 2rem;
}

.ornament {
  color: rgba(197, 160, 89, 0.3);
  font-size: 1.5rem;
  line-height: 1;
}
.ornament.top {
  margin-bottom: 1.5rem;
}
.ornament.bottom {
  margin-top: 1.5rem;
  transform: rotate(180deg);
}

.section-title {
  font-family: var(--font-script);
  font-size: clamp(3rem, 6vw, 4.5rem);
  color: #f0f0ee;
  margin: 0;
  text-shadow: 0 2px 30px rgba(197, 160, 89, 0.15);
}

.gold-divider {
  margin: 1.5rem auto 2rem;
}

.proposal-quote {
  margin: 0 0 2rem 0;
  padding: 0;
  border: none;
}

.quote-text {
  font-family: var(--font-serif);
  font-size: clamp(1.2rem, 3vw, 1.8rem);
  color: #f0f0ee;
  line-height: 1.6;
  font-style: italic;
  letter-spacing: 0.02em;
}

.proposal-story p {
  font-family: var(--font-serif);
  font-size: 0.95rem;
  color: rgba(240, 240, 238, 0.6);
  line-height: 1.8;
  letter-spacing: 0.05em;
  max-width: 400px;
  margin: 0 auto;
}

/* ─── Video Side ─── */
.proposal-video-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.video-frame {
  position: relative;
  width: 100%;
  max-width: 360px; /* Constrains the width to keep the vertical aspect ratio looking good */
  aspect-ratio: 9 / 16;
  border-radius: 12px;
  overflow: hidden;
  box-shadow:
    0 25px 50px -12px rgba(0, 0, 0, 0.6),
    0 0 0 1px rgba(197, 160, 89, 0.2),
    /* Subtle inner gold border */ 0 0 40px rgba(197, 160, 89, 0.1); /* Gold glow */
  transform: rotate(2deg); /* Slightly skewed for a "polaroid/memory" feel */
  transition: transform 0.6s cubic-bezier(0.175, 0.885, 0.32, 1.275);
}

.video-frame:hover {
  transform: rotate(0deg) scale(1.02);
  box-shadow:
    0 30px 60px -15px rgba(0, 0, 0, 0.8),
    0 0 0 1px rgba(197, 160, 89, 0.4),
    0 0 60px rgba(197, 160, 89, 0.2);
}

/* Cinematic styling for the video */
.proposal-video {
  width: 100%;
  height: 100%;
  object-fit: cover;
  /* Cinematic color grading: 
     - Lower brightness slightly for mood
     - Increase contrast to make the darks richer
     - Slightly desaturate to hide low-quality color noise
     - Add a slight sepia tone to make it nostalgic/golden 
  */
  filter: brightness(0.85) contrast(1.1) saturate(0.8) sepia(0.15);
}

/* Add a film grain / cinematic overlay on top of the video */
.video-overlay {
  position: absolute;
  inset: 0;
  z-index: 2;
  pointer-events: none;
  background: 
    /* Vignette effect (darkens edges) */
    radial-gradient(circle, transparent 50%, rgba(10, 15, 12, 0.8) 120%),
    /* Subtle vertical scratch/light leak effect */
    linear-gradient(
        to right,
        transparent 45%,
        rgba(197, 160, 89, 0.05) 50%,
        transparent 55%
      );
}

/* Add a beautiful golden reflection on the "glass" of the frame */
.video-frame::after {
  content: "";
  position: absolute;
  inset: 0;
  z-index: 3;
  pointer-events: none;
  background: linear-gradient(
    135deg,
    rgba(255, 255, 255, 0.1) 0%,
    transparent 50%,
    rgba(197, 160, 89, 0.05) 100%
  );
}

/* Responsive */
@media (max-width: 968px) {
  .proposal-container {
    grid-template-columns: 1fr;
    gap: 3rem;
  }

  .proposal-content {
    order: 2; /* Put text below video on mobile */
  }

  .proposal-video-wrapper {
    order: 1;
  }

  .video-frame {
    max-width: 320px;
    transform: rotate(0deg); /* Straight on mobile */
  }

  .video-frame:hover {
    transform: scale(1.02);
  }
}

@media (max-width: 480px) {
  .section-title {
    font-size: 3rem;
  }

  .video-frame {
    max-width: 280px;
  }

  .proposal-section {
    padding: 6rem 1rem;
  }
}
</style>
