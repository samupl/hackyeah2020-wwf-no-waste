<script lang="ts">
import CRUDView from "@/components/base/CRUDView.vue";
import { Category } from "@/interfaces/category";
import { Component } from "vue-property-decorator";
import { VTextField } from "vuetify/lib";

@Component
export default class CategoriesCRUD extends CRUDView<Category> {
  public itemNamePlural = "Categories"
  public itemName = "category";
  public baseUrl = "api/category";
  public headers = [
    {
      text: "Name",
      value: "name"
    },
    CRUDView.actionHeader
  ];

  public form = [
    {
      attribute: "name",
      component: VTextField,
      label: "Name"
    }
  ];

  public items: Category[] = [];
  public defaultItem = {
    name: ""
  };
}
</script>
