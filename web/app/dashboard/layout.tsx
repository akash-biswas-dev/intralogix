"use client";

import { AuthProvider } from "@/context/AuthContext";
import { AxiosProvider } from "@/context/AxiosContext";
import { LoadingContextProvider } from "@/context/LoadingContext";
import { ReactNode } from "react";

export default function DashboardLayout({ children }: { children: ReactNode }) {
  return (
    <LoadingContextProvider>
      <AuthProvider>
        <AxiosProvider>
          <div>
            Dashboard layout
            {children}
          </div>
        </AxiosProvider>
      </AuthProvider>
    </LoadingContextProvider>
  );
}
