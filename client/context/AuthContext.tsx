"use client";
import axios from "axios";
import React, {
  createContext,
  ReactNode,
  Suspense,
  use,
  useContext,
  useEffect,
  useState,
} from "react";
import { LoadingPage } from "./LoadingContext";

export const SERVER_ADDRESS =
  process.env.NEXT_PUBLIC_SERVER_ADDRESS || "http://localhost:9000";

type AuthContextType = {
  authorization?: Authorization;
  updateAuthorization: (pre: Authorization | undefined) => void;
};

const AuthContext = createContext<AuthContextType>({
  authorization: undefined,
  updateAuthorization: () => {},
});

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  // The main authorization token which authorize the requests.
  const [authorization, setAuthorization] = useState<Authorization | undefined>(
    undefined,
  );

  useEffect(() => {
    const updateAuthorization = async () => {
      const auth = await fetchAuthorization();
      setAuthorization(auth);
    };
    updateAuthorization();
  }, []);

  return (
    <AuthContext
      value={{
        authorization: authorization,
        updateAuthorization: setAuthorization,
      }}
    >
      {children}
    </AuthContext>
  );
};

export default function useAuthContext() {
  return useContext(AuthContext);
}

export interface User {
  firstName: string;
  lastName: string;
}

export interface Authorization {
  user?: User;
  token?: string;
}

export async function fetchAuthorization(): Promise<Authorization | undefined> {
  const res = await axios.post(
    `${SERVER_ADDRESS}/api/v1/auth/refresh-authorization`,
    null,
    {
      validateStatus: () => true,
      withCredentials: true,
    },
  );

  const { status, data } = res;

  if (status === 201) {
    return data;
  }

  console.error(data);
  return undefined;
}
