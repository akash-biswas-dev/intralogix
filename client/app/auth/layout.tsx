"use client";

import { ReactNode } from "react";
import useAuthContext, {
  AuthProvider,
  fetchAuthorization,
} from "@/context/AuthContext";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function AuthLayout({ children }: { children: ReactNode }) {
  return (
    <AuthProvider>
      <CheckAuthorization>
        <div className="w-full min-h-screen relative">{children}</div>
      </CheckAuthorization>
    </AuthProvider>
  );
}

export function CheckAuthorization({ children }: { children: ReactNode }) {
  const router = useRouter();
  const { authorization, updateAuthorization } = useAuthContext();

  useEffect(() => {
    (async function () {
      const auth = await fetchAuthorization();
      if (auth) {
        updateAuthorization(auth);
        router.push("/dashboard");
        return;
      }
    })();
  }, [router, authorization, updateAuthorization]);

  return <>{!authorization && children}</>;
}
