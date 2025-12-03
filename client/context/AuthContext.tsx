"use client";
import { useRouter } from "next/navigation";
import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";

const AuthContext = createContext<
  | undefined
  | {
      authorization?: string;
      getAuthorization: () => Promise<string>;
      saveAuthrization: (token: string) => void;
    }
>(undefined);

export const AuthContextProvider = ({ children }: { children: ReactNode }) => {
  const router = useRouter();
  const [authorization, setAuthorization] = useState<undefined | string>(
    undefined,
  );

  const getAuthrozation = async () => {
    const res = await fetch("/api/refresh-token", {
      method: "post",
    });

    if (res.status === 201) {
      const authToken = await res.text();
      return authToken;
    }
    return Promise.reject();
  };

  useEffect(() => {
    getAuthrozation()
      .then((authToken) => {
        setAuthorization(authToken);
        router.push("/dashboard");
      })
      .catch(() => {
        router.push("/auth");
      });
  });

  const saveAuthrization = (token: string) => {
    setAuthorization(token);
  };

  return (
    <AuthContext
      value={{
        authorization,
        getAuthorization: getAuthrozation,
        saveAuthrization: saveAuthrization,
      }}
    >
      {children}
    </AuthContext>
  );
};

export default function useAuthContext() {
  return useContext(AuthContext);
}
