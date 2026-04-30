import { TopNavbar } from "@/components/dashboard/top-navbar";
import { Sidebar } from "@/components/dashboard/sidebar";
import { getAxiosWithAuthorization } from "@/lib/axios.server";
import { redirect } from "next/navigation";
import { UserContextProvider } from "@/context/user-context";

export default async function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const axios = await getAxiosWithAuthorization();

  const res = await axios.get("/api/v1/users");

  const { status, data } = res;

  if (status !== 200) {
    redirect("/auth");
  }

  return (
    <div className="flex flex-col h-screen overflow-hidden">
      <UserContextProvider user={data}>
        <TopNavbar />
        <div className="flex flex-1 overflow-hidden">
          <Sidebar />
          <main className="flex-1 overflow-y-auto bg-muted/30">{children}</main>
        </div>
      </UserContextProvider>
    </div>
  );
}
