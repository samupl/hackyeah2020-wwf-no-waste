import axios from "axios";

const baseURL = process.env.VUE_APP_API_URL || "http://127.0.0.1:8080/"
export default axios.create({
  baseURL: baseURL, // TODO: duh.
  headers: {
    "Content-type": "application/json"
  }
});
