<template>
  <v-data-table :headers="headers" :items="items" :loading="loading">
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>{{ itemNamePlural }}</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="500px" @click:outside="cancel()">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              New {{ itemName }}
            </v-btn>
          </template>
          <v-card :loading="submitLoading">
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>
            <v-card-text>
              <v-alert
                v-model="hasError"
                type="error"
                transition="slide-y-transition"
              >
                {{ error }}
              </v-alert>
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
              <v-btn
                color="grey darken-1"
                text
                @click="cancel"
                :disabled="submitLoading"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="submit"
                :loading="submitLoading"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="deleteDialog" max-width="500px">
          <v-card :loading="deleteLoading">
            <v-card-title>Delete {{ itemName }}</v-card-title>
            <v-card-text>
              Are you sure you want to delete {{ itemName }}
              {{ editedItem.id }}?
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="grey darken-1"
                text
                @click="cancelDelete"
                :disabled="deleteLoading"
              >
                Cancel
              </v-btn>
              <v-btn
                color="error darken-1"
                text
                @click="deleteItemConfirm"
                :loading="deleteLoading"
              >
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
import { APIClient } from "@/api/cllient";
import { AxiosError } from "axios";
import { Component, Vue } from "vue-property-decorator";
import { DataTableHeader } from "vuetify";

interface FormField {
  attribute: string;
  component: unknown;
  label: string;
}

interface ItemType {
  id?: number | string;
}

@Component
export default class CRUDView<T extends ItemType> extends Vue {
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
  public itemNamePlural = "Items (override)";
  public editMode = false;
  public loading = false;
  public deleteLoading = false;
  public submitLoading = false;
  public baseUrl = "";
  public error = "";

  public editEnabled = false;
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

  public mounted() {
    this.loadItems();
  }

  // eslint-disable-next-line @typescript-eslint/no-empty-function
  public async loadItems() {
    this.loading = true;
    const response = await APIClient.get(`${this.baseUrl}/all`);
    try {
      this.items = response.data;
    } finally {
      this.loading = false;
    }
  }

  public async submit() {
    this.error = "";
    this.submitLoading = true;
    const url = this.editMode
      ? `${this.baseUrl}/${this.editedItem?.id}`
      : this.baseUrl;
    try {
      await APIClient.put(url, this.editedItem);
      this.close();
      await this.loadItems();
    } catch (err) {
      this.handleErrors(err);
    } finally {
      this.submitLoading = false;
    }
  }

  public async deleteItemConfirm() {
    this.error = "";
    this.deleteLoading = true;
    try {
      await APIClient.delete(`${this.baseUrl}/${this.editedItem?.id}`);
      this.editedItem = Object.assign({}, this.defaultItem);
    } finally {
      this.deleteLoading = false;
    }
    this.deleteDialog = false;
    await this.loadItems();
  }

  public handleErrors(err: AxiosError) {
    this.error = err.response?.data.reason;
  }

  public get hasError() {
    return this.error.length > 0;
  }
}
</script>
