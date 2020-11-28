<script lang="ts">
import CRUDView from "@/components/base/CRUDView.vue";
import { Component } from "vue-property-decorator";
import { VTextField } from "vuetify/lib";

interface Category {
  id?: number;
  name: string;
}

@Component
export default class CategoriesCRUD extends CRUDView<Category> {
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
