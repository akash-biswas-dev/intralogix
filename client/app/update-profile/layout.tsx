import { AuthContextProvider } from "@/context/AuthContext";
import { ReactNode } from "react";

export default function UpdateProfileLayout({
  children,
}: {
  children: ReactNode;
}) {
  return <AuthContextProvider>{children}</AuthContextProvider>;
}
