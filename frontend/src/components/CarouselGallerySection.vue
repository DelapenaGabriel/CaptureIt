<template>
  <section class="carousel-section section" data-aos="fade-in" data-aos-duration="1500">
    <div class="carousel-container" ref="carouselContainer">
      <div class="carousel-track">
        <!-- Duplicate items to create seamless infinite loop -->
        <div 
          v-for="(item, index) in duplicatedGallery" 
          :key="index" 
          class="carousel-item"
        >
          <video 
            v-if="isVideo(item)" 
            :src="item" 
            autoplay 
            muted 
            loop 
            playsinline 
            class="media-content video-content"
          ></video>
          <img 
            v-else 
            :src="item" 
            alt="Gallery memory" 
            class="media-content" 
            loading="lazy"
          />
        </div>
      </div>
    </div>
  </section>
</template>

<script>
// Import the gallery array
import { gallery } from "@/stores/carousel";

export default {
  name: "CarouselGallerySection",
  data() {
    // Remove exact duplicates since they might appear side-by-side
    const uniqueGallery = [...new Set(gallery)];
    
    // Shuffle the array sequentially to make it random
    const shuffledGallery = [...uniqueGallery];
    for (let i = shuffledGallery.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [shuffledGallery[i], shuffledGallery[j]] = [shuffledGallery[j], shuffledGallery[i]];
    }

    return {
      // Duplicate the array to ensure continuous scrolling without visual break
      duplicatedGallery: [...shuffledGallery, ...shuffledGallery]
    };
  },
  methods: {
    isVideo(url) {
      if (!url) return false;
      return url.toLowerCase().endsWith('.mp4') || url.toLowerCase().endsWith('.mov') || url.toLowerCase().endsWith('.webm');
    }
  }
};
</script>

<style scoped>
.carousel-section {
  padding: 4rem 0;
  margin: 2rem 0;
  overflow: hidden;
  background: linear-gradient(to bottom, transparent, rgba(197, 160, 89, 0.03), transparent);
  position: relative;
}

/* Subtle gold top/bottom borders */
.carousel-section::before,
.carousel-section::after {
  content: '';
  position: absolute;
  left: 0;
  width: 100%;
  height: 1px;
  background: linear-gradient(90deg, transparent, rgba(197, 160, 89, 0.3), transparent);
}

.carousel-section::before {
  top: 0;
}

.carousel-section::after {
  bottom: 0;
}

.carousel-container {
  width: 100%;
  overflow: hidden;
  position: relative;
  /* Mask edges for a fading effect */
  -webkit-mask-image: linear-gradient(90deg, transparent, black 15%, black 85%, transparent);
  mask-image: linear-gradient(90deg, transparent, black 15%, black 85%, transparent);
}

.carousel-track {
  display: flex;
  gap: 2rem;
  width: max-content;
  /* Animation to translate horizontally by half of its width (the original length) */
  animation: scroll-carousel 65s linear infinite;
  padding: 2rem 0;
  align-items: center;
}

.carousel-track:hover {
  animation-play-state: paused;
}

.carousel-item {
  flex-shrink: 0;
  width: 280px;
  height: 380px;
  border-radius: 6px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.4);
  background: rgba(26, 47, 37, 0.5);
  border: 1px solid rgba(197, 160, 89, 0.2);
  transition: transform 0.4s var(--ease-elegant), box-shadow 0.4s var(--ease-elegant), border-color 0.4s ease;
  cursor: grab;
}

.carousel-item:active {
  cursor: grabbing;
}

/* Give slight alternating vertical shifts for an organic mosaic feel */
.carousel-item:nth-child(even) {
  transform: translateY(15px);
}

.carousel-item:nth-child(odd) {
  transform: translateY(-15px);
}

.carousel-item:hover {
  transform: translateY(-5px) scale(1.03) !important;
  box-shadow: 0 15px 40px rgba(0, 0, 0, 0.6), 0 0 20px rgba(197, 160, 89, 0.2);
  border-color: rgba(197, 160, 89, 0.5);
  z-index: 2;
}

.media-content {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: filter 0.5s var(--ease-elegant), transform 1.5s var(--ease-elegant);
  filter: brightness(0.85) contrast(1.1) saturate(0.95);
  display: block;
}

.carousel-item:hover .media-content {
  filter: brightness(1.05) contrast(1.1) saturate(1.1);
  transform: scale(1.05);
}

@keyframes scroll-carousel {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(calc(-50% - 1rem)); /* -1rem accounts for half the 2rem gap */
  }
}

@media (max-width: 768px) {
  .carousel-section {
    padding: 3rem 0;
    margin: 1rem 0;
  }
  .carousel-container {
    -webkit-mask-image: linear-gradient(90deg, transparent, black 10%, black 90%, transparent);
    mask-image: linear-gradient(90deg, transparent, black 10%, black 90%, transparent);
  }
  .carousel-item {
    width: 200px;
    height: 280px;
  }
  .carousel-item:nth-child(even) {
    transform: translateY(10px);
  }
  .carousel-item:nth-child(odd) {
    transform: translateY(-10px);
  }
  .carousel-track {
    gap: 1rem;
    animation-duration: 50s; /* Speed up a bit on mobile since screen is narrower */
  }
  @keyframes scroll-carousel {
    0% {
      transform: translateX(0);
    }
    100% {
      transform: translateX(calc(-50% - 0.5rem)); /* -0.5rem accounts for half the 1rem gap */
    }
  }
}
</style>
