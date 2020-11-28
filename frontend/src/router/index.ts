import CategoriesCRUD from "@/views/CategoriesCRUD.vue";
import ProductsCRUD from "@/views/ProductsCRUD.vue";
import TagsCRUD from "@/views/TagsCRUD.vue";
import Vue from "vue";
import VueRouter, { RouteConfig } from "vue-router";

Vue.use(VueRouter);

const routes: Array<RouteConfig> = [
  {
    path: "/",
    name: "Home",
    redirect: "/products"
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
  {
    path: "/products",
    name: "Products",
    component: ProductsCRUD
  },
  {
    path: "/products/:id",
    name: "Products-details",
    component: ProductsCRUD
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
