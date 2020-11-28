<template>
  <v-card :loading="loading">
    <v-img
      max-height="400"
      :src="product.photoUrl"
      v-if="product.photoUrl !== null"
    >
    </v-img>
    <v-sheet
      v-if="product.photoUrl === null"
      class="grey lighten-3 text-center flex justify-center"
      height="400px"
    >
      <v-icon class="grey--text lighten-5 missing-image-icon"
        >mdi-image-off</v-icon
      >
    </v-sheet>
    <v-skeleton-loader
      type="list-item-two-line"
      v-if="loading"
    ></v-skeleton-loader>
    <v-card-title v-if="!loading">
      {{ product.name }}
    </v-card-title>
    <v-card-subtitle v-if="!loading">
      {{ product.category.name }}
    </v-card-subtitle>

    <v-card-text>
      <div class="mb-2">
        <v-chip v-for="tag of product.tags" :key="tag.id" small class="ma-2">
          {{ tag.name }}
        </v-chip>
      </div>

      <!-- Ratings -->
      <v-row>
        <v-col>
          <h3>Product</h3>
          <v-row
            v-for="ratingConfig of ratingLabels.product"
            :key="ratingConfig.name"
          >
            <v-col>
              <h4 class="font-weight-regular">{{ ratingConfig.name }}:</h4>
              <v-rating
                v-model="product.averageRatings[ratingConfig.attribute]"
                readonly
                half-increments
                color="yellow darken-3"
                background-color="grey darken-1"
              ></v-rating>
            </v-col>
          </v-row>
        </v-col>
        <v-col>
          <h3>Packaging</h3>
          <v-row
            v-for="ratingConfig of ratingLabels.packaging"
            :key="ratingConfig.name"
          >
            <v-col>
              <h4 class="font-weight-regular">{{ ratingConfig.name }}:</h4>
              <v-rating
                v-model="product.averageRatings[ratingConfig.attribute]"
                readonly
                half-increments
                color="yellow darken-3"
                background-color="grey darken-1"
              ></v-rating>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <!-- comments -->
      <v-divider></v-divider>
      <h3 class="mt-3">Comments</h3>

      <v-card v-for="review of reviews" :key="review.id" class="ma-2">
        <v-card-text>
          <v-row>
            <v-col md="6">
              <h3 class="font-weight-regular">{{ review.author }}</h3>
              <h4 class="grey--text font-weight-regular">
                {{ formatDate(review.date) }}
              </h4>
              {{ review.comment }}
            </v-col>
            <v-col>
              <h5 class="font-weight-regular">Product</h5>
              <v-row
                v-for="ratingConfig of ratingLabels.product"
                :key="ratingConfig.name"
              >
                <v-col class="ma-0 pa-1 pl-3">
                  <h5 class="font-weight-regular">{{ ratingConfig.name }}:</h5>
                  <v-rating
                    v-model="product.averageRatings[ratingConfig.attribute]"
                    readonly
                    half-increments
                    color="yellow darken-3"
                    background-color="grey darken-1"
                    dense
                    x-small
                  ></v-rating>
                </v-col>
              </v-row>
            </v-col>
            <v-col>
              <h5 class="font-weight-regular">Packaging</h5>
              <v-row
                v-for="ratingConfig of ratingLabels.packaging"
                :key="ratingConfig.name"
              >
                <v-col class="ma-0 pa-1 pl-3">
                  <h5 class="font-weight-regular ma-0">
                    {{ ratingConfig.name }}:
                  </h5>
                  <v-rating
                    v-model="product.averageRatings[ratingConfig.attribute]"
                    readonly
                    half-increments
                    color="yellow darken-3"
                    background-color="grey darken-1"
                    dense
                    x-small
                  ></v-rating>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-card-text>
      </v-card>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import { APIClient } from "@/api/cllient";
import { defaultProduct, Product } from "@/interfaces/product";
import { Review } from "@/interfaces/review";
import { Component, Vue, Prop, Watch } from "vue-property-decorator";

@Component
export default class ProductDetails extends Vue {
  @Prop({ type: Number, default: undefined }) productId?: number;

  public loading = true;
  public product: Product = Object.assign({}, defaultProduct);
  public defaultProduct: Product = defaultProduct;
  public ratingLabels = {
    packaging: [
      {
        name: "From from recycled materials",
        attribute: "boxFromRecycling"
      },
      { name: "Recyclable/Biodegradable", attribute: "boxRecycable" },
      { name: "Re-usability", attribute: "boxReusable" }
    ],
    product: [
      {
        name: "From from recycled materials",
        attribute: "productFromRecycling"
      },
      { name: "Recyclable/Biodegradable", attribute: "productRecycable" },
      { name: "Re-usability", attribute: "productReusable" },
      { name: "Repairability", attribute: "repairable" }
    ]
  };

  public reviews: Review[] = [];

  @Watch("productId")
  public onProductIdChanged() {
    this.product = Object.assign({}, this.defaultProduct);
    this.fetch();
  }

  public async fetch() {
    this.reviews = [];
    this.loading = true;
    try {
      const response = await APIClient.get(`/api/product/${this.productId}`);
      this.product = response.data;

      const responseReviews = await APIClient.get(
        `/api/review/find-by-product/${this.productId}`
      );
      this.reviews = responseReviews.data;
    } finally {
      this.loading = false;
    }
  }

  public mounted() {
    this.fetch();
  }

  public formatDate(date: number[]) {
    return `${date[0]}-${date[1]}-${date[2]} ${date[3]}:${date[4]}`;
  }
}
</script>

<style lang="scss">
.missing-image-icon {
  padding-top: 100px;
  font-size: 200px !important;
  opacity: 0.25;
}
</style>
