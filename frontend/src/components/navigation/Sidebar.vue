<template>
  <v-navigation-drawer permanent app>
    <v-list dense nav>
      <v-list-item class="justify-center">
        <v-img :src="require('@/assets/logo.svg')" max-width="80px"></v-img>
      </v-list-item>
    </v-list>

    <v-divider></v-divider>

    <v-list dense nav>
      <v-list-item v-for="item in items" :key="item.title" :to="item.to" link>
        <v-list-item-icon>
          <v-icon>{{ item.icon }}</v-icon>
        </v-list-item-icon>

        <v-list-item-content>
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-navigation-drawer>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Sidebar extends Vue {
  public items = [
    {
      title: "Dashboard",
      icon: "mdi-view-dashboard",
      to: "/"
    },
    {
      title: "Products",
      icon: "mdi-basket",
      to: "/products"
    },
    {
      title: "Categories",
      icon: "mdi-shape",
      to: "/categories"
    },
    {
      title: "Tags",
      icon: "mdi-tag",
      to: "/tags"
    },
    {
      title: "Re-use examples",
      icon: "mdi-recycle",
      to: "/reuse"
    }
  ];
}
</script>
