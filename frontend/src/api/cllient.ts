import Axios from "axios";

export class APIClient {
  private static url = process.env.VUE_APP_API_URL || "http://127.0.0.1:8080/";
  private static axios = Axios.create({
    baseURL: APIClient.url,
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
}
