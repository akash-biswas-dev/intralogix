"use client";
import { AuthContextProvider } from "@/context/AuthContext";
import { AxiosProvider } from "@/context/AxiosContext";
import { LoadingContextProvider } from "@/context/LoadingContext";
import { ReactNode } from "react";

export default function DashboardLayout({ children }: { children: ReactNode }) {
  return (
    <LoadingContextProvider>
      <AuthContextProvider>
        <AxiosProvider>
          <div>
            Dashboard layout
            {children}
          </div>
        </AxiosProvider>
      </AuthContextProvider>
    </LoadingContextProvider>
  );
}
