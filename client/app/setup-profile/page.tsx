"use client";
import UserProfileUpdate from "@/components/UserProfileUpdate";
import { SERVER_ADDRESS } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";
import { use, useEffect } from "react";

const isProfileNotCreated = fetchProfileStatus();
export default function UpdateProfile() {
  const router = useRouter();
  // If users profile already setup then isAlreadySetup is
  const isProfileCreated = use(isProfileNotCreated);

  useEffect(() => {
    if (!isProfileCreated) router.push("/auth");
  }, [isProfileCreated, router]);

  return <>{isProfileCreated && <UserProfileUpdate />}</>;
}

export async function fetchProfileStatus(): Promise<boolean | undefined> {
  const res = await axios.get(`${SERVER_ADDRESS}/api/v1/auth/setup-profile`, {
    validateStatus: () => true,
  });

  const { status, data } = res;

  if (status === 200) return false;

  if (status === 401) {
    console.error(data);
  }

  return true;
}
