<template>
  <canvas ref="canvas" class="particles-canvas"></canvas>
</template>

<script>
export default {
  name: "FloatingParticles",
  data() {
    return {
      particles: [],
      animationId: null,
      mouseX: 0,
      mouseY: 0,
      targetMouseX: 0,
      targetMouseY: 0,
    };
  },
  mounted() {
    this.targetMouseX = window.innerWidth / 2;
    this.targetMouseY = window.innerHeight / 2;
    this.mouseX = this.targetMouseX;
    this.mouseY = this.targetMouseY;

    this.initCanvas();
    window.addEventListener("resize", this.handleResize);
    window.addEventListener("mousemove", this.handleMouseMove);
  },
  beforeUnmount() {
    cancelAnimationFrame(this.animationId);
    window.removeEventListener("resize", this.handleResize);
    window.removeEventListener("mousemove", this.handleMouseMove);
  },
  methods: {
    initCanvas() {
      const canvas = this.$refs.canvas;
      const ctx = canvas.getContext("2d");
      canvas.width = window.innerWidth;
      canvas.height = window.innerHeight;

      this.particles = [];
      const count = Math.min(80, Math.floor(canvas.width / 15));
      for (let i = 0; i < count; i++) {
        const isBokeh = Math.random() > 0.85; // 15% are larger, out of focus orbs
        this.particles.push({
          x: Math.random() * canvas.width,
          y: Math.random() * canvas.height,
          size: isBokeh ? Math.random() * 8 + 4 : Math.random() * 2 + 0.5,
          speedX: (Math.random() - 0.5) * (isBokeh ? 0.1 : 0.3),
          speedY: (Math.random() - 0.5) * (isBokeh ? 0.05 : 0.2) - 0.1,
          opacity: isBokeh
            ? Math.random() * 0.15 + 0.05
            : Math.random() * 0.5 + 0.1,
          flickerSpeed: Math.random() * 0.02 + 0.005,
          flickerOffset: Math.random() * Math.PI * 2,
          zFactor: isBokeh ? 0.05 : 0.15, // Defines how much it reacts to mouse
          isBokeh,
        });
      }

      this.animate(canvas, ctx);
    },
    animate(canvas, ctx) {
      ctx.clearRect(0, 0, canvas.width, canvas.height);
      const time = Date.now() * 0.001;

      // Smoothly interpolate mouse position for fluid effect
      this.mouseX += (this.targetMouseX - this.mouseX) * 0.05;
      this.mouseY += (this.targetMouseY - this.mouseY) * 0.05;

      const parallaxOffsetX = this.mouseX - canvas.width / 2;
      const parallaxOffsetY = this.mouseY - canvas.height / 2;

      this.particles.forEach((p) => {
        p.x += p.speedX;
        p.y += p.speedY;

        // Apply mouse parallax
        const displayX = p.x - parallaxOffsetX * p.zFactor;
        const displayY = p.y - parallaxOffsetY * p.zFactor;

        if (p.x < -100) p.x = canvas.width + 100;
        if (p.x > canvas.width + 100) p.x = -100;
        if (p.y < -100) p.y = canvas.height + 100;
        if (p.y > canvas.height + 100) p.y = -100;

        const flicker =
          Math.sin(time * p.flickerSpeed * 60 + p.flickerOffset) * 0.3 + 0.7;
        const alpha = p.opacity * (p.isBokeh ? 1 : flicker);

        ctx.beginPath();
        ctx.arc(displayX, displayY, p.size, 0, Math.PI * 2);

        if (p.isBokeh) {
          const gradient = ctx.createRadialGradient(
            displayX,
            displayY,
            0,
            displayX,
            displayY,
            p.size,
          );
          gradient.addColorStop(0, `rgba(212, 185, 120, ${alpha})`);
          gradient.addColorStop(1, `rgba(212, 185, 120, 0)`);
          ctx.fillStyle = gradient;
        } else {
          ctx.fillStyle = `rgba(197, 160, 89, ${alpha})`;
        }

        ctx.fill();

        // Subtle glow for tiny particles
        if (!p.isBokeh) {
          ctx.beginPath();
          ctx.arc(displayX, displayY, p.size * 3, 0, Math.PI * 2);
          ctx.fillStyle = `rgba(197, 160, 89, ${alpha * 0.15})`;
          ctx.fill();
        }
      });

      this.animationId = requestAnimationFrame(() => this.animate(canvas, ctx));
    },
    handleResize() {
      cancelAnimationFrame(this.animationId);
      this.initCanvas();
      this.targetMouseX = window.innerWidth / 2;
      this.targetMouseY = window.innerHeight / 2;
    },
    handleMouseMove(e) {
      this.targetMouseX = e.clientX;
      this.targetMouseY = e.clientY;
    },
  },
};
</script>

<style scoped>
.particles-canvas {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1;
  pointer-events: none;
}
</style>
