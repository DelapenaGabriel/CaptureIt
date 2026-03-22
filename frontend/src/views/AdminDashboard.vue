<template>
  <div class="admin-dashboard">
    <!-- Top Bar -->
    <header class="admin-header">
      <h1 class="admin-title">Dashboard</h1>
      <div class="admin-actions">
        <button class="btn-gold btn-sm" @click="exportCsv">Export CSV</button>
        <button class="logout-btn" @click="logout">Logout</button>
      </div>
    </header>

    <!-- Analytics Cards -->
    <div class="analytics-grid">
      <div class="stat-card" v-for="stat in stats" :key="stat.label">
        <span class="stat-value">{{ stat.value }}</span>
        <span class="stat-label">{{ stat.label }}</span>
      </div>
    </div>

    <!-- Tabs -->
    <div class="tab-bar">
      <button
        v-for="tab in tabs"
        :key="tab.id"
        class="tab-btn"
        :class="{ active: activeTab === tab.id }"
        @click="activeTab = tab.id"
      >
        {{ tab.label }}
      </button>
    </div>

    <!-- RSVP Tab -->
    <div v-if="activeTab === 'rsvp'" class="tab-content">
      <div class="filter-bar">
        <select v-model="rsvpFilter" class="filter-select">
          <option value="all">All Responses</option>
          <option value="attending">Attending</option>
          <option value="declined">Declined</option>
        </select>
        <select v-model="mealFilter" class="filter-select">
          <option value="all">All Meals</option>
          <option value="filet">Filet Mignon</option>
          <option value="salmon">Salmon</option>
          <option value="chicken">Chicken</option>
          <option value="vegetarian">Vegetarian</option>
          <option value="vegan">Vegan</option>
        </select>
      </div>

      <div class="table-container">
        <table class="data-table">
          <thead>
            <tr>
              <th>Guest Name</th>
              <th>Status</th>
              <th>Guests</th>
              <th>Meal</th>
              <th>Song Request</th>
              <th>Note</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="rsvp in filteredRsvps" :key="rsvp.id">
              <td>{{ rsvp.name }}</td>
              <td>
                <span
                  class="badge"
                  :class="rsvp.attending ? 'badge-accept' : 'badge-decline'"
                >
                  {{ rsvp.attending ? "Attending" : "Declined" }}
                </span>
              </td>
              <td>{{ rsvp.guestCount }}</td>
              <td>{{ rsvp.meal || "—" }}</td>
              <td>{{ rsvp.song || "—" }}</td>
              <td class="note-cell">{{ rsvp.note || "—" }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Photos Tab -->
    <div v-if="activeTab === 'photos'" class="tab-content">
      <div class="photo-mod-grid">
        <div v-for="photo in photoQueue" :key="photo.id" class="photo-mod-card">
          <img :src="photo.src" :alt="photo.uploader" />
          <div class="photo-mod-info">
            <p>{{ photo.uploader }}</p>
            <div class="photo-mod-actions">
              <button class="action-btn approve" @click="approvePhoto(photo)">
                ✓ Approve
              </button>
              <button class="action-btn reject" @click="rejectPhoto(photo)">
                ✕ Delete
              </button>
            </div>
          </div>
        </div>
        <p v-if="photoQueue.length === 0" class="empty-state">
          No photos pending moderation
        </p>
      </div>
    </div>

    <!-- Settings Tab -->
    <div v-if="activeTab === 'settings'" class="tab-content">
      <div class="settings-grid">
        <div class="setting-group">
          <label class="form-label">Groom's Name</label>
          <input v-model="settings.groomName" class="form-input" />
        </div>
        <div class="setting-group">
          <label class="form-label">Bride's Name</label>
          <input v-model="settings.brideName" class="form-input" />
        </div>
        <div class="setting-group">
          <label class="form-label">Wedding Date</label>
          <input v-model="settings.date" type="date" class="form-input" />
        </div>
        <div class="setting-group">
          <label class="form-label">Primary Color</label>
          <input
            v-model="settings.primaryColor"
            type="color"
            class="form-input color-input"
          />
        </div>
        <div class="setting-group full-width">
          <button class="btn-gold" @click="saveSettings">Save Changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AdminDashboard",
  data() {
    return {
      activeTab: "rsvp",
      rsvpFilter: "all",
      mealFilter: "all",
      tabs: [
        { id: "rsvp", label: "RSVPs" },
        { id: "photos", label: "Photos" },
        { id: "settings", label: "Settings" },
      ],
      stats: [
        { label: "Total RSVPs", value: 47 },
        { label: "Attending", value: 38 },
        { label: "Declined", value: 9 },
        { label: "Photos", value: 23 },
      ],
      rsvps: [
        {
          id: 1,
          name: "Elizabeth Bennett",
          attending: true,
          guestCount: 2,
          meal: "filet",
          song: "At Last - Etta James",
          note: "So happy for you!",
        },
        {
          id: 2,
          name: "Charles Darcy",
          attending: true,
          guestCount: 1,
          meal: "salmon",
          song: "",
          note: "",
        },
        {
          id: 3,
          name: "Catherine Howard",
          attending: false,
          guestCount: 0,
          meal: "",
          song: "",
          note: "Wishing you the best",
        },
        {
          id: 4,
          name: "James & Mary",
          attending: true,
          guestCount: 3,
          meal: "vegetarian",
          song: "Can't Help Falling in Love",
          note: "We are thrilled!",
        },
        {
          id: 5,
          name: "William Spencer",
          attending: true,
          guestCount: 2,
          meal: "chicken",
          song: "",
          note: "",
        },
      ],
      photoQueue: [
        {
          id: 1,
          src: "https://images.unsplash.com/photo-1519741497674-611481863552?w=300&h=200&fit=crop",
          uploader: "Elizabeth",
        },
        {
          id: 2,
          src: "https://images.unsplash.com/photo-1465495976277-4387d4b0b4c6?w=300&h=200&fit=crop",
          uploader: "James",
        },
      ],
      settings: {
        groomName: "Jonathan",
        brideName: "Norma",
        date: "2026-04-25",
        primaryColor: "#C5A059",
      },
    };
  },
  computed: {
    filteredRsvps() {
      return this.rsvps.filter((r) => {
        if (this.rsvpFilter === "attending" && !r.attending) return false;
        if (this.rsvpFilter === "declined" && r.attending) return false;
        if (this.mealFilter !== "all" && r.meal !== this.mealFilter)
          return false;
        return true;
      });
    },
  },
  methods: {
    exportCsv() {
      const headers = ["Name", "Attending", "Guests", "Meal", "Song", "Note"];
      const rows = this.rsvps.map((r) => [
        r.name,
        r.attending ? "Yes" : "No",
        r.guestCount,
        r.meal,
        r.song,
        r.note,
      ]);
      const csv = [headers, ...rows].map((r) => r.join(",")).join("\n");
      const blob = new Blob([csv], { type: "text/csv" });
      const url = URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "rsvp-list.csv";
      a.click();
      URL.revokeObjectURL(url);
    },
    approvePhoto(photo) {
      this.photoQueue = this.photoQueue.filter((p) => p.id !== photo.id);
    },
    rejectPhoto(photo) {
      this.photoQueue = this.photoQueue.filter((p) => p.id !== photo.id);
    },
    saveSettings() {
      alert("Settings saved (will be connected to backend)");
    },
    logout() {
      localStorage.removeItem("adminToken");
      this.$router.push("/admin/login");
    },
  },
  mounted() {
    const token = localStorage.getItem("adminToken");
    if (!token) this.$router.push("/admin/login");
  },
};
</script>

