"use client";
import useAuthContext from "@/context/AuthContext";
import { useEffect } from "react";

export default function UserProfile() {
  const { isUserAuthorized } = useAuthContext();

  const isAuthorized = isUserAuthorized();

  useEffect(() => {}, []);

  return <>{isAuthorized && <div>User profile</div>}</>;
}
