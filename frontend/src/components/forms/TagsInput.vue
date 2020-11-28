<template>
  <v-select
    :items="items"
    :value="value"
    @input="update"
    label="Tags"
    multiple
    chips
  ></v-select>
</template>

<script lang="ts">
import { APIClient } from "@/api/cllient";
import { Tag } from "@/interfaces/tag";
import { Component, Vue, Prop } from "vue-property-decorator";

@Component
export default class TagsInput extends Vue {
  @Prop({ type: Array, default: () => [] }) value?: string;
  public items = [];

  public async mounted() {
    const response = await APIClient.get("api/tag/all");
    this.items = response.data.map((item: Tag) => {
      return { value: item.id, text: item.name };
    });
  }

  public update(newValue: number) {
    this.$emit("input", newValue);
  }
}
</script>
