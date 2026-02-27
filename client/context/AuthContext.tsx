"use client";
import axios from "axios";
import { createContext, ReactNode, use, useContext, useState } from "react";

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

const authorizationPromise = fetchAuthorization();
export const AuthContextProvider = ({ children }: { children: ReactNode }) => {
  const initialAuth = use(authorizationPromise);
  // The main authorization token which authorize the requests.
  const [authorization, setAuthorization] = useState<Authorization | undefined>(
    initialAuth,
  );

  const updateAuthorization = (auth: Authorization | undefined) => {
    setAuthorization(auth);
  };

  return (
    <AuthContext
      value={{
        authorization: authorization,
        updateAuthorization: updateAuthorization,
      }}
    >
      {children}
    </AuthContext>
  );
};
export async function fetchAuthorization(): Promise<Authorization | undefined> {
  const res = await axios.post(
    `${SERVER_ADDRESS}/api/v1/auth/refresh-authorization`,
    null,
    {
      validateStatus: () => true,
    },
  );

  const { status, data } = res;

  if (status === 201) {
    return data;
  }

  console.error(data);

  return undefined;
}

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
