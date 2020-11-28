import CategoriesCRUD from "@/views/CategoriesCRUD.vue";
import TagsCRUD from "@/views/TagsCRUD.vue";
import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/tags",
    name: "Tags",
    component: TagsCRUD
  },
  {
    path: "/categories",
    name: "Categories",
    component: CategoriesCRUD
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
