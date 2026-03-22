<template>
  <div class="gallery-page">
    <canvas ref="galleryCanvas" class="gallery-particles"></canvas>

    <!-- Navigation -->
    <nav class="gallery-nav">
      <router-link to="/" class="back-link">
        <svg
          width="16"
          height="16"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="1.5"
        >
          <path d="M19 12H5m7-7-7 7 7 7" />
        </svg>
        <span>Return Home</span>
      </router-link>
    </nav>

    <!-- Main Content for Wedding Day -->
    <template v-if="store.weddingDayMode">
      <!-- Hero Section with Upload CTA -->
      <header class="gallery-hero">
        <div class="ornament-line">
          <span class="orn-dash"></span><span class="orn-diamond">◆</span
          ><span class="orn-dash"></span>
        </div>
        <h1 class="gallery-title" data-aos="fade-up" data-aos-duration="1200">
          <span class="title-script">Capture the Moment</span>
        </h1>
        <p class="gallery-subtitle" data-aos="fade-up" data-aos-delay="200">
          Every stolen glance, every joyful tear, every burst of laughter —<br />
          help us capture these precious moments
        </p>

        <!-- Big upload CTA -->
        <div class="upload-hero-cta" data-aos="fade-up" data-aos-delay="400">
          <button class="hero-upload-btn" @click="showUploadModal = true">
            <div class="upload-icon-circle">
              <svg
                width="28"
                height="28"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="1.5"
              >
                <path
                  d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
                />
                <circle cx="12" cy="13" r="4" />
              </svg>
            </div>
            <div class="upload-btn-text">
              <span class="upload-primary">Share Your Photos</span>
              <span class="upload-secondary"
                >Tap to upload a moment from the celebration</span
              >
            </div>
          </button>
          <p class="upload-hint">
            {{ allPhotos.length }} memories captured so far
          </p>
        </div>

        <div class="ornament-line" data-aos="zoom-in" data-aos-delay="500">
          <span class="orn-dash"></span><span class="orn-diamond">◆</span
          ><span class="orn-dash"></span>
        </div>
      </header>

      <!-- Content when photos exist -->
      <template v-if="allPhotos.length">
        <!-- Auto-scrolling Marquee Rows -->
        <section class="marquee-section">
          <div
            v-for="(row, rIdx) in marqueeRows"
            :key="rIdx"
            class="marquee-row"
            :class="{ reverse: rIdx % 2 === 1 }"
          >
            <div
              class="marquee-track"
              :style="{ animationDuration: 30 + rIdx * 10 + 's' }"
            >
              <div
                v-for="(photo, pIdx) in row"
                :key="'a-' + pIdx"
                class="marquee-item"
                @click="openLightbox(photo)"
              >
                <img :src="photo.url" :alt="photo.caption" loading="lazy" />
                <div class="marquee-overlay">
                  <span v-if="photo.caption" class="marquee-caption">{{
                    photo.caption
                  }}</span>
                  <span v-if="photo.author" class="marquee-author"
                    >📷 {{ photo.author }}</span
                  >
                </div>
              </div>
              <div
                v-for="(photo, pIdx) in row"
                :key="'b-' + pIdx"
                class="marquee-item"
                @click="openLightbox(photo)"
              >
                <img :src="photo.url" :alt="photo.caption" loading="lazy" />
                <div class="marquee-overlay">
                  <span v-if="photo.caption" class="marquee-caption">{{
                    photo.caption
                  }}</span>
                  <span v-if="photo.author" class="marquee-author"
                    >📷 {{ photo.author }}</span
                  >
                </div>
              </div>
            </div>
          </div>
        </section>

        <!-- Call to action between sections -->
        <div class="mid-cta" data-aos="fade-up">
          <p class="mid-text">See a moment worth remembering?</p>
          <button class="mid-upload-btn" @click="showUploadModal = true">
            <svg
              width="18"
              height="18"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="1.5"
            >
              <path d="M12 5v14m-7-7h14" />
            </svg>
            <span>Add Your Photo</span>
          </button>
        </div>

        <!-- Static Grid Section -->
        <section class="grid-section" data-aos="fade-up">
          <h2 class="grid-heading">All Photos</h2>
          <div class="photo-grid">
            <div
              v-for="(photo, idx) in allPhotos"
              :key="idx"
              class="grid-item"
              :class="'grid-span-' + getSpan(idx)"
              data-aos="fade-up"
              :data-aos-delay="(idx % 8) * 50"
              @click="openLightbox(photo)"
            >
              <img :src="photo.url" :alt="photo.caption" loading="lazy" />
              <div class="grid-overlay">
                <span v-if="photo.caption" class="grid-caption">{{
                  photo.caption
                }}</span>
                <span v-if="photo.author" class="grid-author"
                  >📷 {{ photo.author }}</span
                >
              </div>
            </div>
          </div>
        </section>
      </template>

      <!-- Empty State -->
      <section v-else class="empty-state" data-aos="fade-up">
        <div class="empty-icon">
          <svg
            width="64"
            height="64"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="0.8"
          >
            <path
              d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
            />
            <circle cx="12" cy="13" r="4" />
          </svg>
        </div>
        <h2 class="empty-title">The Story Awaits</h2>
        <p class="empty-text">
          No moments have been captured yet — but every great love story<br />
          begins with a single frame. Be the first to share one.
        </p>
        <button class="empty-cta" @click="showUploadModal = true">
          <svg
            width="18"
            height="18"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="1.5"
          >
            <path d="M12 5v14m-7-7h14" />
          </svg>
          <span>Capture the First Moment</span>
        </button>
      </section>

      <!-- Floating Upload Button -->
      <button
        class="fab-upload"
        @click="showUploadModal = true"
        title="Upload a photo"
      >
        <svg
          width="22"
          height="22"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
        >
          <path
            d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
          />
          <circle cx="12" cy="13" r="4" />
        </svg>
        <span class="fab-label">Capture</span>
      </button>
    </template>

    <!-- Early Access State -->
    <section v-else class="early-access-section" style="display: flex; align-items: center; justify-content: center; min-height: 80vh;">
      <div class="empty-state" data-aos="fade-up">
        <div class="empty-icon">
          <svg
            width="64"
            height="64"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="0.8"
          >
            <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
            <path d="M7 11V7a5 5 0 0 1 10 0v4" />
          </svg>
        </div>
        <h2 class="empty-title">Gallery Opens on Wedding Day</h2>
        <p class="empty-text">
          Please come back on our wedding day to view and<br />
          share moments from our special day!
        </p>
      </div>
    </section>

    <!-- Lightbox -->
    <transition name="lightbox-fade">
      <div v-if="lightboxPhoto" class="lightbox" @click.self="closeLightbox">
        <button class="lb-close" @click="closeLightbox">✕</button>
        <button class="lb-nav lb-prev" @click.stop="prevPhoto">‹</button>
        <div class="lb-content">
          <img :src="lightboxPhoto.url" :alt="lightboxPhoto.caption" />
          <div
            v-if="lightboxPhoto.caption || lightboxPhoto.author"
            class="lb-info"
          >
            <p v-if="lightboxPhoto.caption" class="lb-caption">
              {{ lightboxPhoto.caption }}
            </p>
            <p v-if="lightboxPhoto.author" class="lb-author">
              — {{ lightboxPhoto.author }}
            </p>
          </div>
        </div>
        <button class="lb-nav lb-next" @click.stop="nextPhoto">›</button>
      </div>
    </transition>

    <!-- Upload Modal -->
    <transition name="modal-fade">
      <div
        v-if="showUploadModal"
        class="upload-modal"
        @click.self="showUploadModal = false"
      >
        <div class="upload-card">
          <button class="modal-close" @click="showUploadModal = false">
            ✕
          </button>

          <div class="modal-header">
            <div class="modal-icon">
              <svg
                width="32"
                height="32"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="1.2"
              >
                <path
                  d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
                />
                <circle cx="12" cy="13" r="4" />
              </svg>
            </div>
            <h3 class="upload-title">Share a Memory</h3>
            <p class="upload-desc">
              Your photo will join the couple's living gallery
            </p>
          </div>

          <div
            class="upload-zone"
            :class="{ 'drag-over': dragging, 'has-preview': uploadPreview }"
            @dragover.prevent="dragging = true"
            @dragleave="dragging = false"
            @drop.prevent="handleDrop"
          >
            <input
              type="file"
              ref="fileInput"
              accept="image/*"
              @change="handleFile"
              class="hidden-input"
            />
            <div
              v-if="!uploadPreview"
              class="zone-empty"
              @click="$refs.fileInput.click()"
            >
              <div class="zone-illustration">
                <svg
                  width="48"
                  height="48"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="0.8"
                >
                  <rect x="3" y="3" width="18" height="18" rx="2" />
                  <circle cx="8.5" cy="8.5" r="1.5" />
                  <path d="m21 15-5-5L5 21" />
                </svg>
                <div class="zone-plus">+</div>
              </div>
              <span class="zone-text-primary">Tap to choose a photo</span>
              <span class="zone-text-secondary">or drag and drop here</span>
            </div>
            <div v-else class="zone-preview">
              <img :src="uploadPreview" alt="Upload preview" />
              <button class="preview-remove" @click="clearUpload">✕</button>
            </div>
          </div>

          <div class="upload-fields">
            <div class="field-group">
              <label class="field-label">Caption</label>
              <input
                v-model="uploadForm.caption"
                type="text"
                placeholder="Describe this moment..."
              />
            </div>
            <div class="field-group">
              <label class="field-label">Your Name</label>
              <input
                v-model="uploadForm.author"
                type="text"
                placeholder="Who captured this?"
              />
            </div>
          </div>

          <button
            class="upload-submit"
            :disabled="!uploadPreview"
            @click="submitUpload"
          >
            <svg
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
            >
              <path d="M12 5v14m-7-7h14" />
            </svg>
            <span>Add to Gallery</span>
          </button>
        </div>
      </div>
    </transition>

    <!-- Footer -->
    <footer class="gallery-footer">
      <div class="footer-ornament">❧</div>
      <p>Jonathan &amp; Norma · April 25, 2026</p>
    </footer>
  </div>
