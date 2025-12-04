"use client";

import { AuthContextProvider } from "@/context/AuthContext";
import { AxiosProvider } from "@/context/AxiosContext";
import { LoadingContextProvider } from "@/context/LoadingContext";
import { ReactNode } from "react";

export default function UserProfileLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <LoadingContextProvider>
      <AuthContextProvider>
        <AxiosProvider>{children}</AxiosProvider>
      </AuthContextProvider>
    </LoadingContextProvider>
  );
}
