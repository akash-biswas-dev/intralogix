import { AuthContextProvider } from "@/context/AuthContext";
import { AxiosProvider } from "@/context/AxiosContext";
import { ReactNode } from "react";

export default function UserProfileLayout({
  children,
}: {
  children: ReactNode;
}) {
  return (
    <AuthContextProvider>
      <AxiosProvider>{children}</AxiosProvider>
    </AuthContextProvider>
  );
}
