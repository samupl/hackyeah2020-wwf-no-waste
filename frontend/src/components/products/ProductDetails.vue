<template>
  <v-card :loading="loading">
    <v-img max-height="400" src="https://via.placeholder.com/468x480">
      <template v-slot:placeholder>
        <v-sheet>
          <v-skeleton-loader
            type="image"
            min-height="100px"
          ></v-skeleton-loader>
        </v-sheet>
      </template>
    </v-img>
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
      <v-row v-for="ratingConfig of ratingLabels" :key="ratingConfig.name">
        <v-col md="3">
          {{ ratingConfig.name }}
        </v-col>
        <v-col>
          <v-rating
            v-model="product.averageRatings[ratingConfig.attribute]"
            readonly
            half-increments
            color="yellow darken-3"
            background-color="grey darken-1"
          ></v-rating>
        </v-col>
      </v-row>

      <!-- comments -->
      <v-divider></v-divider>
      <h3 class="mt-3">Comments</h3>

      <v-card v-for="review of reviews" :key="review.id" class="ma-2">
        <v-card-text>
          <v-row>
            <v-col md="3">
              <h3 class="font-weight-regular">{{ review.author }}</h3>
              <h4 class="grey--text font-weight-regular">
                {{ formatDate(review.date) }}
              </h4>
            </v-col>
            <v-col>
              <v-row>
                <v-col class="text-right">
                  <h5 class="font-weight-regular">
                    Packaging re-usability:
                  </h5>
                  <span
                    v-if="review.productReusabilityRating === null"
                    class="grey--text"
                    >n/a</span
                  >
                  <v-rating
                    v-if="review.productReusabilityRating !== null"
                    v-model="review.productReusabilityRating"
                    small
                    dense
                    readonly
                    half-increments
                    color="yellow darken-3"
                    background-color="grey darken-1"
                  ></v-rating>
                </v-col>
                <v-col class="text-right">
                  <h5 class="font-weight-regular">Packaging:</h5>
                  <v-rating
                    v-model="review.boxRating"
                    small
                    dense
                    readonly
                    half-increments
                    color="yellow darken-3"
                    background-color="grey darken-1"
                  ></v-rating>
                </v-col>
                <v-col class="text-right">
                  <h5 class="font-weight-regular">Packaging re-usability:</h5>
                  <v-rating
                    v-model="review.boxReusabilityRating"
                    small
                    dense
                    readonly
                    half-increments
                    color="yellow darken-3"
                    background-color="grey darken-1"
                  ></v-rating>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <br />
          {{ review.comment }}
        </v-card-text>
      </v-card>
    </v-card-text>
  </v-card>
</template>

<script lang="ts">
import { APIClient } from "@/api/cllient";
import { Product } from "@/interfaces/product";
import { Review } from "@/interfaces/review";
import { Component, Vue, Prop, Watch } from "vue-property-decorator";

@Component
export default class ProductDetails extends Vue {
  @Prop({ type: Number, default: undefined }) productId?: number;

  public loading = true;
  public product: Product = {
    id: undefined,
    name: "",
    category: {
      name: ""
    },
    tags: [],
    averageRatings: {
      boxRating: 0,
      productReusabilityRating: 0,
      boxReusabilityRating: 0
    }
  };
  public defaultProduct: Product = {
    id: undefined,
    name: "",
    category: {
      name: ""
    },
    tags: [],
    averageRatings: {
      boxRating: 0,
      productReusabilityRating: 0,
      boxReusabilityRating: 0
    }
  };
  public ratingLabels = [
    { name: "Packaging rating", attribute: "boxRating" },
    { name: "Packaging re-usability", attribute: "boxReusabilityRating" },
    { name: "Product re-usability", attribute: "productReusabilityRating" }
  ];
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
