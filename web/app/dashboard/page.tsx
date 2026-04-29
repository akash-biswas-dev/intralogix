import { getAxiosWithAuthorization } from "@/lib/axios.server";
import { redirect } from "next/navigation";

export default async function DashboardPage() {
  const axios = await getAxiosWithAuthorization();

  const res = await axios.get("/api/v1/users");

  const { status, data } = res;

  if (status !== 200) {
    redirect("/auth");
  }

  console.log(data);

  return <h1>Dashboard</h1>;
}
