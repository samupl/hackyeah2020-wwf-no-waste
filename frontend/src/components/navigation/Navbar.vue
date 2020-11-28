<template>
  <v-app-bar app color="black" dark flat>
    <div class="d-flex align-center">Product Panda administrative interface</div>

    <v-spacer></v-spacer>

    <v-btn target="_blank" text @click="logout">
      <v-icon>mdi-power</v-icon>
      <span class="mr-2">Sign out</span>
    </v-btn>
  </v-app-bar>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Navbar extends Vue {
  public logout() {
    this.$store.commit("setToken", "");
    this.$router.push("/login");
  }
}
</script>
