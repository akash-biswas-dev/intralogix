import axios from "axios";

const API = process.env.SERVER_URL;

export function getAxios() {
  const instance = axios.create({
    baseURL: API,
    validateStatus: () => true,
  });
  return instance;
}
