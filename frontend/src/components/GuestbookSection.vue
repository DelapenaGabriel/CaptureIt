<template>
  <div class="guestbook section" id="guestbook">
    <h2 class="section-title" data-aos="fade-up">Words of Love</h2>
    <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>
    <p class="section-subtitle" data-aos="fade-up" data-aos-delay="150">
      Leave your wishes for the happy couple
    </p>

    <!-- Message Form -->
    <form
      class="guestbook-form"
      @submit.prevent="submitMessage"
      data-aos="fade-up"
      data-aos-delay="200"
    >
      <div class="form-row">
        <input
          v-model="newEntry.name"
          type="text"
          class="form-input"
          placeholder="Your name"
          required
        />
      </div>
      <div class="form-row">
        <textarea
          v-model="newEntry.message"
          class="form-input form-textarea"
          placeholder="Write your message..."
          rows="3"
          required
        ></textarea>
      </div>

      <!-- Photo Upload -->
      <div
        class="upload-zone"
        :class="{ 'drag-over': isDragging }"
        @dragover.prevent="isDragging = true"
        @dragleave="isDragging = false"
        @drop.prevent="handleDrop"
        @click="$refs.fileInput.click()"
      >
        <input
          ref="fileInput"
          type="file"
          accept="image/*"
          multiple
          hidden
          @change="handleFiles"
        />
        <svg
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="1.5"
        >
          <rect x="3" y="3" width="18" height="18" rx="2" />
          <circle cx="8.5" cy="8.5" r="1.5" />
          <path d="M21 15l-5-5L5 21" />
        </svg>
        <span v-if="selectedFiles.length === 0"
          >Drop photos here or click to upload</span
        >
        <span v-else
          >{{ selectedFiles.length }} photo{{
            selectedFiles.length > 1 ? "s" : ""
          }}
          selected</span
        >
      </div>

      <div v-if="selectedFiles.length > 0" class="file-preview">
        <div v-for="(file, i) in previewUrls" :key="i" class="preview-thumb">
          <img :src="file" alt="Preview" />
          <button type="button" class="remove-file" @click="removeFile(i)">
            ×
          </button>
        </div>
      </div>

      <button type="submit" class="btn-gold submit-btn" :disabled="submitting">
        {{ submitting ? "Posting..." : "Share Your Wishes" }}
      </button>
    </form>

    <!-- Guest Messages -->
    <div class="messages-grid" data-aos="fade-up" data-aos-delay="300">
      <div
        v-for="(entry, index) in displayEntries"
        :key="entry.id"
        class="message-card slide-up-reveal"
        :style="{ animationDelay: `${index * 150}ms` }"
      >
        <div class="message-header">
          <span class="author-initial">{{ entry.name.charAt(0) }}</span>
          <div>
            <p class="author-name">{{ entry.name }}</p>
            <p class="message-date">{{ entry.date }}</p>
          </div>
        </div>
        <p class="message-text">{{ entry.message }}</p>
        <div v-if="entry.photos && entry.photos.length" class="message-photos">
          <img
            v-for="(photo, i) in entry.photos"
            :key="i"
            :src="photo"
            alt="Guest photo"
            @click="openLightbox(photo)"
          />
        </div>
        <button
          class="like-btn"
          :class="{ liked: entry.liked }"
          @click="toggleLike(entry)"
        >
          ♥ {{ entry.likes }}
        </button>
      </div>

      <p v-if="displayEntries.length === 0" class="no-messages">
        Be the first to share your wishes!
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "GuestbookSection",
  data() {
    return {
      isDragging: false,
      submitting: false,
      selectedFiles: [],
      previewUrls: [],
      newEntry: { name: "", message: "" },
      entries: [
        {
          id: 1,
          name: "Elizabeth & James",
          message: "Wishing you both a lifetime of love and happiness!",
          date: "Feb 10, 2026",
          likes: 5,
          liked: false,
          photos: [],
        },
        {
          id: 2,
          name: "Margaret",
          message:
            "So thrilled for you two! Can't wait to celebrate this beautiful union.",
          date: "Feb 8, 2026",
          likes: 3,
          liked: false,
          photos: [],
        },
        {
          id: 3,
          name: "Robert & Catherine",
          message:
            "May your journey together be filled with joy, laughter, and endless love.",
          date: "Feb 5, 2026",
          likes: 7,
          liked: false,
          photos: [],
        },
      ],
    };
  },
  computed: {
    displayEntries() {
      return [...this.entries].sort((a, b) => b.likes - a.likes);
    },
  },
  methods: {
    handleDrop(e) {
      this.isDragging = false;
      const files = Array.from(e.dataTransfer.files).filter((f) =>
        f.type.startsWith("image/"),
      );
      this.addFiles(files);
    },
    handleFiles(e) {
      const files = Array.from(e.target.files);
      this.addFiles(files);
    },
    addFiles(files) {
      this.selectedFiles.push(...files);
      files.forEach((file) => {
        const reader = new FileReader();
        reader.onload = (e) => this.previewUrls.push(e.target.result);
        reader.readAsDataURL(file);
      });
    },
    removeFile(index) {
      this.selectedFiles.splice(index, 1);
      this.previewUrls.splice(index, 1);
    },
    async submitMessage() {
      if (!this.newEntry.name || !this.newEntry.message) return;
      this.submitting = true;
      await new Promise((r) => setTimeout(r, 1000));
      this.entries.unshift({
        id: Date.now(),
        name: this.newEntry.name,
        message: this.newEntry.message,
        date: new Date().toLocaleDateString("en-US", {
          month: "short",
          day: "numeric",
          year: "numeric",
        }),
        likes: 0,
        liked: false,
        photos: [...this.previewUrls],
      });
      this.newEntry = { name: "", message: "" };
      this.selectedFiles = [];
      this.previewUrls = [];
      this.submitting = false;
    },
    toggleLike(entry) {
      entry.liked = !entry.liked;
      entry.likes += entry.liked ? 1 : -1;
    },
    openLightbox(photo) {
      // Will be connected to lightbox component
      window.open(photo, "_blank");
    },
  },
};
</script>

