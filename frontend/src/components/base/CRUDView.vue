<template>
  <v-data-table :headers="headers" :items="items">
    <template v-slot:top>
      <v-toolbar flat>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px" @click:outside="cancel()">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              New {{ itemName }}
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>
            <v-card-text>
              <component
                :is="field.component"
                v-for="field of form"
                :key="field.attribute"
                :label="field.label"
                v-model="editedItem[field.attribute]"
              ></component>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="grey darken-1" text @click="cancel">
                Cancel
              </v-btn>
              <v-btn color="blue darken-1" text @click="submit">
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="deleteDialog" max-width="500px">
          <v-card>
            <v-card-title>Delete {{ itemName }}</v-card-title>
            <v-card-text>
              Are you sure you want to delete {{ itemName }}
              {{ editedItem.id }}?
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="grey darken-1" text @click="cancelDelete">
                Cancel
              </v-btn>
              <v-btn color="error darken-1" text @click="deleteItemConfirm">
                Delete
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>

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

interface FormField {
  attribute: string;
  component: any;
  label: string;
}

@Component
export default class CRUDView<T> extends Vue {
  public static actionHeader: DataTableHeader = {
    text: "Actions",
    value: "actions",
    align: "end",
    sortable: false
  };

  public headers: DataTableHeader[] = [];
  public items: T[] = [];
  public editedItem?: T = {} as T;
  public defaultItem?: T = undefined;
  public itemName = "Item (override)";
  public editMode = false;

  public editEnabled = true;
  public deleteEnabled = true;

  public form: FormField[] = [];

  public dialog = false;
  public deleteDialog = false;

  public get formTitle() {
    return this.editMode ? `Edit ${this.itemName}` : `Add ${this.itemName}`;
  }

  public close() {
    this.dialog = false;
  }

  public cancel() {
    this.$nextTick(() => {
      this.editedItem = Object.assign({}, this.defaultItem);
      this.editMode = false;
    });

    this.close();
  }

  public submit() {
    this.close();
  }

  public editItem(item: T) {
    this.editMode = true;
    this.editedItem = item;
    this.dialog = true;
  }

  public deleteItem(item: T) {
    this.editedItem = item;
    this.deleteDialog = true;
  }

  public cancelDelete() {
    this.editedItem = Object.assign({}, this.defaultItem);
    this.deleteDialog = false;
  }

  public deleteItemConfirm() {
    console.log('Confirming.');
  }
}
</script>
