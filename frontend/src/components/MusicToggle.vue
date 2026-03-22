<template>
  <button
    class="music-toggle"
    :class="{ playing: store.musicEnabled }"
    @click="store.toggleMusic()"
    :title="store.musicEnabled ? 'Pause music' : 'Play music'"
  >
    <svg
      v-if="!store.musicEnabled"
      width="18"
      height="18"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      stroke-width="1.5"
    >
      <path d="M9 18V5l12-2v13" />
      <circle cx="6" cy="18" r="3" />
      <circle cx="18" cy="16" r="3" />
      <line
        x1="9"
        y1="9"
        x2="9"
        y2="9.01"
        stroke-dasharray="2 2"
        opacity="0.5"
      />
    </svg>
    <svg
      v-else
      width="18"
      height="18"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      stroke-width="1.5"
    >
      <path d="M9 18V5l12-2v13" />
      <circle cx="6" cy="18" r="3" />
      <circle cx="18" cy="16" r="3" />
      <g class="sound-waves">
        <path d="M1 8 Q3 6, 1 4" opacity="0.4" />
        <path d="M23 8 Q25 6, 23 4" opacity="0.4" />
      </g>
    </svg>
  </button>
</template>

<script>
import { useWeddingStore } from "@/stores/wedding";

export default {
  name: "MusicToggle",
  setup() {
    const store = useWeddingStore();
    return { store };
  },
};
</script>

<style scoped>
.music-toggle {
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background: rgba(26, 47, 37, 0.9);
  border: 1px solid rgba(197, 160, 89, 0.3);
  color: #c5a059;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9990;
  transition: all 0.3s ease;
  backdrop-filter: blur(8px);
}

.music-toggle:hover {
  border-color: rgba(197, 160, 89, 0.6);
  box-shadow: 0 0 16px rgba(197, 160, 89, 0.2);
  transform: scale(1.05);
}

.music-toggle.playing {
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%,
  100% {
    box-shadow: 0 0 0 0 rgba(197, 160, 89, 0.1);
  }
  50% {
    box-shadow: 0 0 0 8px rgba(197, 160, 89, 0);
  }
}
</style>
