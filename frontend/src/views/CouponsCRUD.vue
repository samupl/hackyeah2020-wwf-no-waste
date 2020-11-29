<script lang="ts">
import { CouponAPIClient } from "@/api/coupons";
import CRUDView from "@/components/base/CRUDView.vue";
import DateTimeInput from "@/components/forms/DateTimeInput.vue";
import { Coupon } from "@/interfaces/coupon";
import { AxiosError } from "axios";
import { Component } from "vue-property-decorator";
import { VFileInput, VTextarea, VTextField } from "vuetify/lib";

@Component
export default class CouponsCRUD extends CRUDView<Coupon> {
  public itemNamePlural = "Coupons";
  public itemName = "coupon";
  public baseUrl = "coupons";
  public headers = [
    {
      text: "Coupon",
      value: "coupon"
    },
    {
      text: "Expiry date",
      value: "expiry_date"
    },
    {
      text: "Required opinions",
      value: "required_opinions"
    },
    CRUDView.actionHeader
  ];

  public form = [
    {
      attribute: "icon",
      component: VFileInput,
      label: "Icon image"
    },
    {
      attribute: "coupon",
      component: VTextField,
      label: "Coupon"
    },
    {
      attribute: "description",
      component: VTextarea,
      label: "Description"
    },
    {
      attribute: "expiry_date",
      component: DateTimeInput,
      label: "Expiration date"
    },
    {
      attribute: "required_opinions",
      component: VTextField,
      label: "Required opinions"
    }
  ];

  public items: Coupon[] = [];
  public defaultItem = {
    // eslint-disable-next-line @typescript-eslint/camelcase
    required_opinions: 0,
    description: "",
    coupon: "",
    // eslint-disable-next-line @typescript-eslint/camelcase
    expiry_date: "",
    icon: ""
  };

  public async loadItems() {
    this.loading = true;
    const response = await CouponAPIClient.get(this.baseUrl);
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

    const formData = new FormData();
    formData.append("icon", (this.editedItem?.icon as unknown) as Blob);
    formData.append(
      "coupon",
      (this.editedItem?.coupon as unknown) as string | Blob
    );
    formData.append(
      "description",
      (this.editedItem?.description as unknown) as string | Blob
    );
    formData.append(
      "expiry_date",
      (this.formatDate(
        (this.editedItem?.expiry_date as unknown) as Date
      ) as unknown) as string | Blob
    );
    formData.append(
      "required_opinions",
      (this.editedItem?.required_opinions as unknown) as string | Blob
    );
    try {
      await CouponAPIClient.post(url, formData);
      this.close();
      await this.loadItems();
    } catch (err) {
      this.handleErrors(err);
    } finally {
      this.submitLoading = false;
    }
  }

  public handleErrors(err: AxiosError) {
    this.error = Object.values(err.response?.data)
      .map(i => (i as string[]).join(", "))
      .join(". ");
  }

  public formatDate(date: Date): string {
    if (date === undefined) return "";
    //  YYYY-MM-DDThh:mm[:ss[.uuuuuu]][+HH:MM|-HH:MM|Z].
    return `${date.getFullYear()}-${date.getMonth() +
      1}-${date.getDate()}T${date
      .getHours()
      .toString()
      .padStart(2, "0")}:${date
      .getMinutes()
      .toString()
      .padStart(2, "0")}`;
  }

  public async deleteItemConfirm() {
    this.error = "";
    this.deleteLoading = true;
    try {
      await CouponAPIClient.delete(`${this.baseUrl}/${this.editedItem?.id}`);
      this.editedItem = Object.assign({}, this.defaultItem);
    } finally {
      this.deleteLoading = false;
    }
    this.deleteDialog = false;
    await this.loadItems();
  }
}
</script>
