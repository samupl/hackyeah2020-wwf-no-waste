<template>
  <v-app>
    <Sidebar></Sidebar>
    <Navbar></Navbar>

    <v-main>
      <v-container fluid>
        <router-view></router-view>
      </v-container>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Navbar from "@/components/navigation/Navbar.vue";
import Sidebar from "@/components/navigation/Sidebar.vue";
import Vue from "vue";
import { Component } from "vue-property-decorator";
import HelloWorld from "./components/HelloWorld.vue";

@Component({
  components: {
    Sidebar,
    Navbar,
    HelloWorld
  }
})
export default class App extends Vue {}
</script>
