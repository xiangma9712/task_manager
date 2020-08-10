import Vue from "vue";
import VueRouter, { Route, RouteConfig } from "vue-router";
import Home from "../views/Home.vue";
import store from "../store";

Vue.use(VueRouter);

const requireAuth = (to: Route, from: Route, next: Function) => {
  if (store.getters["cognito/isLoggedIn"]) {
    next();
  } else {
    next({
      path: "/login",
      query: { redirect: to.fullPath }
    });
  }
};

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Home,
    beforeEnter: requireAuth
  },
  {
    path: "/log",
    name: "Log History",
    component: () => import("../views/LogHistory.vue"),
    beforeEnter: requireAuth
  },
  {
    path: "/task/:id",
    name: "Task Detail",
    component: () => import("../views/TaskDetail.vue"),
    beforeEnter: requireAuth
  },
  {
    path: "/login",
    name: "Login",
    component: () => import("../views/Login.vue")
  },
  {
    path: "/signup",
    name: "Sign Up",
    component: () => import("../views/Signup.vue")
  },
  {
    path: "*",
    redirect: "/home"
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
