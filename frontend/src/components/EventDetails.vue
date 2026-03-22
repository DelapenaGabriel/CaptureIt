<template>
  <div class="event-details section">
    <h2 class="section-title" data-aos="fade-up">The Details</h2>
    <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>

    <div class="details-grid">
      <!-- Ceremony -->
      <div class="detail-card" data-aos="fade-up" data-aos-delay="200">
        <div class="card-icon">💒</div>
        <h3 class="card-title">The Ceremony</h3>
        <div class="card-divider"></div>
        <p class="card-time">Saturday, April 25, 2026 at 5:30 P.M.</p>
        <p class="card-time">Please arrive at least 20 minutes <br>prior at the latest</p>
        <p class="card-venue">128 Barclay Hts Avenue</p>
        <p class="card-address">Henderson, NV 89015</p>

        <div class="map-container">
          <iframe
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3226.831778828903!2d-114.98072399999998!3d36.0244011!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x80c8d6a0b7d64547%3A0x80f5a03efc10841b!2s128%20Barclay%20Hts%20Ave%2C%20Henderson%2C%20NV%2089015!5e0!3m2!1sen!2sus!4v1774130172451!5m2!1sen!2sus"
            width="100%"
            height="200"
            style="
              border: 0;
              filter: invert(90%) hue-rotate(180deg) saturate(0.3);
              opacity: 1;
            "
            allowfullscreen=""
            loading="lazy"
          ></iframe>
        </div>
      </div>

      <!-- Reception -->
      <div class="detail-card" data-aos="fade-up" data-aos-delay="300">
        <div class="card-icon">🥂</div>
        <h3 class="card-title">The Reception</h3>
        <div class="card-divider"></div>
        <p class="card-time">Cocktail Hour at 6:00 P.M.</p>
        <p class="card-time">Reception Immediately Following</p>
        <p class="card-venue">128 Barclay Hts Avenue</p>
        <p class="card-address">Henderson, NV 89015</p>
        <p class="card-detail" style="margin-top: 0.75rem;">Dinner, Dancing & Celebration</p>
      </div>

      <!-- Dress Code -->
      <div class="detail-card" data-aos="fade-up" data-aos-delay="400">
        <div class="card-icon">👔</div>
        <h3 class="card-title">Dress Code</h3>
        <div class="card-divider"></div>
        <p class="card-dress-code">Formal Attire • Earth Tones </p>
        <p class="card-detail">
          We invite our guests to dress in formal wear inspired by the natural beauty of the earth. Shades of brown, sage, olive,
           beige, tan, and soft greens are warmly encouraged.
        </p>
        <p class="card-detail">
          Think elegant, timeless, and grounded colors that reflect nature and complement the atmosphere of our celebration. Please note that it will be outdoors.
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "EventDetails",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
};
</script>

<style scoped>
.event-details {
  text-align: center;
  max-width: 1200px;
  margin: 0 auto;
}

.section-title {
  font-family: var(--font-serif);
  font-size: clamp(1.5rem, 4vw, 2.5rem);
  color: #f0f0ee;
  letter-spacing: 0.05em;
}

.details-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 2rem;
  margin-top: 3rem;
}

.detail-card {
  padding: 2.5rem 2rem;
  border: 1px solid rgba(197, 160, 89, 0.4);
  background: rgba(197, 160, 89, 0.05);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
  text-align: center;
  transition: all 0.4s var(--ease-elegant);
  border-radius: 4px;
}

.detail-card:hover {
  border-color: rgba(197, 160, 89, 0.8);
  background: rgba(212, 185, 120, 0.08);
  transform: translateY(-4px);
  box-shadow:
    0 12px 40px rgba(0, 0, 0, 0.4),
    0 0 20px rgba(197, 160, 89, 0.15);
}

.card-icon {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.card-title {
  font-family: var(--font-serif);
  font-size: 1.25rem;
  color: #c5a059;
  letter-spacing: 0.1em;
  text-transform: uppercase;
}

.card-divider {
  width: 40px;
  height: 1px;
  background: rgba(197, 160, 89, 0.4);
  margin: 1rem auto;
}

.card-time {
  font-family: var(--font-serif);
  font-size: 0.9rem;
  color: rgba(240, 240, 238, 0.7);
  line-height: 1.6;
}

.card-venue {
  font-family: var(--font-serif);
  font-size: 1rem;
  color: #f0f0ee;
  margin-top: 0.75rem;
  font-weight: 500;
}

.card-address {
  font-size: 0.8rem;
  color: rgba(240, 240, 238, 0.5);
  margin-top: 0.25rem;
}

.card-detail {
  font-size: 0.85rem;
  color: rgba(240, 240, 238, 0.6);
  line-height: 1.6;
  margin-top: 0.5rem;
  max-width: 300px;
  margin-left: auto;
  margin-right: auto;
}

.card-dress-code {
  font-family: var(--font-script);
  font-size: 1.75rem;
  color: #c5a059;
  margin-top: 0.5rem;
}

.map-container {
  margin-top: 1.5rem;
  border: 1px solid rgba(197, 160, 89, 0.1);
  overflow: hidden;
}

@media (min-width: 768px) {
  .details-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}
</style>
