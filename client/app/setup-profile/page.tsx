"use client";
import UserProfileUpdate from "@/components/UserProfileUpdate";
import { SERVER_ADDRESS } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";
import { FormEvent, use, useEffect } from "react";

const profileStatusPromise = fetchProfileStatus();
export default function UpdateProfile() {
  const router = useRouter();
  // If users profile already setup then isAlreadySetup is
  const isProfileNotCreated = use(profileStatusPromise);

  useEffect(() => {
    if (!isProfileNotCreated) router.push("/auth");
  }, [isProfileNotCreated, router]);

  const onSubmit = (eve: FormEvent<HTMLFormElement>) => {
    eve.preventDefault();
    const formData = new FormData(eve.currentTarget);

    const userData = {
      firstName: formData.get("firstName"),
      lastName: formData.get("lastName"),
      dateOfBirth: formData.get("dateOfBirth"),
      gender: formData.get("gender"),
    };

    console.log(userData);
  };

  return (
    <>{isProfileNotCreated && <UserProfileUpdate onSubmit={onSubmit} />}</>
  );
}

export async function fetchProfileStatus(): Promise<boolean | undefined> {
  const res = await axios.get(`${SERVER_ADDRESS}/api/v1/auth/setup-profile`, {
    validateStatus: () => true,
    withCredentials: true,
  });

  const { status, data } = res;

  if (status === 200) return true;

  if (status === 400) {
    console.error(data);
  }

  return false;
}
