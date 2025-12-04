"use client";
import ax, { AxiosInstance } from "axios";
import { createContext, ReactNode, useContext } from "react";
import useAuthContext from "./AuthContext";

const BASE_URL = process.env.BACKEND_URL || "http://localhost:9000";

const AxiosContext = createContext<undefined | AxiosInstance>(undefined);

export function AxiosProvider({ children }: { children: ReactNode }) {
  const { authorization, updateAuthorization } = useAuthContext();

  const axiosInstance = ax.create({
    baseURL: BASE_URL,
  });
  axiosInstance.interceptors.request.use(
    function (config) {
      // Interceptor which include the authorizartion to the request.
      if (!authorization) return config;
      config.headers.Authorization = `Bearer ${authorization}`;
      return config;
    },
    async function (error) {
      // Retry logic when authorization failed recreate the authorization and update it.
      if (!error.respone) {
        return Promise.reject(error);
      }
      const { respone } = error;
      if (respone.status === 407) {
        // Retry to generate the tokens using refresh token.
        const res = await fetch("/api/refresh-token", {
          method: "post",
        });

        const { status } = res;
        if (status === 401) {
          updateAuthorization(undefined);
          return Promise.reject(error);
        }
        const body = await res.json();
        updateAuthorization(body.token);
        return axiosInstance(error.config);
      }
      return Promise.reject(error);
    },
  );

  return (
    <>
      {authorization && (
        <AxiosContext value={axiosInstance}>{children}</AxiosContext>
      )}
    </>
  );
}
export default function useAxios() {
  return useContext(AxiosContext);
}
