"use client";
import {
  createContext,
  ReactNode,
  useContext,
  useState
} from "react";

const AuthContext = createContext<{
  authorization: string | undefined;
  updateAuthorization: (token: string | undefined) => void;
}>({
  authorization: undefined,
  updateAuthorization: () => {},
});

export const AuthContextProvider = ({
  initialAuth,
  children,
}: {
  initialAuth: string | undefined;
  children: ReactNode;
}) => {
  // The main authorization token which authorize the requests.
  const [authorization, setAuthorization] = useState<undefined| string>(initialAuth);

  const updateAuthorization = (auth: string | undefined) => {
    setAuthorization(auth);
  };


  return (
    <>
     {authorization && <AuthContext
      value={{
        authorization,
        updateAuthorization: updateAuthorization,
      }}
    >
      {children}
    </AuthContext>}
    </>
  )
}

export default function useAuthContext() {
  return useContext(AuthContext);
}
