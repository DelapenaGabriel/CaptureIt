<template>
  <div class="gallery section" id="gallery">
    <h2 class="section-title" data-aos="fade-up">Memories in the Making</h2>
    <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>

    <!-- Masonry Grid -->
    <div class="masonry-grid" data-aos="fade-up" data-aos-delay="200">
      <div
        v-for="(photo, index) in photos"
        :key="index"
        class="masonry-item"
        :style="{ '--delay': index * 0.1 + 's' }"
        @click="openLightbox(index)"
      >
        <img
          :src="photo.src"
          :alt="photo.caption || 'Gallery photo'"
          loading="lazy"
        />
        <div class="photo-overlay">
          <span class="photo-caption">{{ photo.caption }}</span>
        </div>
      </div>
    </div>

    <!-- Lightbox -->
    <transition name="lightbox">
      <div
        v-if="lightboxIndex !== null"
        class="lightbox-overlay"
        @click.self="closeLightbox"
      >
        <button class="lightbox-close" @click="closeLightbox">✕</button>
        <button class="lightbox-nav prev" @click.stop="prevPhoto">‹</button>
        <div class="lightbox-content">
          <img
            :src="photos[lightboxIndex].src"
            :alt="photos[lightboxIndex].caption"
          />
          <p v-if="photos[lightboxIndex].caption" class="lightbox-caption">
            {{ photos[lightboxIndex].caption }}
          </p>
        </div>
        <button class="lightbox-nav next" @click.stop="nextPhoto">›</button>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  name: "PhotoGallery",
  data() {
    return {
      lightboxIndex: null,
      photos: [
        {
          src: "https://images.unsplash.com/photo-1519741497674-611481863552?w=600&h=400&fit=crop",
          caption: "A glance across the room",
        },
        {
          src: "https://images.unsplash.com/photo-1606216794074-735e91aa2c92?w=400&h=600&fit=crop",
          caption: "The proposal",
        },
        {
          src: "https://images.unsplash.com/photo-1465495976277-4387d4b0b4c6?w=600&h=400&fit=crop",
          caption: "Together forever",
        },
        {
          src: "https://images.unsplash.com/photo-1511285560929-80b456fea0bc?w=400&h=500&fit=crop",
          caption: "Golden hour",
        },
        {
          src: "https://images.unsplash.com/photo-1522673607200-164d1b6ce486?w=600&h=400&fit=crop",
          caption: "Our adventure begins",
        },
        {
          src: "https://images.unsplash.com/photo-1583939003579-730e3918a45a?w=400&h=600&fit=crop",
          caption: "Love in bloom",
        },
        {
          src: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600&h=350&fit=crop",
          caption: "Side by side",
        },
        {
          src: "https://images.unsplash.com/photo-1529636798458-92182e662485?w=500&h=500&fit=crop",
          caption: "Our story",
        },
      ],
    };
  },
  methods: {
    openLightbox(index) {
      this.lightboxIndex = index;
      document.body.style.overflow = "hidden";
    },
    closeLightbox() {
      this.lightboxIndex = null;
      document.body.style.overflow = "";
    },
    nextPhoto() {
      this.lightboxIndex = (this.lightboxIndex + 1) % this.photos.length;
    },
    prevPhoto() {
      this.lightboxIndex =
        (this.lightboxIndex - 1 + this.photos.length) % this.photos.length;
    },
  },
  mounted() {
    document.addEventListener("keydown", (e) => {
      if (this.lightboxIndex === null) return;
      if (e.key === "Escape") this.closeLightbox();
      if (e.key === "ArrowRight") this.nextPhoto();
      if (e.key === "ArrowLeft") this.prevPhoto();
    });
  },
};
</script>

<style scoped>
.gallery {
  text-align: center;
  max-width: 1000px;
  margin: 0 auto;
}

.section-title {
  font-family: var(--font-script);
  font-size: clamp(1.75rem, 5vw, 3rem);
  color: #c5a059;
}

.masonry-grid {
  column-count: 2;
  column-gap: 1rem;
  margin-top: 2.5rem;
}

.masonry-item {
  break-inside: avoid;
  margin-bottom: 1rem;
  position: relative;
  cursor: pointer;
  overflow: hidden;
  border-radius: 4px;
  background: rgba(197, 160, 89, 0.05);
  opacity: 0;
  animation: fadeIn 0.6s ease forwards;
  animation-delay: var(--delay);
}

/* Delicate golden border that reveals on hover (pseudo-element frame) */
.masonry-item::before {
  content: "";
  position: absolute;
  inset: 10px;
  border: 1px solid rgba(212, 185, 120, 0);
  z-index: 10;
  pointer-events: none;
  transition:
    border-color 0.8s ease,
    inset 0.8s ease;
}

.masonry-item:hover::before {
  border-color: rgba(212, 185, 120, 0.4);
  inset: 15px; /* Compresses inward slightly for depth */
}

@keyframes fadeIn {
  to {
    opacity: 1;
  }
}

.masonry-item img {
  width: 100%;
  display: block;
  transform: scale(1);
  transition:
    transform 4s cubic-bezier(0.25, 0.46, 0.45, 0.94),
    filter 0.8s ease;
  filter: sepia(0.15) contrast(0.95);
}

.masonry-item:hover img {
  transform: scale(1.08); /* Slow, dramatic Ken Burns zoom */
  filter: sepia(0) contrast(1.05) saturate(1.1);
}

.photo-overlay {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 2rem 1rem 1rem;
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.6));
  opacity: 0;
  transition: opacity 0.4s ease;
}

.masonry-item:hover .photo-overlay {
  opacity: 1;
}

.photo-caption {
  font-family: var(--font-serif);
  font-size: 0.8rem;
  color: rgba(255, 255, 255, 0.9);
  letter-spacing: 0.1em;
}

/* Lightbox */
.lightbox-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.95);
  z-index: 10001;
  display: flex;
  align-items: center;
  justify-content: center;
}

.lightbox-content {
  max-width: 85vw;
  max-height: 85vh;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.lightbox-content img {
  max-width: 100%;
  max-height: 80vh;
  object-fit: contain;
}

.lightbox-caption {
  margin-top: 1rem;
  font-family: var(--font-serif);
  font-size: 0.9rem;
  color: rgba(255, 255, 255, 0.7);
  letter-spacing: 0.1em;
}

.lightbox-close {
  position: absolute;
  top: 1.5rem;
  right: 1.5rem;
  background: none;
  border: none;
  color: rgba(255, 255, 255, 0.6);
  font-size: 1.5rem;
  cursor: pointer;
  transition: color 0.3s ease;
  z-index: 2;
}

.lightbox-close:hover {
  color: #fff;
}

.lightbox-nav {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: none;
  color: rgba(255, 255, 255, 0.5);
  font-size: 3rem;
  cursor: pointer;
  padding: 1rem;
  transition: color 0.3s ease;
  z-index: 2;
}

.lightbox-nav:hover {
  color: #fff;
}
.lightbox-nav.prev {
  left: 1rem;
}
.lightbox-nav.next {
  right: 1rem;
}

/* Transitions */
.lightbox-enter-active,
.lightbox-leave-active {
  transition: opacity 0.4s ease;
}
.lightbox-enter-from,
.lightbox-leave-to {
  opacity: 0;
}

@media (min-width: 768px) {
  .masonry-grid {
    column-count: 3;
  }
}

@media (min-width: 1024px) {
  .masonry-grid {
    column-count: 4;
  }
}
</style>
