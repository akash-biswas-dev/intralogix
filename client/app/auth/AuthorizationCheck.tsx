"use client";
import useAuthContext from "@/context/AuthContext";
import { ReactNode } from "react";

export const AuthrizationCheck = ({ children }: { children: ReactNode }) => {
  const { temporaryAuth, authorization } = useAuthContext();
  const isUserAuthrized = temporaryAuth || authorization ? true : false;
  return <>{!isUserAuthrized && <>{children}</>}</>;
};