</template>

<script>
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "GalleryView",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
  data() {
    return {
      allPhotos: [
        {
          url: "https://images.unsplash.com/photo-1519741497674-611481863552?w=600&h=400&fit=crop",
          caption: "The proposal ✨",
          author: "Sarah",
        },
        {
          url: "https://images.unsplash.com/photo-1583939003579-730e3918a45a?w=600&h=400&fit=crop",
          caption: "Engagement party",
          author: "David",
        },
        {
          url: "https://images.unsplash.com/photo-1511285560929-80b456fea0bc?w=600&h=400&fit=crop",
          caption: "Our first dance practice",
          author: "Rachel",
        },
        {
          url: "https://images.unsplash.com/photo-1465495976277-4387d4b0b4c6?w=600&h=400&fit=crop",
          caption: "Sunset at the vineyard",
          author: "Marco",
        },
        {
          url: "https://images.unsplash.com/photo-1522673607200-164d1b6ce486?w=600&h=400&fit=crop",
          caption: "Cake tasting day!",
          author: "Elizabeth",
        },
        {
          url: "https://images.unsplash.com/photo-1529636798458-92182e662485?w=600&h=400&fit=crop",
          caption: "The venue 🏡",
          author: "Thomas",
        },
        {
          url: "https://images.unsplash.com/photo-1460978812857-470ed1c77af0?w=600&h=400&fit=crop",
          caption: "Rehearsal dinner",
          author: "Sophie",
        },
        {
          url: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=600&h=400&fit=crop",
          caption: "Groomsmen getting ready",
          author: "Jake",
        },
        {
          url: "https://images.unsplash.com/photo-1519225421980-715cb0215aed?w=600&h=400&fit=crop",
          caption: "Floral arrangements 🌸",
          author: "Emily",
        },
        {
          url: "https://images.unsplash.com/photo-1478146059778-26028b07395a?w=600&h=400&fit=crop",
          caption: "The rings",
          author: "Jonathan",
        },
        {
          url: "https://images.unsplash.com/photo-1544078751-58fee2d8a03b?w=600&h=400&fit=crop",
          caption: "Bridesmaids 💕",
          author: "Rachel",
        },
        {
          url: "https://images.unsplash.com/photo-1550005809-91ad75fb315f?w=600&h=400&fit=crop",
          caption: "Table arrangements",
          author: "Norma",
        },
      ],
      lightboxPhoto: null,
      lightboxIndex: -1,
      showUploadModal: false,
      uploadPreview: null,
      uploadFile: null,
      dragging: false,
      uploadForm: { caption: "", author: "" },
      particles: [],
      animFrame: null,
    };
  },
  computed: {
    marqueeRows() {
      const rows = [[], [], []];
      this.allPhotos.forEach((p, i) => rows[i % 3].push(p));
      return rows;
    },
  },
  mounted() {
    this.initParticles();
    window.addEventListener("keydown", this.handleKey);
  },
  beforeUnmount() {
    cancelAnimationFrame(this.animFrame);
    window.removeEventListener("keydown", this.handleKey);
  },
  methods: {
    getSpan(idx) {
      const pattern = [1, 1, 2, 1, 1, 1, 2, 1];
      return pattern[idx % pattern.length];
    },
    openLightbox(photo) {
      this.lightboxIndex = this.allPhotos.indexOf(photo);
      this.lightboxPhoto = photo;
    },
    closeLightbox() {
      this.lightboxPhoto = null;
      this.lightboxIndex = -1;
    },
    prevPhoto() {
      this.lightboxIndex =
        (this.lightboxIndex - 1 + this.allPhotos.length) %
        this.allPhotos.length;
      this.lightboxPhoto = this.allPhotos[this.lightboxIndex];
    },
    nextPhoto() {
      this.lightboxIndex = (this.lightboxIndex + 1) % this.allPhotos.length;
      this.lightboxPhoto = this.allPhotos[this.lightboxIndex];
    },
    handleKey(e) {
      if (!this.lightboxPhoto) return;
      if (e.key === "Escape") this.closeLightbox();
      if (e.key === "ArrowLeft") this.prevPhoto();
      if (e.key === "ArrowRight") this.nextPhoto();
    },
    handleFile(e) {
      const f = e.target.files[0];
      if (f) {
        this.uploadFile = f;
        this.uploadPreview = URL.createObjectURL(f);
      }
    },
    handleDrop(e) {
      this.dragging = false;
      const f = e.dataTransfer.files[0];
      if (f && f.type.startsWith("image/")) {
        this.uploadFile = f;
        this.uploadPreview = URL.createObjectURL(f);
      }
    },
    clearUpload() {
      if (this.uploadPreview) URL.revokeObjectURL(this.uploadPreview);
      this.uploadFile = null;
      this.uploadPreview = null;
    },
    submitUpload() {
      if (!this.uploadPreview) return;
      this.allPhotos.unshift({
        url: this.uploadPreview,
        caption: this.uploadForm.caption,
        author: this.uploadForm.author || "Guest",
      });
      this.uploadForm = { caption: "", author: "" };
      this.uploadFile = null;
      this.uploadPreview = null;
      this.showUploadModal = false;
    },
    initParticles() {
      const canvas = this.$refs.galleryCanvas;
      if (!canvas) return;
      const ctx = canvas.getContext("2d");
      const resize = () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
      };
      resize();
      this.particles = [];
      for (let i = 0; i < 25; i++) {
        this.particles.push({
          x: Math.random() * canvas.width,
          y: Math.random() * canvas.height,
          r: Math.random() * 1.2 + 0.3,
          dx: (Math.random() - 0.5) * 0.08,
          dy: -Math.random() * 0.06 - 0.02,
          o: Math.random() * 0.2 + 0.05,
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
          const flicker = Math.sin(t * 1.5 + p.phase) * 0.3 + 0.7;
          ctx.beginPath();
          ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
          ctx.fillStyle = `rgba(197, 160, 89, ${p.o * flicker})`;
          ctx.fill();
        });
        this.animFrame = requestAnimationFrame(animate);
      };
      animate();
      window.addEventListener("resize", resize);
    },
  },
};
</script>

