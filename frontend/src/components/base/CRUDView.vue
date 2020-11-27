<template>
  <v-data-table :headers="headers" :items="items">
    <template v-slot:item.actions="{ item }">
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
  </v-data-table>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { DataTableHeader } from "vuetify";

@Component
export default class CRUDView<T> extends Vue {
  public static actionHeader: DataTableHeader = {
    text: "Actions",
    value: "actions",
    align: "end"
  };

  public headers: DataTableHeader[] = [];
  public items: T[] = [];

  public editEnabled = true;
  public deleteEnabled = true;
}
</script>
