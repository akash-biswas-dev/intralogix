"use client";
import { useRouter } from "next/navigation";
import {
  createContext,
  ReactNode,
  useContext,
  useEffect,
  useState,
} from "react";
import useLoadingContext from "./LoadingContext";

const AuthContext = createContext<{
  authorization?: string;
  temporaryAuth?: string;
  updateAuthorization: (token: string | undefined) => void;
  isUserAuthorized: () => boolean;
}>({
  updateAuthorization: () => {},
  isUserAuthorized: () => {
    return true;
  },
});

export const AuthContextProvider = ({ children }: { children: ReactNode }) => {
  const router = useRouter();
  // The main authorization token which authorize the requests.
  const [authorization, setAuthorization] = useState<undefined | string>(
    undefined,
  );
  // This state variable store the temporary authorization for thw accounts which are not enabled.
  const [temporaryAuth, setTemporaryAuth] = useState<undefined | string>(
    undefined,
  );
  // This state variable stop the rendering until the authorization fetched.

  const { setLoading } = useLoadingContext();

  useEffect(() => {
    // Fetch the authorization from the nextjs route handler.
    async function fetchAuthorization() {
      setLoading(true);
      const res = await fetch("/api/refresh-token", {
        method: "POST",
      });
      if (res.status === 307) {
        // Case when account created but not enabled.
        const body = await res.json();
        setTemporaryAuth(body.token);
        router.push("/update-profile");
      }

      if (res.status !== 201) {
        // Create a notification that operation not successful.
        // TODO: Send a notification.
        return;
      }

      const body = await res.json();
      setAuthorization(body.token);
      router.push("/dashboard");
      setLoading(false);
    }
    fetchAuthorization().catch((err) => {
      setLoading(false);
      console.error(err);
    });
  }, [router, setLoading]);

  const updateAuthorization = (token: string | undefined) => {
    setAuthorization(token);
  };

  const isUserAuthorized = () => {
    return authorization ? true : false;
  };

  const isUserAnyTypeAuthorized = authorization || temporaryAuth ? true : false;

  return (
    <AuthContext
      value={{
        authorization,
        temporaryAuth,
        updateAuthorization: updateAuthorization,
        isUserAuthorized: isUserAuthorized,
      }}
    >
      {isUserAnyTypeAuthorized && children}
    </AuthContext>
  );
};

export default function useAuthContext() {
  return useContext(AuthContext);
}