<style scoped>
.gallery-page {
  min-height: 100vh;
  background: #0f1f18;
  color: #f0f0ee;
  position: relative;
  overflow-x: hidden;
}
.gallery-particles {
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 0;
}

/* ─── Nav ─── */
.gallery-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  padding: 1.25rem 2rem;
  background: linear-gradient(to bottom, rgba(15, 31, 24, 0.95), transparent);
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.back-link {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  color: rgba(197, 160, 89, 0.6);
  text-decoration: none;
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  transition: color 0.3s;
}
.back-link:hover {
  color: #c5a059;
}

/* ─── Hero ─── */
.gallery-hero {
  padding: 7rem 2rem 2rem;
  text-align: center;
  position: relative;
  z-index: 1;
}
.ornament-line {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  margin: 1.5rem 0;
}
.orn-dash {
  width: 60px;
  height: 1px;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(197, 160, 89, 0.3),
    transparent
  );
}
.orn-diamond {
  color: rgba(197, 160, 89, 0.4);
  font-size: 0.5rem;
}
.gallery-title {
  margin: 0;
}
.title-script {
  font-family: var(--font-script);
  font-size: clamp(2.8rem, 10vw, 4.5rem);
  color: #f0f0ee;
  text-shadow: 0 0 60px rgba(197, 160, 89, 0.1);
}
.gallery-subtitle {
  font-family: var(--font-serif);
  font-size: 0.85rem;
  color: rgba(240, 240, 238, 0.45);
  letter-spacing: 0.06em;
  line-height: 1.8;
  max-width: 480px;
  margin: 0 auto;
}

