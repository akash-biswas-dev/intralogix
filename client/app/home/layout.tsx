import { AuthContextProvider } from "@/context/AuthContext";
import { AxiosProvider } from "@/context/AxiosContext";
import { LoadingContextProvider } from "@/context/LoadingContext";
import { ReactNode } from "react";
import { isUserAuthorized } from "../auth/action";
import { redirect } from "next/navigation";

export default async function DashboardLayout({
  children,
}: {
  children: ReactNode;
}) {
  const authorization = await isUserAuthorized();

  if (authorization) {
    if (authorization.isTemporary) {
     redirect("/update-profile")
   } 
  } else{
    redirect("/auth")
  }

  return (
    <LoadingContextProvider>
      <AuthContextProvider initialAuth={authorization.authorization}>
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
