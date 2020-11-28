import axios from "axios";

export default axios.create({
  baseURL: "http://192.168.1.228:8888/", // TODO: duh.
  headers: {
    "Content-type": "application/json"
  }
});