/* ─── Hero Upload CTA ─── */
.upload-hero-cta {
  margin: 2rem auto 0;
  max-width: 440px;
}
.hero-upload-btn {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 1.25rem;
  padding: 1.25rem 1.5rem;
  cursor: pointer;
  background: rgba(197, 160, 89, 0.06);
  border: 1px solid rgba(197, 160, 89, 0.2);
  color: #f0f0ee;
  text-align: left;
  transition: all 0.4s ease;
  position: relative;
  overflow: hidden;
}
.hero-upload-btn::before {
  content: "";
  position: absolute;
  inset: 0;
  background: linear-gradient(
    135deg,
    transparent,
    rgba(197, 160, 89, 0.04),
    transparent
  );
  opacity: 0;
  transition: opacity 0.4s;
}
.hero-upload-btn:hover {
  border-color: rgba(197, 160, 89, 0.45);
  background: rgba(197, 160, 89, 0.1);
  transform: translateY(-2px);
  box-shadow:
    0 8px 30px rgba(0, 0, 0, 0.2),
    0 0 40px rgba(197, 160, 89, 0.06);
}
.hero-upload-btn:hover::before {
  opacity: 1;
}
.upload-icon-circle {
  width: 56px;
  height: 56px;
  flex-shrink: 0;
  border: 1px solid rgba(197, 160, 89, 0.35);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c5a059;
  background: rgba(197, 160, 89, 0.06);
  transition: all 0.4s;
}
.hero-upload-btn:hover .upload-icon-circle {
  background: rgba(197, 160, 89, 0.12);
  box-shadow: 0 0 20px rgba(197, 160, 89, 0.15);
}
.upload-btn-text {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}
.upload-primary {
  font-family: var(--font-serif);
  font-size: 0.85rem;
  letter-spacing: 0.12em;
  text-transform: uppercase;
  color: #c5a059;
  font-weight: 500;
}
.upload-secondary {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  color: rgba(240, 240, 238, 0.35);
}
.upload-hint {
  font-family: var(--font-serif);
  font-size: 0.65rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.3);
  margin-top: 0.75rem;
}

