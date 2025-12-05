import { AuthContextProvider } from "@/context/AuthContext";
import { ReactNode } from "react";
import { AuthrizationCheck } from "./AuthorizationCheck";

export default function AuthLayout({ children }: { children: ReactNode }) {
  return (
    <AuthContextProvider>
      <AuthrizationCheck>
        <div className="w-full min-h-screen relative">{children}</div>
      </AuthrizationCheck>
    </AuthContextProvider>
  );
}
