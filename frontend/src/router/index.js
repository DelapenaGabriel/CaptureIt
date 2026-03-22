import { createRouter, createWebHistory } from "vue-router";
import LandingView from "@/views/LandingView.vue";

const routes = [
  {
    path: "/",
    name: "landing",
    component: LandingView,
  },
  {
    path: "/admin/login",
    name: "admin-login",
    component: () => import("@/views/AdminLogin.vue"),
  },
  {
    path: "/admin",
    name: "admin",
    component: () => import("@/views/AdminDashboard.vue"),
    meta: { requiresAuth: true },
  },
  {
    path: "/guestbook",
    name: "guestbook",
    component: () => import("@/views/GuestbookView.vue"),
  },
  {
    path: "/gallery",
    name: "gallery",
    component: () => import("@/views/GalleryView.vue"),
  },
  {
    path: "/:slug",
    name: "custom-wedding",
    component: LandingView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (to.hash) {
      return { el: to.hash, behavior: "smooth" };
    }
    return savedPosition || { top: 0 };
  },
});

export default router;