/* ─── Marquee ─── */
.marquee-section {
  position: relative;
  z-index: 1;
  padding: 2rem 0;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  overflow: hidden;
}
.marquee-row {
  width: 100%;
  overflow: hidden;
}
.marquee-track {
  display: flex;
  gap: 0.75rem;
  width: max-content;
  animation: marqueeScroll linear infinite;
}
.marquee-row.reverse .marquee-track {
  animation-direction: reverse;
}
.marquee-item {
  position: relative;
  width: 300px;
  height: 200px;
  flex-shrink: 0;
  overflow: hidden;
  cursor: pointer;
  border: 1px solid rgba(197, 160, 89, 0.06);
  transition:
    transform 0.4s ease,
    border-color 0.4s,
    box-shadow 0.4s;
}
.marquee-item:hover {
  transform: scale(1.04);
  border-color: rgba(197, 160, 89, 0.25);
  z-index: 2;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
}
.marquee-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  filter: brightness(0.92);
  transition:
    filter 0.4s,
    transform 0.6s;
}
.marquee-item:hover img {
  filter: brightness(1);
  transform: scale(1.06);
}
.marquee-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgba(15, 31, 24, 0.75), transparent 50%);
  opacity: 0;
  transition: opacity 0.4s;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  padding: 0.75rem 1rem;
}
.marquee-item:hover .marquee-overlay {
  opacity: 1;
}
.marquee-caption {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  color: rgba(240, 240, 238, 0.85);
}
.marquee-author {
  font-size: 0.55rem;
  color: rgba(197, 160, 89, 0.55);
  margin-top: 0.15rem;
}
@keyframes marqueeScroll {
  from {
    transform: translateX(0);
  }
  to {
    transform: translateX(-50%);
  }
}

