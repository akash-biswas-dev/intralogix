import ax, { AxiosInstance } from "axios";
import { createContext, ReactNode, useContext } from "react";
import useAuthContext from "./AuthContext";

const BASE_URL = process.env.BACKEND_URL || "http://localhost:9000";

const axios = ax.create({
  baseURL: BASE_URL,
});

const AxiosContext = createContext<undefined | AxiosInstance>(undefined);

export function AxiosProvider({ children }: { children: ReactNode }) {
  const authContext = useAuthContext();

  if (!authContext) {
    throw new Error("Conext data not set yet");
  }

  const { authorization, getAuthorization, saveAuthrization } = authContext;

  axios.interceptors.request.use(
    async function (config) {
      if (!authorization) return config;
      config.headers.Authorization = `Bearer ${authorization}`;
      return config;
    },
    async function (error) {
      if (!error.respone) {
        return Promise.reject(error);
      }
      const { respone } = error;
      if (respone.status === 401) {
        // Retry to generate the tokens using refresh token.
        const authToken = await getAuthorization();
        saveAuthrization(authToken);
        return axios(error.config);
      }
      return Promise.reject(error);
    },
  );

  return <AxiosContext value={axios}>{children}</AxiosContext>;
}
export default function useAxios() {
  return useContext(AxiosContext);
}
