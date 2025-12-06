"use client";
import useAuthContext from "@/context/AuthContext";
import { useEffect } from "react";

export default function UserProfile() {
  const { authorization } = useAuthContext();

  useEffect(() => {}, []);

  return <>{authorization && <div>User profile</div>}</>;
}
