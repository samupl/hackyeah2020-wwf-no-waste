import CategoriesCRUD from "@/views/CategoriesCRUD.vue";
import CouponsCRUD from '@/views/CouponsCRUD.vue';
import Dashboard from "@/views/Dashboard.vue";
import ErrorNotFound from "@/views/ErrorNotFound.vue";
import Login from "@/views/Login.vue";
import ProductsCRUD from "@/views/ProductsCRUD.vue";
import TagsCRUD from "@/views/TagsCRUD.vue";
import Vue from "vue";
import VueRouter, { Route, RouteConfig } from "vue-router";
import store from "@/store";

Vue.use(VueRouter);

function loginRequiredGuard(to: Route, from: Route, next: Function) {
  if (store.state.authToken == "") {
    next("/login");
  }
  return next();
}

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Dashboard,
    beforeEnter: loginRequiredGuard
  },
  {
    path: "/tags",
    name: "Tags",
    component: TagsCRUD,
    beforeEnter: loginRequiredGuard
  },
  {
    path: "/categories",
    name: "Categories",
    component: CategoriesCRUD
  },
  {
    path: "/products",
    name: "Products",
    component: ProductsCRUD,
    beforeEnter: loginRequiredGuard
  },
  {
    path: "/products/:id",
    name: "Products-details",
    component: ProductsCRUD,
    beforeEnter: loginRequiredGuard
  },
  {
    path: "/coupons",
    name: "Coupons",
    component: CouponsCRUD,
    beforeEnter: loginRequiredGuard
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "*",
    name: "NotFound",
    component: ErrorNotFound
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
