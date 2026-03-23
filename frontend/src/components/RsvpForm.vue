<template>
  <div class="rsvp section" id="rsvp">
    <h2 class="section-title" data-aos="fade-up">Répondez S'il Vous Plaît</h2>
    <div class="gold-divider" data-aos="fade-up" data-aos-delay="100">✦</div>
    <p class="section-subtitle" data-aos="fade-up" data-aos-delay="150">
      Kindly respond by April 20, 2026
    </p>

    <!-- Success State -->
    <div v-if="submitted" class="rsvp-success" data-aos="zoom-in">
      <div class="success-icon">✦</div>
      <h3>Thank You</h3>
      <p>
        Your response has been received. We look forward to celebrating with
        you.
      </p>
    </div>

    <!-- Form -->
    <form
      v-else
      class="rsvp-form"
      @submit.prevent="handleSubmit"
      data-aos="fade-up"
      data-aos-delay="200"
    >
      <div class="form-group">
        <label class="form-label" for="guestName">Your Name</label>
        <input
          id="guestName"
          v-model="form.guestName"
          type="text"
          class="form-input"
          placeholder="Enter your full name"
          required
        />
      </div>

      <div class="form-group">
        <label class="form-label">Will you be attending?</label>
        <div class="toggle-group">
          <button
            type="button"
            class="toggle-btn"
            :class="{ active: form.attending === true }"
            @click="form.attending = true"
          >
            Joyfully Accept
          </button>
          <button
            type="button"
            class="toggle-btn"
            :class="{ active: form.attending === false }"
            @click="form.attending = false"
          >
            Regretfully Decline
          </button>
        </div>
      </div>

      <template v-if="form.attending">
        <div class="form-group">
          <label class="form-label" for="guestCount">Number of Guests</label>
          <select id="guestCount" v-model="form.guestCount" class="form-input">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
          </select>
        </div>


        <div class="form-group">
          <label class="form-label" for="songReq"
            >Song Request <span class="optional">(optional)</span></label
          >
          <input
            id="songReq"
            v-model="form.songRequest"
            type="text"
            class="form-input"
            placeholder="What song gets you on the dance floor?"
          />
        </div>
      </template>

      <div class="form-group">
        <label class="form-label" for="message"
          >A Note to the Couple <span class="optional">(optional)</span></label
        >
        <textarea
          id="message"
          v-model="form.message"
          class="form-input form-textarea"
          placeholder="Share your well wishes..."
          rows="3"
        ></textarea>
      </div>

      <button type="submit" class="btn-gold submit-btn" :disabled="submitting">
        <span v-if="submitting">Sending...</span>
        <span v-else>Send Response</span>
      </button>
    </form>
  </div>
</template>

<script>
import { API_URL } from "@/config";

export default {
  name: "RsvpForm",
  data() {
    return {
      submitted: false,
      submitting: false,
      form: {
        guestName: "",
        attending: null,
        guestCount: "1",
        songRequest: "",
        message: "",
      },
    };
  },
  methods: {
    async handleSubmit() {
      if (this.form.attending === null) return;
      this.submitting = true;

      const rsvpData = {
        name: this.form.guestName,
        attending: this.form.attending,
        guests: parseInt(this.form.guestCount),
        songRequest: this.form.songRequest,
        message: this.form.message,
        email: "" // Optional but in model
      };

      try {
        const response = await fetch(`${API_URL}/rsvps`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(rsvpData),
        });

        if (!response.ok) throw new Error("Failed to submit RSVP");

        this.submitted = true;
      } catch (error) {
        console.error("Error submitting RSVP:", error);
        alert("There was an error submitting your RSVP. Please try again later.");
      } finally {
        this.submitting = false;
      }
    },
  },
};
</script>

<style scoped>
.rsvp {
  text-align: center;
  max-width: 560px;
  margin: 0 auto;
}

.section-title {
  font-family: var(--font-script);
  font-size: clamp(1.75rem, 5vw, 3rem);
  color: #c5a059;
  letter-spacing: 0.02em;
}

.section-subtitle {
  font-size: 0.8rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
  margin-top: 0.5rem;
}

.rsvp-form {
  margin-top: 2.5rem;
  text-align: left;
}

.form-group {
  margin-bottom: 1.5rem;
}

.form-label {
  display: block;
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.6);
  margin-bottom: 0.5rem;
}

.optional {
  text-transform: none;
  letter-spacing: normal;
  opacity: 0.5;
  font-size: 0.7rem;
}

.form-input {
  width: 100%;
  padding: 0.875rem 1rem;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(197, 160, 89, 0.3);
  color: #f0f0ee;
  font-family: var(--font-serif);
  font-size: 0.95rem;
  outline: none;
  border-radius: 4px;
  transition: all 0.4s ease;
  appearance: none;
  box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-input:focus {
  border-color: rgba(212, 185, 120, 0.8);
  background: rgba(0, 0, 0, 0.3);
  box-shadow:
    inset 0 2px 4px rgba(0, 0, 0, 0.2),
    0 0 15px rgba(212, 185, 120, 0.15);
}

.form-input::placeholder {
  color: rgba(240, 240, 238, 0.25);
}

.form-textarea {
  resize: vertical;
  min-height: 80px;
}

select.form-input {
  cursor: pointer;
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='12' height='6' viewBox='0 0 12 6'%3E%3Cpath fill='%23C5A059' d='M0 0l6 6 6-6z'/%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 1rem center;
  padding-right: 2.5rem;
}

select.form-input option {
  background: #1a2f25;
  color: #f0f0ee;
}

.toggle-group {
  display: flex;
  gap: 0.75rem;
}

.toggle-btn {
  flex: 1;
  padding: 0.875rem 1rem;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(197, 160, 89, 0.3);
  color: rgba(240, 240, 238, 0.6);
  font-family: var(--font-serif);
  font-size: 0.85rem;
  letter-spacing: 0.05em;
  cursor: pointer;
  border-radius: 4px;
  transition: all 0.4s ease;
}

.toggle-btn.active {
  border-color: rgba(212, 185, 120, 0.9);
  color: #122119;
  background: linear-gradient(
    135deg,
    rgba(212, 185, 120, 0.9),
    rgba(229, 204, 142, 0.9)
  );
  font-weight: 600;
  box-shadow: 0 4px 15px rgba(212, 185, 120, 0.2);
  transform: translateY(-2px);
}

.toggle-btn:hover:not(.active) {
  border-color: rgba(212, 185, 120, 0.6);
  background: rgba(212, 185, 120, 0.05);
}

.submit-btn {
  width: 100%;
  margin-top: 0.5rem;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Success */
.rsvp-success {
  margin-top: 3rem;
  padding: 3rem 2rem;
  border: 1px solid rgba(197, 160, 89, 0.2);
}

.success-icon {
  font-size: 2rem;
  color: #c5a059;
  margin-bottom: 1rem;
}

.rsvp-success h3 {
  font-family: var(--font-script);
  font-size: 2rem;
  color: #c5a059;
  margin-bottom: 1rem;
}

.rsvp-success p {
  font-size: 0.9rem;
  color: rgba(240, 240, 238, 0.6);
  line-height: 1.6;
}
</style>
