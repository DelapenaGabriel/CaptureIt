import { defineStore } from "pinia";
import { ref, computed } from "vue";

export const useWeddingStore = defineStore("wedding", () => {
  // Wedding config
  const brideFirstName = ref("Norma");
  const groomFirstName = ref("Jonathan");
  const weddingDate = ref("2026-04-25T16:00:00");
  const venueName = ref("128 Barclay Hts Avenue, Henderson, NV 89015");
  const venueAddress = ref("128 Barclay Hts Avenue, Henderson, NV 89015");
  const receptionVenue = ref("The Rosewood Ballroom");
  const dressCode = ref("Black Tie Optional");

  // Theme config
  const primaryColor = ref("#C5A059");
  const backgroundColor = ref("#1A2F25");
  const accentColor = ref("#8A9A5B");

  // Music
  const musicEnabled = ref(false);
  let audio = null;

  function initMusic() {
    if (!audio) {
      audio = new Audio("/music/Congratulations.mp3");
      audio.loop = true;
      audio.volume = 0.2;
    }

    // Wrap the play attempt in a dedicated function
    const tryPlay = () => {
      const playPromise = audio.play();
      if (playPromise !== undefined) {
        playPromise
          .then(() => {
            musicEnabled.value = true;
            // Clean up all global listeners once it successfully plays
            resumeEventTypes.forEach((type) => {
              document.removeEventListener(type, tryPlay);
            });
          })
          .catch(() => {
            // Autoplay blocked. That's okay, our listeners will catch the next interaction.
          });
      }
    };

    const resumeEventTypes = [
      "click",
      "touchstart",
      "scroll",
      "wheel",
      "mousemove",
      "keydown",
    ];

    // Try playing immediately (works after the EnvelopeGate click).
    tryPlay();

    // Still add listeners just in case the initial click event execution context was lost.
    resumeEventTypes.forEach((type) => {
      document.addEventListener(type, tryPlay, {
        once: true,
        passive: true,
      });
    });
  }

  function toggleMusic() {
    if (!audio) return;
    if (musicEnabled.value) {
      audio.pause();
    } else {
      audio.play().catch(() => {});
    }
    musicEnabled.value = !musicEnabled.value;
  }

  // RSVP state
  const rsvpSubmitted = ref(false);

  // Guestbook
  const guestbookEntries = ref([]);
  const photos = ref([]);

  // Timeline events
  const timelineEvents = ref([
    {
      time: "4:00 PM",
      title: "Ceremony",
      description: "Garden pavilion by the lake",
      icon: "💒",
    },
    {
      time: "5:00 PM",
      title: "Cocktail Hour",
      description: "Rose garden terrace with live quartet",
      icon: "🥂",
    },
    {
      time: "6:30 PM",
      title: "Reception",
      description: "Grand ballroom dinner & dancing",
      icon: "🎶",
    },
    {
      time: "10:00 PM",
      title: "Sparkler Send-Off",
      description: "A magical farewell under the stars",
      icon: "✨",
    },
  ]);

  // Computed
  const coupleNames = computed(
    () => `${groomFirstName.value} & ${brideFirstName.value}`,
  );
  const formattedDate = computed(() => {
    const d = new Date(weddingDate.value);
    return d.toLocaleDateString("en-US", {
      weekday: "long",
      year: "numeric",
      month: "long",
      day: "numeric",
    });
  });

  // Wedding Day Mode — auto-activates at midnight on the wedding day
  // Dev override: add ?weddingDay=true to URL to preview
  const weddingDayMode = computed(() => {
    const params = new URLSearchParams(window.location.search);
    if (params.get("weddingDay") === "true") return true;
    const wedding = new Date(weddingDate.value);
    wedding.setHours(0, 0, 0, 0);
    return new Date() >= wedding;
  });

  return {
    brideFirstName,
    groomFirstName,
    weddingDate,
    venueName,
    venueAddress,
    receptionVenue,
    dressCode,
    primaryColor,
    backgroundColor,
    accentColor,
    musicEnabled,
    initMusic,
    toggleMusic,
    rsvpSubmitted,
    guestbookEntries,
    photos,
    timelineEvents,
    coupleNames,
    formattedDate,
    weddingDayMode,
  };
});
