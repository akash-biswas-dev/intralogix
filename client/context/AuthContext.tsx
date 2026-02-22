"use client";
import { createContext, ReactNode, useContext, useState } from "react";

type AuthContextType = {
  authorization?: Authorization;
  updateAuthorization: (pre: Authorization | undefined) => void;
};

const AuthContext = createContext<AuthContextType>({
  authorization: undefined,
  updateAuthorization: () => {},
});

export const AuthContextProvider = ({
  initialAuth,
  children,
}: {
  initialAuth: Authorization | undefined;
  children: ReactNode;
}) => {
  // The main authorization token which authorize the requests.
  const [authorization, setAuthorization] = useState<Authorization | undefined>(
    initialAuth,
  );

  const updateAuthorization = (auth: Authorization | undefined) => {
    setAuthorization(auth);
  };

  return (
    <>
      {authorization && (
        <AuthContext
          value={{
            authorization: authorization,
            updateAuthorization: updateAuthorization,
          }}
        >
          {children}
        </AuthContext>
      )}
    </>
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