<style scoped>
.admin-dashboard {
  min-height: 100vh;
  background: #1a2f25;
  padding: 1.5rem;
  max-width: 1200px;
  margin: 0 auto;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.admin-title {
  font-family: var(--font-serif);
  font-size: 1.5rem;
  color: #f0f0ee;
  letter-spacing: 0.1em;
}

.admin-actions {
  display: flex;
  gap: 0.75rem;
}

.btn-sm {
  padding: 0.5rem 1.25rem;
  font-size: 0.75rem;
}

.logout-btn {
  padding: 0.5rem 1.25rem;
  background: transparent;
  border: 1px solid rgba(240, 240, 238, 0.2);
  color: rgba(240, 240, 238, 0.6);
  font-family: var(--font-serif);
  font-size: 0.75rem;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-btn:hover {
  border-color: rgba(240, 240, 238, 0.5);
  color: #f0f0ee;
}

/* Analytics */
.analytics-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
  margin-bottom: 2rem;
}

.stat-card {
  padding: 1.5rem;
  border: 1px solid rgba(197, 160, 89, 0.15);
  background: rgba(197, 160, 89, 0.02);
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.stat-value {
  font-family: var(--font-serif);
  font-size: 2rem;
  color: #c5a059;
}

.stat-label {
  font-size: 0.7rem;
  letter-spacing: 0.2em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
}

/* Tabs */
.tab-bar {
  display: flex;
  border-bottom: 1px solid rgba(197, 160, 89, 0.1);
  margin-bottom: 1.5rem;
}

.tab-btn {
  padding: 0.75rem 1.5rem;
  background: none;
  border: none;
  border-bottom: 2px solid transparent;
  color: rgba(240, 240, 238, 0.5);
  font-family: var(--font-serif);
  font-size: 0.8rem;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  cursor: pointer;
  transition: all 0.3s ease;
}

.tab-btn.active {
  color: #c5a059;
  border-bottom-color: #c5a059;
}

.tab-btn:hover:not(.active) {
  color: rgba(240, 240, 238, 0.7);
}

/* Filters */
.filter-bar {
  display: flex;
  gap: 0.75rem;
  margin-bottom: 1rem;
  flex-wrap: wrap;
}

.filter-select {
  padding: 0.5rem 1rem;
  background: rgba(197, 160, 89, 0.03);
  border: 1px solid rgba(197, 160, 89, 0.2);
  color: #f0f0ee;
  font-family: var(--font-serif);
  font-size: 0.8rem;
  outline: none;
  appearance: none;
  cursor: pointer;
}

.filter-select option {
  background: #1a2f25;
}

/* Table */
.table-container {
  overflow-x: auto;
}

.data-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.85rem;
}

.data-table th {
  text-align: left;
  padding: 0.75rem;
  font-size: 0.7rem;
  letter-spacing: 0.15em;
  text-transform: uppercase;
  color: rgba(240, 240, 238, 0.5);
  border-bottom: 1px solid rgba(197, 160, 89, 0.15);
  white-space: nowrap;
}

.data-table td {
  padding: 0.75rem;
  color: rgba(240, 240, 238, 0.7);
  border-bottom: 1px solid rgba(197, 160, 89, 0.05);
}

.badge {
  padding: 0.25rem 0.5rem;
  font-size: 0.7rem;
  letter-spacing: 0.1em;
  text-transform: uppercase;
}

.badge-accept {
  background: rgba(138, 154, 91, 0.2);
  color: #8a9a5b;
  border: 1px solid rgba(138, 154, 91, 0.3);
}

.badge-decline {
  background: rgba(229, 115, 115, 0.1);
  color: #e57373;
  border: 1px solid rgba(229, 115, 115, 0.2);
}

.note-cell {
  max-width: 200px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* Photo Moderation */
.photo-mod-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 1rem;
}

.photo-mod-card {
  border: 1px solid rgba(197, 160, 89, 0.15);
  overflow: hidden;
}

.photo-mod-card img {
  width: 100%;
  height: 160px;
  object-fit: cover;
}

.photo-mod-info {
  padding: 0.75rem;
}

.photo-mod-info p {
  font-size: 0.8rem;
  color: rgba(240, 240, 238, 0.6);
  margin-bottom: 0.5rem;
}

.photo-mod-actions {
  display: flex;
  gap: 0.5rem;
}

.action-btn {
  flex: 1;
  padding: 0.4rem;
  font-size: 0.7rem;
  font-family: var(--font-serif);
  letter-spacing: 0.05em;
  border: 1px solid;
  background: none;
  cursor: pointer;
  transition: all 0.3s ease;
}

.action-btn.approve {
  color: #8a9a5b;
  border-color: rgba(138, 154, 91, 0.3);
}
.action-btn.approve:hover {
  background: rgba(138, 154, 91, 0.15);
}

.action-btn.reject {
  color: #e57373;
  border-color: rgba(229, 115, 115, 0.2);
}
.action-btn.reject:hover {
  background: rgba(229, 115, 115, 0.1);
}

.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  font-size: 0.85rem;
  color: rgba(240, 240, 238, 0.4);
  padding: 3rem;
  border: 1px dashed rgba(197, 160, 89, 0.15);
}

/* Settings */
.settings-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1.25rem;
}

.setting-group {
  display: flex;
  flex-direction: column;
}

.setting-group.full-width {
  grid-column: 1 / -1;
  align-items: flex-start;
}

.form-label {
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

.color-input {
  padding: 0.25rem;
  height: 42px;
  cursor: pointer;
}

@media (min-width: 768px) {
  .admin-dashboard {
    padding: 2rem;
  }
  .analytics-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media (max-width: 640px) {
  .settings-grid {
    grid-template-columns: 1fr;
  }
}
</style>
