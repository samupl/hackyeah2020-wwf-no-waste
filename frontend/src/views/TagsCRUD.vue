<script lang="ts">
import CRUDView from "@/components/base/CRUDView.vue";
import { Component } from "vue-property-decorator";

interface Tag {
  id: string;
  name: string;
}

@Component
export default class TagsCRUD extends CRUDView<Tag> {
  public headers = [
    {
      text: "Name",
      value: "name"
    },
    CRUDView.actionHeader
  ];
  public items: Tag[] = [
    {
      id: "1",
      name: "Test1"
    },
    {
      id: "2",
      name: "Test2"
    },
    {
      id: "3",
      name: "Test3"
    }
  ];
}
</script>
