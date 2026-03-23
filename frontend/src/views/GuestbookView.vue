<template>
  <div class="guestbook-page">
    <canvas ref="gbCanvas" class="gb-particles"></canvas>

    <!-- Navigation -->
    <nav class="gb-nav">
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
      <!-- Hero -->
      <header class="gb-hero">
        <div class="ornament-line">
          <span class="orn-dash"></span><span class="orn-diamond">◆</span
          ><span class="orn-dash"></span>
        </div>
        <h1 class="gb-title" data-aos="fade-up" data-aos-duration="1200">
          <span class="title-script">Messages of Love</span>
        </h1>
        <p class="gb-subtitle" data-aos="fade-up" data-aos-delay="200">
          Your words are the most beautiful gift you could give us
        </p>
        <p class="gb-count" data-aos="fade-up" data-aos-delay="400">
          {{ messages.length }} heartfelt wishes
        </p>
        <div class="ornament-line" data-aos="zoom-in" data-aos-delay="300">
          <span class="orn-dash"></span><span class="orn-diamond">◆</span
          ><span class="orn-dash"></span>
        </div>
      </header>

      <!-- Write a Message CTA -->
      <section class="compose-section" data-aos="fade-up">
        <div class="compose-card">
          <div class="compose-header">
            <div class="compose-icon">
              <svg
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="1.2"
              >
                <path d="M20.24 12.24a6 6 0 0 0-8.49-8.49L5 10.5V19h8.5z" />
                <line x1="16" y1="8" x2="2" y2="22" />
                <line x1="17.5" y1="15" x2="9" y2="15" />
              </svg>
            </div>
            <div>
              <h2 class="compose-title">Leave Your Blessing</h2>
              <p class="compose-desc">
                A few words of love, a wish, or a prayer for the couple
              </p>
            </div>
          </div>

          <div class="compose-fields">
            <div class="fields-row">
              <div class="field-group">
                <label>Your Name</label>
                <input
                  v-model="form.name"
                  type="text"
                  placeholder="Who are you?"
                />
              </div>
              <div class="field-group">
                <label>Relationship</label>
                <select v-model="form.relationship">
                  <option value="">Select one...</option>
                  <option>Family of the Bride</option>
                  <option>Family of the Groom</option>
                  <option>Friend of the Couple</option>
                  <option>Colleague</option>
                  <option>Other</option>
                </select>
              </div>
            </div>
            <div class="field-group">
              <label>Your Message</label>
              <textarea
                v-model="form.message"
                placeholder="Write something beautiful..."
                rows="4"
                maxlength="500"
              ></textarea>
              <span class="char-count">{{ form.message.length }} / 500</span>
            </div>
          </div>

          <button
            class="compose-submit"
            :disabled="!form.name || !form.message"
            @click="submitMessage"
          >
            <svg
              width="16"
              height="16"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="2"
            >
              <path d="M22 2 11 13" />
              <path d="M22 2 15 22l-4-9-9-4z" />
            </svg>
            <span>Send Your Wishes</span>
          </button>
        </div>
      </section>

      <!-- Messages Wall -->
      <section class="messages-section">
        <h2 class="wall-heading" data-aos="fade-up">Wall of Love</h2>

        <!-- Empty State -->
        <div v-if="!messages.length" class="empty-state" data-aos="fade-up">
          <div class="empty-icon">
            <svg
              width="64"
              height="64"
              viewBox="0 0 24 24"
              fill="none"
              stroke="currentColor"
              stroke-width="0.8"
            >
              <path d="M20.24 12.24a6 6 0 0 0-8.49-8.49L5 10.5V19h8.5z" />
              <line x1="16" y1="8" x2="2" y2="22" />
              <line x1="17.5" y1="15" x2="9" y2="15" />
            </svg>
          </div>
          <h3 class="empty-title">Be the First to Write</h3>
          <p class="empty-text">
            The pages are blank, waiting for the first words of love.<br />
            Your blessing will set the tone for a lifetime of beautiful wishes.
          </p>
        </div>

        <!-- Messages -->
        <template v-else>
          <div class="masonry-wall">
            <div
              v-for="(msg, idx) in visibleMessages"
              :key="idx"
              class="message-card"
              :class="'card-accent-' + (idx % 5)"
              data-aos="fade-up"
              :data-aos-delay="(idx % 10) * 60"
            >
              <!-- Quote mark -->
              <div class="quote-mark">"</div>

              <!-- Message text -->
              <p class="msg-text">{{ msg.text }}</p>

              <!-- Author bar -->
              <div class="msg-footer">
                <div class="msg-author">
                  <div
                    class="author-avatar"
                    :style="{ background: getAvatarGradient(idx) }"
                  >
                    {{ msg.name.charAt(0) }}
                  </div>
                  <div class="author-info">
                    <span class="author-name">{{ msg.name }}</span>
                    <span v-if="msg.relationship" class="author-rel">{{
                      msg.relationship
                    }}</span>
                  </div>
                </div>
                <button
                  class="heart-btn"
                  :class="{ liked: msg.liked }"
                  @click="toggleLike(idx)"
                >
                  <svg
                    width="14"
                    height="14"
                    viewBox="0 0 24 24"
                    :fill="msg.liked ? 'currentColor' : 'none'"
                    stroke="currentColor"
                    stroke-width="1.5"
                  >
                    <path
                      d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"
                    />
                  </svg>
                  <span>{{ msg.likes }}</span>
                </button>
              </div>

              <!-- Photo if attached -->
              <div v-if="msg.photo" class="msg-photo">
                <img :src="msg.photo" alt="" />
              </div>
            </div>
          </div>

          <!-- Load More -->
          <div
            v-if="visibleCount < messages.length"
            class="load-more-section"
            data-aos="fade-up"
          >
            <div class="load-progress">
              <div class="progress-bar">
                <div
                  class="progress-fill"
                  :style="{ width: (visibleCount / messages.length) * 100 + '%' }"
                ></div>
              </div>
              <span class="progress-text"
                >Showing {{ visibleCount }} of {{ messages.length }} wishes</span
              >
            </div>
            <button class="load-more-btn" @click="loadMore">
              <svg
                width="16"
                height="16"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="1.5"
              >
                <path d="M12 5v14m-7-7 7 7 7-7" />
              </svg>
              <span>Read More Messages</span>
            </button>
          </div>

          <div v-else class="all-loaded" data-aos="fade-up">
            <span class="all-loaded-icon">✦</span>
            <p>You've read all {{ messages.length }} messages of love</p>
          </div>
        </template>
      </section>
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
        <h2 class="empty-title">Guestbook Opens on Wedding Day</h2>
        <p class="empty-text">
          Please come back on our wedding day to share your beautiful wishes<br />
          and capture the moment with us!
        </p>
      </div>
    </section>

    <!-- Footer -->
    <footer class="gb-footer">
      <div class="footer-ornament">❧</div>
      <p>Jonathan &amp; Norma · April 25, 2026</p>
    </footer>
  </div>
