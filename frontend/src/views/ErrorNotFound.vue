<template>
  <v-container fluid>
    <v-row align="center" justify="center">
      <v-col>
        <h1>404</h1>
        <h2>Not found</h2>
        <p>The page you requested cannot be found. Please double the URL.</p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";

@Component
export default class ErrorNotFound extends Vue {}
</script>
