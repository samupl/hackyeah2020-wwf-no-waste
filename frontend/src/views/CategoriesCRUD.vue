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

  public mounted() {
    this.items = [];
    for (let i = 1; i <= 39; ++i) {
      this.items.push({
        id: i,
        name: `Category ${i}`
      });
    }
  }
}
</script>
