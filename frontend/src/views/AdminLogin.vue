<template>
  <div class="admin-login">
    <div class="login-card" data-aos="fade-up">
      <div class="login-monogram">
        <span class="script-text">C</span>
      </div>
      <h1 class="login-title">Admin Portal</h1>
      <div class="gold-divider">✦</div>

      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label class="form-label" for="username">Username</label>
          <input
            id="username"
            v-model="username"
            type="text"
            class="form-input"
            placeholder="Admin username"
            required
          />
        </div>
        <div class="form-group">
          <label class="form-label" for="password">Password</label>
          <input
            id="password"
            v-model="password"
            type="password"
            class="form-input"
            placeholder="Password"
            required
          />
        </div>

        <p v-if="error" class="error-text">{{ error }}</p>

        <button type="submit" class="btn-gold submit-btn" :disabled="loading">
          {{ loading ? "Authenticating..." : "Enter" }}
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { API_URL } from "@/config";

export default {
  name: "AdminLogin",
  data() {
    return {
      username: "",
      password: "",
      loading: false,
      error: "",
    };
  },
  methods: {
    async handleLogin() {
      this.error = "";
      this.loading = true;
      try {
        const response = await fetch(`${API_URL}/admin/login`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            username: this.username,
            password: this.password,
          }),
        });

        if (!response.ok) {
          const data = await response.json();
          throw new Error(data.message || "Invalid credentials");
        }

        const data = await response.json();
        localStorage.setItem("adminToken", data.token);
        this.$router.push("/admin");
      } catch (error) {
        console.error("Login error:", error);
        this.error = error.message;
      } finally {
        this.loading = false;
      }
    },
  },
};
</script>

<style scoped>
.admin-login {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem;
  background: #1a2f25;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 3rem 2.5rem;
  border: 1px solid rgba(197, 160, 89, 0.15);
  background: rgba(197, 160, 89, 0.02);
  text-align: center;
}

.login-monogram {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: rgba(197, 160, 89, 0.1);
  border: 1px solid rgba(197, 160, 89, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 1.5rem;
  font-size: 1.75rem;
  color: #c5a059;
}

.login-title {
  font-family: var(--font-serif);
  font-size: 1.25rem;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  color: #f0f0ee;
  margin-bottom: 0.5rem;
}

.login-form {
  margin-top: 1.5rem;
  text-align: left;
}

.form-group {
  margin-bottom: 1.25rem;
}

.form-label {
  display: block;
  font-family: var(--font-serif);
  font-size: 0.7rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
  margin-bottom: 0.4rem;
}

.form-input {
  width: 100%;
  padding: 0.75rem 1rem;
  background: rgba(197, 160, 89, 0.03);
  border: 1px solid rgba(197, 160, 89, 0.2);
  color: #f0f0ee;
  font-family: var(--font-serif);
  font-size: 0.9rem;
  outline: none;
  transition: border-color 0.3s ease;
}

.form-input:focus {
  border-color: rgba(197, 160, 89, 0.5);
}

.form-input::placeholder {
  color: rgba(240, 240, 238, 0.2);
}

.error-text {
  font-size: 0.8rem;
  color: #e57373;
  margin-bottom: 1rem;
  text-align: center;
}

.submit-btn {
  width: 100%;
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
</style>
