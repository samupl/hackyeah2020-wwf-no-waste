<template>
  <v-select :items="items" :value="value" @input="update" label="Category"></v-select>
</template>

<script lang="ts">
import http from "@/api/http";
import { Component, Vue, Prop } from "vue-property-decorator";

@Component
export default class CategoryInput extends Vue {
  @Prop({ type: Number, default: 0 }) value?: string;
  public items = [];

  public async mounted() {
    const response = await http.get("api/category/all");
    this.items = response.data.map((item: any) => {
      return { value: item.id, text: item.name };
    });
  }

  public update(newValue: number) {
    this.$emit("input", newValue);
  }
}
</script>