/* ─── Mid CTA ─── */
.mid-cta {
  text-align: center;
  padding: 3rem 2rem;
  position: relative;
  z-index: 1;
}
.mid-text {
  font-family: var(--font-serif);
  font-size: 0.75rem;
  color: rgba(240, 240, 238, 0.3);
  letter-spacing: 0.1em;
  margin-bottom: 1rem;
}
.mid-upload-btn {
  display: inline-flex;
  align-items: center;
  gap: 0.6rem;
  padding: 0.75rem 2rem;
  background: transparent;
  border: 1px solid rgba(197, 160, 89, 0.2);
  color: #c5a059;
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  cursor: pointer;
  transition: all 0.4s;
}
.mid-upload-btn:hover {
  border-color: rgba(197, 160, 89, 0.5);
  background: rgba(197, 160, 89, 0.05);
}

/* ─── Grid ─── */
.grid-section {
  position: relative;
  z-index: 1;
  padding: 2rem 1.5rem 4rem;
  max-width: 1200px;
  margin: 0 auto;
}
.grid-heading {
  text-align: center;
  font-family: var(--font-serif);
  font-size: 0.7rem;
  font-weight: 400;
  letter-spacing: 0.35em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.3);
  margin-bottom: 2rem;
}
.photo-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 0.75rem;
}
.grid-item {
  position: relative;
  overflow: hidden;
  cursor: pointer;
  aspect-ratio: 4/3;
  border: 1px solid rgba(197, 160, 89, 0.05);
  transition:
    transform 0.4s,
    border-color 0.4s,
    box-shadow 0.4s;
}
.grid-span-2 {
  grid-column: span 2;
}
.grid-item:hover {
  transform: translateY(-3px);
  border-color: rgba(197, 160, 89, 0.2);
  box-shadow: 0 10px 35px rgba(0, 0, 0, 0.25);
}
.grid-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
  filter: brightness(0.93);
  transition:
    filter 0.4s,
    transform 0.6s;
}
.grid-item:hover img {
  filter: brightness(1);
  transform: scale(1.05);
}
.grid-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(to top, rgba(15, 31, 24, 0.7), transparent 55%);
  opacity: 0;
  transition: opacity 0.4s;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  padding: 1rem;
}
.grid-item:hover .grid-overlay {
  opacity: 1;
}
.grid-caption {
  font-family: var(--font-serif);
  font-size: 0.75rem;
  color: rgba(240, 240, 238, 0.85);
}
.grid-author {
  font-size: 0.6rem;
  color: rgba(197, 160, 89, 0.5);
  margin-top: 0.2rem;
}

@media (max-width: 768px) {
  .photo-grid {
    grid-template-columns: repeat(2, 1fr);
  }
  .grid-span-2 {
    grid-column: span 2;
  }
}
@media (max-width: 480px) {
  .photo-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 0.4rem;
  }
  .grid-span-2 {
    grid-column: span 1;
  }
  .marquee-item {
    width: 220px;
    height: 150px;
  }
}

