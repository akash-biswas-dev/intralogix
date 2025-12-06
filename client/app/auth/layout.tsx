import { ReactNode } from "react";
import { isUserAuthorized } from "./action";
import { redirect } from "next/navigation";

export default async function AuthLayout({ children }: { children: ReactNode }) {

  const authorization = await isUserAuthorized();

  if (authorization) {
    if (authorization.isTemporary) {
      redirect("/update-profile")
    } else {
      redirect("/")
    }
  }

  return (
        <div className="w-full min-h-screen relative">{children}</div>
  );
}
