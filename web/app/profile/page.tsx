import UserProfileUpdate from "@/app/profile/UserProfileUpdate";
import { getAxiosWithAuthorization } from "@/lib/axios.server";
import { redirect } from "next/navigation";
import { setUpProfile } from "./action";

export default async function SetupProfile() {
  const axios = await getAxiosWithAuthorization();

  const res = await axios.get("/api/v1/users/profile");

  const { status, data } = res;

  console.log(status);

  return (
    <div className="min-h-screen">
      <UserProfileUpdate initialData={data} action={setUpProfile} />
    </div>
  );
}