/* ─── Empty State ─── */
.empty-state {
  position: relative;
  z-index: 1;
  text-align: center;
  padding: 6rem 2rem 8rem;
  max-width: 520px;
  margin: 0 auto;
}
.empty-icon {
  color: rgba(197, 160, 89, 0.25);
  margin-bottom: 2rem;
  animation: emptyFloat 4s ease-in-out infinite;
  display: flex;
  align-items: center;
  justify-content: center;
}
@keyframes emptyFloat {
  0%,
  100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-10px);
  }
}
.empty-title {
  font-family: var(--font-script);
  font-size: clamp(2rem, 6vw, 3rem);
  color: #f0f0ee;
  margin: 0 0 1rem;
  text-shadow: 0 0 40px rgba(197, 160, 89, 0.1);
}
.empty-text {
  font-family: var(--font-serif);
  font-size: 0.9rem;
  line-height: 1.9;
  color: rgba(240, 240, 238, 0.4);
  font-style: italic;
  margin: 0 0 2.5rem;
}
.empty-cta {
  display: inline-flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem 2.5rem;
  background: linear-gradient(135deg, #c5a059, #d4b978);
  color: #1a2f25;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  border: none;
  cursor: pointer;
  transition: all 0.4s ease;
  border-radius: 50px;
  box-shadow: 0 6px 24px rgba(197, 160, 89, 0.3);
}
.empty-cta:hover {
  transform: translateY(-2px) scale(1.04);
  box-shadow: 0 10px 35px rgba(197, 160, 89, 0.4);
}

/* ─── FAB ─── */
.fab-upload {
  position: fixed;
  bottom: 2rem;
  right: 5.5rem;
  z-index: 9989;
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.85rem 1.4rem;
  background: linear-gradient(135deg, #c5a059, #d4b978);
  color: #1a2f25;
  border: none;
  cursor: pointer;
  font-family: var(--font-serif);
  font-size: 0.65rem;
  font-weight: 600;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  box-shadow: 0 6px 24px rgba(197, 160, 89, 0.3);
  transition: all 0.4s;
  border-radius: 50px;
}
.fab-upload:hover {
  transform: translateY(-2px) scale(1.04);
  box-shadow: 0 10px 35px rgba(197, 160, 89, 0.4);
}
.fab-label {
  display: inline;
}
@media (max-width: 480px) {
  .fab-label {
    display: none;
  }
  .fab-upload {
    border-radius: 50%;
    padding: 1rem;
  }
}

/* ─── Lightbox ─── */
.lightbox {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: rgba(10, 20, 15, 0.96);
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(12px);
}
.lb-close {
  position: absolute;
  top: 1.5rem;
  right: 1.5rem;
  background: none;
  border: 1px solid rgba(197, 160, 89, 0.2);
  color: rgba(240, 240, 238, 0.5);
  font-size: 1rem;
  width: 42px;
  height: 42px;
  cursor: pointer;
  transition: all 0.3s;
}
.lb-close:hover {
  color: #c5a059;
  border-color: rgba(197, 160, 89, 0.5);
}
.lb-nav {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: none;
  border: 1px solid rgba(197, 160, 89, 0.15);
  color: rgba(240, 240, 238, 0.45);
  font-size: 2.2rem;
  width: 50px;
  height: 50px;
  cursor: pointer;
  transition: all 0.3s;
}
.lb-nav:hover {
  color: #c5a059;
  border-color: rgba(197, 160, 89, 0.4);
  background: rgba(197, 160, 89, 0.05);
}
.lb-prev {
  left: 1.5rem;
}
.lb-next {
  right: 1.5rem;
}
.lb-content {
  max-width: 82vw;
  max-height: 82vh;
  text-align: center;
}
.lb-content img {
  max-width: 100%;
  max-height: 75vh;
  object-fit: contain;
  display: block;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
}
.lb-info {
  margin-top: 1rem;
}
.lb-caption {
  font-family: var(--font-serif);
  font-size: 0.85rem;
  color: rgba(240, 240, 238, 0.7);
}
.lb-author {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  color: rgba(197, 160, 89, 0.5);
  margin-top: 0.25rem;
}
.lightbox-fade-enter-active,
.lightbox-fade-leave-active {
  transition: opacity 0.3s;
}
.lightbox-fade-enter-from,
.lightbox-fade-leave-to {
  opacity: 0;
}

/* ─── Upload Modal ─── */
.upload-modal {
  position: fixed;
  inset: 0;
  z-index: 9999;
  background: rgba(10, 20, 15, 0.92);
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
  padding: 1rem;
}
.upload-card {
  background: linear-gradient(
    170deg,
    rgba(30, 52, 40, 0.98),
    rgba(20, 38, 30, 0.98)
  );
  border: 1px solid rgba(197, 160, 89, 0.12);
  padding: 2.5rem;
  max-width: 460px;
  width: 100%;
  position: relative;
  box-shadow: 0 30px 80px rgba(0, 0, 0, 0.4);
}
.modal-close {
  position: absolute;
  top: 1rem;
  right: 1rem;
  background: none;
  border: none;
  color: rgba(240, 240, 238, 0.3);
  font-size: 1.1rem;
  cursor: pointer;
  transition: color 0.3s;
}
.modal-close:hover {
  color: #c5a059;
}
.modal-header {
  text-align: center;
  margin-bottom: 1.5rem;
}
.modal-icon {
  width: 64px;
  height: 64px;
  margin: 0 auto 1rem;
  border: 1px solid rgba(197, 160, 89, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c5a059;
  background: rgba(197, 160, 89, 0.04);
}
.upload-title {
  font-family: var(--font-serif);
  font-size: 1.1rem;
  color: #f0f0ee;
  letter-spacing: 0.1em;
  margin: 0 0 0.25rem;
}
.upload-desc {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  color: rgba(240, 240, 238, 0.3);
}
.upload-zone {
  border: 1px dashed rgba(197, 160, 89, 0.15);
  margin-bottom: 1.25rem;
  transition: all 0.3s;
  overflow: hidden;
}
.upload-zone.drag-over {
  border-color: rgba(197, 160, 89, 0.45);
  background: rgba(197, 160, 89, 0.04);
}
.hidden-input {
  position: absolute;
  opacity: 0;
  pointer-events: none;
}
.zone-empty {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 2.5rem;
  cursor: pointer;
  color: rgba(197, 160, 89, 0.25);
  transition: color 0.3s;
}
.zone-empty:hover {
  color: rgba(197, 160, 89, 0.5);
}
.zone-illustration {
  position: relative;
}
.zone-plus {
  position: absolute;
  bottom: -2px;
  right: -6px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #c5a059;
  color: #1a2f25;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: 700;
}
.zone-text-primary {
  font-family: var(--font-serif);
  font-size: 0.8rem;
  letter-spacing: 0.08em;
  color: rgba(240, 240, 238, 0.45);
}
.zone-text-secondary {
  font-family: var(--font-serif);
  font-size: 0.65rem;
  color: rgba(240, 240, 238, 0.2);
}
.zone-preview {
  position: relative;
}
.zone-preview img {
  width: 100%;
  height: 220px;
  object-fit: cover;
  display: block;
}
.preview-remove {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background: rgba(15, 31, 24, 0.85);
  border: 1px solid rgba(197, 160, 89, 0.3);
  color: #c5a059;
  font-size: 0.65rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}
.upload-fields {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.field-group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}
.field-label {
  font-family: var(--font-serif);
  font-size: 0.6rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.4);
}
.upload-fields input {
  background: rgba(15, 31, 24, 0.5);
  border: 1px solid rgba(197, 160, 89, 0.1);
  color: #f0f0ee;
  font-family: var(--font-serif);
  font-size: 0.8rem;
  padding: 0.75rem 1rem;
  outline: none;
  transition: border-color 0.3s;
}
.upload-fields input:focus {
  border-color: rgba(197, 160, 89, 0.35);
}
.upload-fields input::placeholder {
  color: rgba(240, 240, 238, 0.2);
}
.upload-submit {
  width: 100%;
  padding: 0.9rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  background: linear-gradient(135deg, #c5a059, #d4b978);
  color: #1a2f25;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  font-weight: 600;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  border: none;
  cursor: pointer;
  transition: all 0.4s;
  position: relative;
  overflow: hidden;
}
.upload-submit:hover:not(:disabled) {
  box-shadow: 0 8px 25px rgba(197, 160, 89, 0.3);
  transform: translateY(-1px);
}
.upload-submit:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}
.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.3s;
}
.modal-fade-enter-from,
.modal-fade-leave-to {
  opacity: 0;
}

/* ─── Footer ─── */
.gallery-footer {
  position: relative;
  z-index: 1;
  text-align: center;
  padding: 3rem 2rem 4rem;
  border-top: 1px solid rgba(197, 160, 89, 0.06);
}
.footer-ornament {
  color: rgba(197, 160, 89, 0.2);
  margin-bottom: 1rem;
}
.gallery-footer p {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.2em;
  color: rgba(240, 240, 238, 0.2);
}
</style>
