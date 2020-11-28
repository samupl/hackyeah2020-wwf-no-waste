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
import http from "@/api/http";
import { Component, Vue, Prop } from "vue-property-decorator";

@Component
export default class TagsInput extends Vue {
  @Prop({ type: Array, default: () => [] }) value?: string;
  public items = [];

  public async mounted() {
    const response = await http.get("api/tag/all");
    this.items = response.data.map((item: any) => {
      return { value: item.id, text: item.name };
    });
  }

  public update(newValue: number) {
    this.$emit("input", newValue);
  }
}
</script>