<style scoped>
.guestbook {
  text-align: center;
  max-width: 700px;
  margin: 0 auto;
}

.section-title {
  font-family: var(--font-script);
  font-size: clamp(1.75rem, 5vw, 3rem);
  color: #c5a059;
}

.section-subtitle {
  font-size: 0.8rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
  margin-top: 0.5rem;
}

.guestbook-form {
  margin-top: 2.5rem;
  text-align: left;
}

.form-row {
  margin-bottom: 1rem;
}

.form-input {
  width: 100%;
  padding: 0.875rem 1rem;
  background: rgba(197, 160, 89, 0.03);
  border: 1px solid rgba(197, 160, 89, 0.2);
  color: #f0f0ee;
  font-family: var(--font-serif);
  font-size: 0.95rem;
  outline: none;
  transition: border-color 0.3s ease;
}

.form-input:focus {
  border-color: rgba(197, 160, 89, 0.5);
}

.form-input::placeholder {
  color: rgba(240, 240, 238, 0.25);
}

.form-textarea {
  resize: vertical;
  min-height: 80px;
}

.upload-zone {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
  padding: 1.5rem;
  border: 1px dashed rgba(197, 160, 89, 0.2);
  color: rgba(240, 240, 238, 0.4);
  cursor: pointer;
  font-size: 0.8rem;
  letter-spacing: 0.1em;
  transition: all 0.3s ease;
  margin-bottom: 1rem;
}

.upload-zone:hover,
.upload-zone.drag-over {
  border-color: rgba(197, 160, 89, 0.5);
  color: rgba(197, 160, 89, 0.7);
  background: rgba(197, 160, 89, 0.03);
}

.file-preview {
  display: flex;
  gap: 0.5rem;
  flex-wrap: wrap;
  margin-bottom: 1rem;
}

.preview-thumb {
  position: relative;
  width: 60px;
  height: 60px;
}

.preview-thumb img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border: 1px solid rgba(197, 160, 89, 0.2);
}

.remove-file {
  position: absolute;
  top: -6px;
  right: -6px;
  width: 18px;
  height: 18px;
  border-radius: 50%;
  background: #c5a059;
  color: #1a2f25;
  border: none;
  cursor: pointer;
  font-size: 12px;
  line-height: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.submit-btn {
  width: 100%;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.messages-grid {
  margin-top: 3rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.message-card {
  text-align: left;
  padding: 1.5rem;
  border: 1px solid rgba(197, 160, 89, 0.1);
  background: rgba(197, 160, 89, 0.02);
  transition: all 0.4s var(--ease-elegant);
  position: relative;
  overflow: hidden;
}

/* Elegant staggered entrance */
.slide-up-reveal {
  opacity: 0;
  transform: translateY(20px);
  animation: slideUpFadeIn 0.8s var(--ease-elegant) forwards;
}

@keyframes slideUpFadeIn {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.message-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: -100%;
  width: 50%;
  height: 100%;
  background: linear-gradient(
    90deg,
    transparent,
    rgba(197, 160, 89, 0.05),
    transparent
  );
  transform: skewX(-20deg);
  transition: 0.7s;
}

.message-card:hover {
  border-color: rgba(197, 160, 89, 0.35);
  background: rgba(197, 160, 89, 0.05);
  box-shadow:
    0 8px 30px rgba(0, 0, 0, 0.2),
    0 0 15px rgba(197, 160, 89, 0.05);
  transform: translateY(-2px);
}

.message-card:hover::before {
  left: 200%;
}

.message-header {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 0.75rem;
}

.author-initial {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: rgba(197, 160, 89, 0.15);
  color: #c5a059;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-script);
  font-size: 1.25rem;
  flex-shrink: 0;
}

.author-name {
  font-family: var(--font-serif);
  font-size: 0.9rem;
  color: #f0f0ee;
}

.message-date {
  font-size: 0.7rem;
  color: rgba(240, 240, 238, 0.4);
}

.message-text {
  font-size: 0.9rem;
  color: rgba(240, 240, 238, 0.7);
  line-height: 1.6;
}

.message-photos {
  display: flex;
  gap: 0.5rem;
  margin-top: 0.75rem;
  flex-wrap: wrap;
}

.message-photos img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border: 1px solid rgba(197, 160, 89, 0.15);
  cursor: pointer;
  transition: opacity 0.3s ease;
}

.message-photos img:hover {
  opacity: 0.8;
}

.like-btn {
  margin-top: 0.75rem;
  background: none;
  border: 1px solid rgba(197, 160, 89, 0.15);
  color: rgba(240, 240, 238, 0.4);
  padding: 0.35rem 0.75rem;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.like-btn:hover,
.like-btn.liked {
  color: #c5a059;
  border-color: rgba(197, 160, 89, 0.4);
}

.no-messages {
  font-size: 0.85rem;
  color: rgba(240, 240, 238, 0.4);
  font-style: italic;
  padding: 2rem 0;
}
</style>
