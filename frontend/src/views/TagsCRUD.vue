<script lang="ts">
import CRUDView from "@/components/base/CRUDView.vue";
import { Component } from "vue-property-decorator";
import { VTextField } from "vuetify/lib";

interface Tag {
  id?: number;
  name: string;
}

@Component
export default class TagsCRUD extends CRUDView<Tag> {
  public itemName = "tag";
  public baseUrl = "api/tag";
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

  public items: Tag[] = [];
  public defaultItem = {
    name: ""
  };
}
</script>
