<template>
  <!-- This should be somehow inherited, but I have no f****g idea how. -->
  <v-data-table :headers="headers" :items="items" :loading="loading">
    <!-- The only change from the base template -->
    <template v-slot:item.category="{ item }">
      {{ item.category.name }}
    </template>

    <template v-slot:item.tags="{ item }">
      <div>
        <v-chip v-for="tag of item.tags" :key="tag.id" small class="ma-2">
          {{ tag.name }}
        </v-chip>
      </div>
    </template>

    <template v-slot:item.actions="{ item }">
      <v-btn icon small :to="`/products/${item.id}`">
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <v-btn icon small @click="editItem(item)" v-if="editEnabled">
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn
        icon
        small
        @click="deleteItem(item)"
        v-if="deleteEnabled"
        class="error--text"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </template>
    <!-- /The only change from the base template -->

    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>{{ itemNamePlural }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px" @click:outside="cancel()">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              New {{ itemName }}
            </v-btn>
          </template>
          <v-card :loading="submitLoading">
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>
            <v-card-text>
              <v-alert
                v-model="hasError"
                type="error"
                transition="slide-y-transition"
              >
                {{ error }}
              </v-alert>
              <component
                :is="field.component"
                v-for="field of form"
                :key="field.attribute"
                :label="field.label"
                v-model="editedItem[field.attribute]"
              ></component>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="grey darken-1"
                text
                @click="cancel"
                :disabled="submitLoading"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="submit"
                :loading="submitLoading"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="deleteDialog" max-width="500px">
          <v-card :loading="deleteLoading">
            <v-card-title>Delete {{ itemName }}</v-card-title>
            <v-card-text>
              Are you sure you want to delete {{ itemName }}
              {{ editedItem.id }}?
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="grey darken-1"
                text
                @click="cancelDelete"
                :disabled="deleteLoading"
              >
                Cancel
              </v-btn>
              <v-btn
                color="error darken-1"
                text
                @click="deleteItemConfirm"
                :loading="deleteLoading"
              >
                Delete
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>

      <v-dialog
        v-model="detailsModal"
        @click:outside="hideDetails()"
        max-width="900px"
      >
        <ProductDetails :product-id="detailId"></ProductDetails>
      </v-dialog>
    </template>
  </v-data-table>
</template>

<script lang="ts">
import CRUDView from "@/components/base/CRUDView.vue";
import CategoryInput from "@/components/forms/CategoryInput.vue";
import TagsInput from "@/components/forms/TagsInput.vue";
import ProductDetails from "@/components/products/ProductDetails.vue";
import { defaultProduct, Product } from "@/interfaces/product";
import { Component, Watch } from "vue-property-decorator";
import { VTextField } from "vuetify/lib";

@Component({
  components: { ProductDetails, CRUDView }
})
export default class ProductsCRUD extends CRUDView<Product> {
  public itemNamePlural = "Products";
  public itemName = "product";
  public baseUrl = "api/product";
  public headers = [
    {
      text: "Name",
      value: "name"
    },
    {
      text: "Bar Code",
      value: "barCode"
    },
    {
      text: "Category",
      value: "category"
    },
    {
      text: "Tags",
      value: "tags"
    },
    CRUDView.actionHeader
  ];

  public form = [
    {
      attribute: "name",
      component: VTextField,
      label: "Name"
    },
    {
      attribute: "barCode",
      component: VTextField,
      label: "Bar Code"
    },
    {
      attribute: "categoryId",
      component: CategoryInput,
      label: "Category"
    },
    {
      attribute: "tags",
      component: TagsInput,
      label: "Tags"
    }
  ];

  public items: Product[] = [];
  public defaultItem = defaultProduct;
  public detailsModal = false;
  public detailId = 0;

  public showDetails(id: number) {
    this.detailId = id;
    this.detailsModal = true;
  }

  public hideDetails() {
    if (this.$route.path !== "/products") this.$router.push("/products");
  }

  @Watch("$route")
  public onRouteChange() {
    const detailId = this.$route.params.id;
    if (detailId !== undefined) this.showDetails(parseInt(detailId, 10));
    else this.hideDetails();
  }

  public mounted() {
    this.onRouteChange();
  }
}
</script>
