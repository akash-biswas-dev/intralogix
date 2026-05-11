import { getAxiosWithAuthorization } from "@/lib/axios.server";
import { redirect } from "next/navigation";

export default async function AuthLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const axios = await getAxiosWithAuthorization();

  const res = await axios.get("/api/v1/users");

  if (res.status === 200) {
    redirect("/dashboard");
  }

  return <div className="w-full min-h-screen relative">{children}</div>;
}
