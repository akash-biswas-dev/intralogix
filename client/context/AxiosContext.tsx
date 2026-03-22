"use client";
import ax, { AxiosInstance } from "axios";

import { createContext, ReactNode, useContext, useEffect } from "react";
import useAuthContext, { fetchAuthorization } from "./AuthContext";

import { useRouter } from "next/navigation";

import { SERVER_ADDRESS } from "./AuthContext";
import { LoadingPage } from "./LoadingContext";

const AxiosContext = createContext<undefined | AxiosInstance>(undefined);

export function AxiosProvider({ children }: { children: ReactNode }) {
  const { authorization, updateAuthorization } = useAuthContext();

  const router = useRouter();

  const axiosInstance = ax.create({
    baseURL: SERVER_ADDRESS,
  });

  // Retrying if encounter unauthorized on any request from server.
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
      if (respone.status === 401) {
        // Retry to generate the tokens using refresh token.
        const auth = await fetchAuthorization();
        updateAuthorization(auth);
        if (!auth) {
          return Promise.reject(error);
        }
      }
      return axiosInstance(error.config);
    },
  );

  useEffect(() => {
    (async () => {
      const auth = await fetchAuthorization();
      if (auth) {
        updateAuthorization(auth);
        return;
      }
      router.push("/auth");
    })();
  }, [updateAuthorization, router]);

  return (
    <>
      {authorization ? (
        <AxiosContext value={axiosInstance}> {children}</AxiosContext>
      ) : (
        <LoadingPage />
      )}
    </>
  );
}
export default function useAxios() {
  return useContext(AxiosContext);
}
