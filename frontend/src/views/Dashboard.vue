<template>
  <v-row>
    <v-col>
      <!-- Product summary (numeric) -->
      <v-card :loading="productsLoading">
        <v-card-title>{{ products.length }}</v-card-title>
        <v-card-subtitle>Products</v-card-subtitle>
      </v-card>
    </v-col>

    <v-col>
      <!-- Review summary (numeric) -->
      <v-card :loading="reviewsLoading">
        <v-card-title>{{ reviews.length }}</v-card-title>
        <v-card-subtitle>Reviews</v-card-subtitle>
      </v-card>
    </v-col>

    <v-col>
      <!-- Re-use ideas summary (numeric) -->
      <v-card :loading="reUseIdeasLoading">
        <v-card-title>{{ reUseIdeas.length }}</v-card-title>
        <v-card-subtitle>Re-use ideas</v-card-subtitle>
      </v-card>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { APIClient } from "@/api/cllient";
import { Product } from "@/interfaces/product";
import { Review } from "@/interfaces/review";
import { Component, Vue } from "vue-property-decorator";

@Component
export default class Dashboard extends Vue {
  public products: Product[] = [];
  public productsLoading = true;

  public reviews: Review[] = [];
  public reviewsLoading = true;

  public reUseIdeas: Review[] = [];
  public reUseIdeasLoading = true;

  public async fetchProducts() {
    this.productsLoading = true;
    try {
      const response = await APIClient.get("/api/product/all");
      this.products = response.data;
    } finally {
      this.productsLoading = false;
    }
  }

  public async fetchReviews() {
    this.reviewsLoading = true;
    try {
      const response = await APIClient.get("/api/review/all");
      this.reviews = response.data;
    } finally {
      this.reviewsLoading = false;
    }
  }

  public async fetchReUseIdeas() {
    this.reUseIdeasLoading = true;
    try {
      const response = await APIClient.get("/api/review/all");
      this.reUseIdeas = response.data;
    } finally {
      this.reUseIdeasLoading = false;
    }
  }
  public mounted() {
    this.fetchProducts();
    this.fetchReviews();
    this.fetchReUseIdeas();
  }
}
</script>
