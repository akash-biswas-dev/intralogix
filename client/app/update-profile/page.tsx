"use client";

import UserProfileUpdate from "@/components/UserProfileUpdate";
import useAuthContext from "@/context/AuthContext";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function UpdateProfile() {
  // This route is only for the first time when user not enabled.
  const router = useRouter();
  const { temporaryAuth } = useAuthContext();

  useEffect(() => {
    if (!temporaryAuth) {
      router.push("/auth");
    }
  }, [temporaryAuth, router]);

  return <>{temporaryAuth && <UserProfileUpdate />}</>;
}
