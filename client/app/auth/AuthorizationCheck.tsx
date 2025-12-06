"use client";
import useAuthContext from "@/context/AuthContext";
import { useRouter } from "next/navigation";
import { ReactNode, useEffect } from "react";

export const AuthrizationCheck = ({ children }: { children: ReactNode }) => {
  const router = useRouter();
  const { authorization } = useAuthContext();

  useEffect(() => {
    if (authorization) {
      const { isTemporary } = authorization;
      if (isTemporary) {
        router.push("/update-profile");
      } else {
        router.push("/dashboard");
      }
    }
  });

  return <>{!authorization && <>{children}</>}</>;
};