</template>

<script>
import { API_URL } from "@/config";
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "GuestbookView",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
  data() {
    return {
      form: { name: "", relationship: "", message: "" },
      messages: [],
      visibleCount: 9,
      loading: false
    };
  },
  computed: {
    visibleMessages() {
      return this.messages.slice(0, this.visibleCount);
    },
  },
  async mounted() {
    this.initParticles();
    await this.fetchMessages();
  },
  beforeUnmount() {
    cancelAnimationFrame(this.animFrame);
  },
  methods: {
    async fetchMessages() {
      this.loading = true;
      try {
        const response = await fetch(`${API_URL}/guestbook`);
        if (!response.ok) throw new Error("Failed to fetch messages");
        const data = await response.json();
        // Map backend fields to frontend if necessary
        this.messages = data.map(m => ({
          name: m.name,
          relationship: m.relationship || "",
          text: m.message,
          likes: 0, // Backend doesn't support likes yet
          liked: false
        }));
      } catch (error) {
        console.error("Error fetching guestbook:", error);
      } finally {
        this.loading = false;
      }
    },
    loadMore() {
      this.visibleCount = Math.min(this.visibleCount + 9, this.messages.length);
    },
    async submitMessage() {
      if (!this.form.name || !this.form.message) return;

      const entryData = {
        name: this.form.name,
        relationship: this.form.relationship,
        message: this.form.message
      };

      try {
        const response = await fetch(`${API_URL}/guestbook`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(entryData),
        });

        if (!response.ok) throw new Error("Failed to submit message");

        const newEntry = await response.json();

        this.messages.unshift({
          name: newEntry.name,
          relationship: newEntry.relationship,
          text: newEntry.message,
          likes: 0,
          liked: false,
        });

        this.visibleCount = Math.min(this.visibleCount + 1, this.messages.length);
        this.form = { name: "", relationship: "", message: "" };
      } catch (error) {
        console.error("Error submitting message:", error);
        alert("Failed to send your message. Please try again.");
      }
    },
    toggleLike(idx) {
      const m = this.messages[idx];
      m.liked = !m.liked;
      m.likes += m.liked ? 1 : -1;
    },
    getAvatarGradient(idx) {
      const gradients = [
        "linear-gradient(135deg, #C5A059, #8B7340)",
        "linear-gradient(135deg, #7B9E89, #4A7B5C)",
        "linear-gradient(135deg, #A08069, #7A5F4D)",
        "linear-gradient(135deg, #8899AA, #5C6B7A)",
        "linear-gradient(135deg, #B5838D, #835B64)",
      ];
      return gradients[idx % gradients.length];
    },
    initParticles() {
      const canvas = this.$refs.gbCanvas;
      if (!canvas) return;
      const ctx = canvas.getContext("2d");
      const resize = () => {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
      };
      resize();
      const particles = [];
      for (let i = 0; i < 20; i++) {
        particles.push({
          x: Math.random() * canvas.width,
          y: Math.random() * canvas.height,
          r: Math.random() * 1 + 0.3,
          dx: (Math.random() - 0.5) * 0.06,
          dy: -Math.random() * 0.05 - 0.01,
          o: Math.random() * 0.15 + 0.04,
          phase: Math.random() * Math.PI * 2,
        });
      }
      const animate = () => {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        const t = Date.now() * 0.001;
        particles.forEach((p) => {
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
.guestbook-page {
  min-height: 100vh;
  background: #0f1f18;
  color: #f0f0ee;
  position: relative;
  overflow-x: hidden;
}
.gb-particles {
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 0;
}

/* ─── Nav ─── */
.gb-nav {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 100;
  padding: 1.25rem 2rem;
  background: linear-gradient(to bottom, rgba(15, 31, 24, 0.95), transparent);
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
.gb-hero {
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
.gb-title {
  margin: 0;
}
.title-script {
  font-family: var(--font-script);
  font-size: clamp(2.8rem, 10vw, 4.5rem);
  color: #f0f0ee;
  text-shadow: 0 0 60px rgba(197, 160, 89, 0.1);
}
.gb-subtitle {
  font-family: var(--font-serif);
  font-size: 0.85rem;
  color: rgba(240, 240, 238, 0.4);
  letter-spacing: 0.06em;
  line-height: 1.7;
  max-width: 420px;
  margin: 0 auto;
}
.gb-count {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.3em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.35);
  margin-top: 0.75rem;
}

/* ─── Compose ─── */
.compose-section {
  max-width: 640px;
  margin: 0 auto;
  padding: 0 1.5rem 3rem;
  position: relative;
  z-index: 1;
}
.compose-card {
  background: rgba(26, 47, 37, 0.5);
  border: 1px solid rgba(197, 160, 89, 0.1);
  padding: 2rem;
  backdrop-filter: blur(6px);
}
.compose-header {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.compose-icon {
  width: 48px;
  height: 48px;
  flex-shrink: 0;
  border: 1px solid rgba(197, 160, 89, 0.2);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c5a059;
  background: rgba(197, 160, 89, 0.04);
}
.compose-title {
  font-family: var(--font-serif);
  font-size: 0.9rem;
  color: #f0f0ee;
  letter-spacing: 0.08em;
  margin: 0;
}
.compose-desc {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  color: rgba(240, 240, 238, 0.3);
  margin: 0.15rem 0 0;
}

.compose-fields {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1.5rem;
}
.fields-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}
@media (max-width: 500px) {
  .fields-row {
    grid-template-columns: 1fr;
  }
}
.field-group {
  display: flex;
  flex-direction: column;
  gap: 0.3rem;
}
.field-group label {
  font-family: var(--font-serif);
  font-size: 0.6rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.4);
}
.field-group input,
.field-group select,
.field-group textarea {
  background: rgba(15, 31, 24, 0.5);
  border: 1px solid rgba(197, 160, 89, 0.1);
  color: #f0f0ee;
  font-family: var(--font-serif);
  font-size: 0.8rem;
  padding: 0.7rem 0.9rem;
  outline: none;
  transition:
    border-color 0.3s,
    box-shadow 0.3s;
  appearance: none;
  -webkit-appearance: none;
}
.field-group input:focus,
.field-group select:focus,
.field-group textarea:focus {
  border-color: rgba(197, 160, 89, 0.3);
  box-shadow: 0 0 12px rgba(197, 160, 89, 0.05);
}
.field-group input::placeholder,
.field-group textarea::placeholder {
  color: rgba(240, 240, 238, 0.15);
}
.field-group select {
  color: rgba(240, 240, 238, 0.6);
  cursor: pointer;
}
.field-group select option {
  background: #1a2f25;
  color: #f0f0ee;
}
.field-group textarea {
  resize: vertical;
  min-height: 100px;
  line-height: 1.7;
}
.char-count {
  font-size: 0.55rem;
  color: rgba(197, 160, 89, 0.25);
  text-align: right;
  margin-top: 0.15rem;
}

.compose-submit {
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
.compose-submit:hover:not(:disabled) {
  box-shadow: 0 8px 25px rgba(197, 160, 89, 0.3);
  transform: translateY(-1px);
}
.compose-submit:disabled {
  opacity: 0.35;
  cursor: not-allowed;
}
.compose-submit::before {
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
.compose-submit:hover::before {
  left: 100%;
}

/* ─── Messages Wall ─── */
.messages-section {
  position: relative;
  z-index: 1;
  padding: 0 1.5rem 3rem;
  max-width: 1400px;
  margin: 0 auto;
}
.wall-heading {
  text-align: center;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  font-weight: 400;
  letter-spacing: 0.35em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.3);
  margin-bottom: 2.5rem;
}

.masonry-wall {
  columns: 3;
  column-gap: 1rem;
}

@media (max-width: 900px) {
  .masonry-wall {
    columns: 2;
  }
}
@media (max-width: 550px) {
  .masonry-wall {
    columns: 1;
    max-width: 420px;
    margin: 0 auto;
  }
}

.message-card {
  break-inside: avoid;
  margin-bottom: 1rem;
  background: rgba(26, 47, 37, 0.4);
  border: 1px solid rgba(197, 160, 89, 0.06);
  padding: 1.5rem;
  position: relative;
  transition:
    transform 0.3s,
    border-color 0.3s,
    box-shadow 0.3s;
  overflow: hidden;
}

.message-card::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 3px;
  height: 100%;
  transition: opacity 0.3s;
}

.message-card:hover {
  transform: translateY(-2px);
  border-color: rgba(197, 160, 89, 0.12);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

/* Accent variants */
.card-accent-0::before {
  background: linear-gradient(to bottom, #c5a059, transparent);
}
.card-accent-1::before {
  background: linear-gradient(to bottom, #7b9e89, transparent);
}
.card-accent-2::before {
  background: linear-gradient(to bottom, #a08069, transparent);
}
.card-accent-3::before {
  background: linear-gradient(to bottom, #8899aa, transparent);
}
.card-accent-4::before {
  background: linear-gradient(to bottom, #b5838d, transparent);
}

.quote-mark {
  font-family: var(--font-script);
  font-size: 3rem;
  line-height: 1;
  color: rgba(197, 160, 89, 0.12);
  position: absolute;
  top: 0.5rem;
  left: 1rem;
  pointer-events: none;
}

.msg-text {
  font-family: var(--font-serif);
  font-size: 0.8rem;
  line-height: 1.85;
  color: rgba(240, 240, 238, 0.65);
  margin: 0;
  position: relative;
  z-index: 1;
}

.msg-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 1.25rem;
  padding-top: 0.85rem;
  border-top: 1px solid rgba(197, 160, 89, 0.06);
}

.msg-author {
  display: flex;
  align-items: center;
  gap: 0.6rem;
}
.author-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-serif);
  font-size: 0.65rem;
  color: rgba(255, 255, 255, 0.85);
  font-weight: 500;
  flex-shrink: 0;
}
.author-info {
  display: flex;
  flex-direction: column;
}
.author-name {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  color: rgba(240, 240, 238, 0.6);
  font-weight: 500;
}
.author-rel {
  font-size: 0.55rem;
  color: rgba(197, 160, 89, 0.35);
  letter-spacing: 0.05em;
}

.heart-btn {
  display: flex;
  align-items: center;
  gap: 0.3rem;
  background: none;
  border: none;
  cursor: pointer;
  color: rgba(240, 240, 238, 0.25);
  font-size: 0.65rem;
  font-family: var(--font-serif);
  transition: all 0.3s;
  padding: 0.3rem;
}
.heart-btn:hover {
  color: rgba(197, 160, 89, 0.6);
}
.heart-btn.liked {
  color: #c5a059;
}

.msg-photo {
  margin-top: 1rem;
  overflow: hidden;
}
.msg-photo img {
  width: 100%;
  height: auto;
  display: block;
  filter: brightness(0.92);
  transition: filter 0.3s;
}
.msg-photo:hover img {
  filter: brightness(1);
}

/* ─── Load More ─── */
.load-more-section {
  text-align: center;
  padding: 2.5rem 1rem 3rem;
  position: relative;
  z-index: 1;
}
.load-progress {
  max-width: 280px;
  margin: 0 auto 1.25rem;
}
.progress-bar {
  width: 100%;
  height: 2px;
  background: rgba(197, 160, 89, 0.08);
  overflow: hidden;
  margin-bottom: 0.5rem;
}
.progress-fill {
  height: 100%;
  background: linear-gradient(90deg, #c5a059, #d4b978);
  transition: width 0.6s ease;
}
.progress-text {
  font-family: var(--font-serif);
  font-size: 0.6rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(197, 160, 89, 0.3);
}
.load-more-btn {
  display: inline-flex;
  align-items: center;
  gap: 0.6rem;
  padding: 0.85rem 2.5rem;
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
.load-more-btn:hover {
  border-color: rgba(197, 160, 89, 0.5);
  background: rgba(197, 160, 89, 0.05);
  transform: translateY(-1px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
}

.all-loaded {
  text-align: center;
  padding: 2.5rem 1rem 3rem;
  position: relative;
  z-index: 1;
}
.all-loaded-icon {
  display: block;
  font-size: 1.2rem;
  color: rgba(197, 160, 89, 0.25);
  margin-bottom: 0.5rem;
}
.all-loaded p {
  font-family: var(--font-serif);
  font-size: 0.65rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.2);
}

/* ─── Empty State ─── */
.empty-state {
  text-align: center;
  padding: 4rem 2rem 5rem;
  max-width: 480px;
  margin: 0 auto;
  position: relative;
  z-index: 1;
}
.empty-icon {
  color: rgba(197, 160, 89, 0.2);
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
  font-size: clamp(1.8rem, 5vw, 2.5rem);
  color: #f0f0ee;
  margin: 0 0 1rem;
  text-shadow: 0 0 40px rgba(197, 160, 89, 0.1);
}
.empty-text {
  font-family: var(--font-serif);
  font-size: 0.85rem;
  line-height: 1.9;
  color: rgba(240, 240, 238, 0.4);
  font-style: italic;
  margin: 0;
}

/* ─── Footer ─── */
.gb-footer {
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
.gb-footer p {
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.2em;
  color: rgba(240, 240, 238, 0.2);
}
</style>
