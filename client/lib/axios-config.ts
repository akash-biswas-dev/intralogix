import ax, { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import { cookies, headers } from "next/headers";
import { redirect } from "next/navigation";

export const ACCESS_TOKEN = "access-token";
export const REFRESH_TOKEN = "refresh-token";

const BASE_URL = process.env.BACKEND_URL || "http://localhost:9000";
const axios = ax.create({
  baseURL: BASE_URL,
});

axios.interceptors.request.use(
  async function (config) {
    const httpHeaders = await headers();
    const accessToken = httpHeaders.get("X-Access-Token");
    if (!accessToken) return config;
    config.headers.Authorization = `Bearer ${accessToken}`;
    return config;
  },
  async function (error) {
    if (error.respone?.status !== 401) {
      return Promise.reject(error);
    }

    const cookie = await extractCookie(REFRESH_TOKEN);

    if (!cookie) return Promise.reject(error);

    const res = await axios<{ accessToken: string; refreshToken: string }>({
      url: "/api/v1/auth/refresh-token",
      method: "post",
      headers: {
        "X-Refresh-Token": cookie,
      },
    });

    const { status, data } = res;
    if (status !== 200) {
      redirect("/auth");
    }
    const cookieStore = await cookies();

    const { accessToken, refreshToken } = data;
    cookieStore.set(ACCESS_TOKEN, accessToken);
    cookieStore.set(REFRESH_TOKEN, refreshToken);

    return axios(error.config);
  },
);

const extractCookie = async (key: string) => {
  const cookieStore = await cookies();
  const cookie = cookieStore.get(key);
  if (cookie === undefined || cookie.value === "") return null;
  return cookie.value;
};

export async function post<K, T>(
  url: string,
  data: T | null = null,
  config: AxiosRequestConfig<T> = {},
): Promise<AxiosResponse<K>> {
  try {
    const res = await axios.post<K>(url, data, config);
    return res;
  } catch (err) {
    const customError = new Error("Something went wrong.");
    if (err instanceof AxiosError) {
      const { response, message } = err;
      console.error(message);
      if (!response) {
        throw customError;
      }
      const serverMessage = response.headers["X-Message"];
      if (message) {
        throw new Error(serverMessage);
      }
    }
    throw customError;
  }
}

export default axios;
