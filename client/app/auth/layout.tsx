"use client";

import { ReactNode } from "react";
import useAuthContext, { AuthContextProvider } from "@/context/AuthContext";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function AuthLayout({ children }: { children: ReactNode }) {
  return (
    <AuthContextProvider>
      <div className="w-full min-h-screen relative">{children}</div>
    </AuthContextProvider>
  );
}

export function CheckAuthorization({ children }: { children: ReactNode }) {
  const router = useRouter();
  const { authorization } = useAuthContext();

  useEffect(() => {
    if (authorization) {
      router.push("/home");
    }
  }, [router, authorization]);

  return <>{!authorization && { children }}</>;
}
