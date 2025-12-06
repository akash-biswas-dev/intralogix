"use client";
import ax, { AxiosInstance } from "axios";

import { createContext, ReactNode, useContext } from "react";
import useAuthContext from "./AuthContext";

import { isUserAuthorized } from "@/app/auth/action";
import { useRouter } from "next/navigation";

const BASE_URL = process.env.BACKEND_URL || "http://localhost:9000";

const AxiosContext = createContext<undefined | AxiosInstance>(undefined);

export function AxiosProvider({ children }: { children: ReactNode }) {

  const router = useRouter();
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
        const auth = await isUserAuthorized();
        if (!auth) {
          updateAuthorization(undefined);
          return Promise.reject(error);
        }
        const { authorization, isTemporary } = auth;
        if (isTemporary) {
          router.replace("/update-profile");
        }
        updateAuthorization(authorization);
        return axiosInstance(error.config);
      }
      return Promise.reject(error);
    }
  );

  return <AxiosContext value={axiosInstance}>{children}</AxiosContext>;
}
export default function useAxios() {
  return useContext(AxiosContext);
}
