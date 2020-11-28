<template>
  <v-row>
    <v-col>
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

      <v-row>
        <v-col md="4">
          <v-card>
            <v-card-title>{{ categoriesData.labels.length }}</v-card-title>
            <v-card-subtitle>Categories</v-card-subtitle>
            <v-card-text>
              <PieChart
                :data="categoriesData.data"
                :labels="categoriesData.labels"
                :colors="chartColors"
              ></PieChart>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>

<script lang="ts">
import { APIClient } from "@/api/cllient";
import PieChart from "@/components/dashboard/PieChart.vue";
import { Product } from "@/interfaces/product";
import { Review } from "@/interfaces/review";
import { Component, Vue } from "vue-property-decorator";
@Component({
  components: { PieChart }
})
export default class Dashboard extends Vue {
  public chartColors = [
    "#FFEE58",
    "#E57373",
    "#1565C0",
    "#673AB7",
    "#9575CD",
    "#D32F2F",
    "#EF9A9A",
    "#CDDC39",
    "#DCE775",
    "#1E88E5",
    "#AFB42B",
    "#7986CB",
    "#43A047",
    "#FF8A65",
    "#03A9F4",
    "#D4E157",
    "#FDD835",
    "#303F9F",
    "#795548",
    "#9CCC65",
    "#8E24AA",
    "#FFCC80",
    "#7E57C2",
    "#01579B",
    "#FFB74D",
    "#3949AB",
    "#A1887F",
    "#9E9D24",
    "#FFA726",
    "#BF360C",
    "#F48FB1",
    "#EF5350",
    "#EF6C00",
    "#29B6F6",
    "#1976D2",
    "#6A1B9A",
    "#FF7043",
    "#AB47BC",
    "#512DA8",
    "#2E7D32",
    "#388E3C",
    "#FFEB3B",
    "#FB8C00",
    "#E91E63",
    "#4FC3F7",
    "#81D4FA",
    "#E64A19",
    "#66BB6A",
    "#FF9800",
    "#5E35B1",
    "#B39DDB",
    "#F57C00",
    "#039BE5",
    "#7B1FA2",
    "#6D4C40",
    "#BCAAA4",
    "#E53935",
    "#D81B60",
    "#E6EE9C",
    "#CE93D8",
    "#689F38",
    "#42A5F5",
    "#0D47A1",
    "#C0CA33",
    "#9C27B0",
    "#F4511E",
    "#BA68C8",
    "#3F51B5",
    "#0277BD",
    "#AED581",
    "#64B5F6",
    "#FBC02D",
    "#FFF176",
    "#4CAF50",
    "#D84315",
    "#8D6E63",
    "#FF5722",
    "#7CB342",
    "#283593",
    "#F06292",
    "#827717",
    "#8BC34A",
    "#1B5E20",
    "#90CAF9",
    "#EC407A",
    "#0288D1",
    "#5C6BC0",
    "#2196F3",
    "#F44336"
  ];

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

  public get categoriesData() {
    const categories: { [key: string]: number } = {};
    const categoryLabels = [];
    for (const product of this.products) {
      if (categoryLabels.indexOf(product.category.name) === -1) {
        categoryLabels.push(product.category.name);
        categories[product.category.name] = 0;
      }
      categories[product.category.name] += 1;
    }

    const categoriesData = [];
    for (const key of categoryLabels) {
      categoriesData.push(categories[key]);
    }
    return {
      labels: categoryLabels,
      data: categoriesData
    };
  }
}
</script>
