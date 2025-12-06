"use client";
import UserProfileUpdate from "@/components/UserProfileUpdate";
import useAuthContext from "@/context/AuthContext";
export default function UserUpdate() {
  // At this route update the already enabled user.


  const { authorization } = useAuthContext();

  

  return (
    <>{
      authorization && 
      <UserProfileUpdate authorization={authorization} />
    }
    </>
  );
}
