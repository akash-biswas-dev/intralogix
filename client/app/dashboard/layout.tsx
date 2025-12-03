import { AuthContextProvider } from "@/context/AuthContext";
import { AxiosProvider } from "@/context/AxiosContext";
import { ReactNode } from "react";

export default function DashboardLayout({ children }: { children: ReactNode }) {
  return (
    <AuthContextProvider>
      <AxiosProvider>
        <div>
          Dashboard layout
          {children}
        </div>
      </AxiosProvider>
    </AuthContextProvider>
  );
}
