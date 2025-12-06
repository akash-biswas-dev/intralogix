
import UserProfileUpdate from "@/components/UserProfileUpdate";
import { isUserAuthorized } from "../auth/action";
import { redirect } from "next/navigation";

export default async function UpdateProfile() {
  // This route is only for the first time when user not enabled.
  const authorization = await isUserAuthorized();

  if (!authorization) { 
    redirect("/auth")

  } 

  if (!authorization.isTemporary) {
    redirect("/home");
  }

  return <>{<UserProfileUpdate authorization={authorization.authorization} />}</>;
}
