<template>
  <v-datetime-picker :label="label" v-model="selectedDate">
    <template v-slot:dateIcon>
      <v-icon>mdi-calendar</v-icon>
    </template>
    <template v-slot:timeIcon>
      <v-icon>mdi-clock</v-icon>
    </template>
  </v-datetime-picker>
</template>

<script lang="ts">
import { Component, Vue, Prop, Watch } from "vue-property-decorator";

@Component
export default class DateTimeInput extends Vue {
  @Prop({ type: String, default: "" }) public label?: string;

  public selectedDate = "";

  @Watch("selectedDate")
  public onSelectedDate() {
    this.$emit("input", this.selectedDate);
  }
}
</script>
