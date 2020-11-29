import Axios from "axios";

export class CouponAPIClient {
  private static url = process.env.VUE_COUPON_APP_API_URL || "http://127.0.0.1:8000/";
  private static axios = Axios.create({
    baseURL: CouponAPIClient.url,
    headers: {
      "Content-type": "application/json"
    }
  });

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  static async get(url: string, ...args: any) {
    return await this.axios.get(url, ...args);
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  static async put(url: string, ...args: any) {
    return await this.axios.put(url, ...args);
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  static async delete(url: string, ...args: any) {
    return await this.axios.delete(url, ...args);
  }

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  static async post(url: string, ...args: any) {
    return await this.axios.post(url, ...args);
  }
}
