"use client";
import UserProfileUpdate from "@/components/UserProfileUpdate";
import { useRouter } from "next/navigation";
import { FormEvent, use, useEffect } from "react";
import { fetchProfileStatus } from "./action";

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
