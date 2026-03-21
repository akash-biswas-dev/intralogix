"use client";
import UserProfileUpdate, {
  UserProfileError,
} from "@/components/UserProfileUpdate";
import { SERVER_ADDRESS } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";
import { FormEvent, useEffect, useState } from "react";
import * as z from "zod";

export default function UpdateProfile() {
  const router = useRouter();
  // If users profile already setup then isAlreadySetup is

  const [isProfileNotCreated, setIsProfileNotCreated] = useState(true);

  const [errors, setErrors] = useState<UserProfileError>({});

  useEffect(() => {
    const isProfileAuthenticated = async () => {
      const res = await axios.get(
        `${SERVER_ADDRESS}/api/v1/auth/setup-profile`,
        {
          validateStatus: () => true,
          withCredentials: true,
        },
      );

      const { status, data } = res;

      if (status === 200) {
        setIsProfileNotCreated(true);
        return;
      }

      if (data?.data?.error) {
        console.error(data.data.error);
      }
      router.push("/dashboard");
    };

    // isProfileAuthenticated();
  }, [isProfileNotCreated, router]);

  const onSubmit = async (eve: FormEvent<HTMLFormElement>) => {
    eve.preventDefault();
    const formData = new FormData(eve.currentTarget);

    const userData = {
      username: formData.get("username"),
      firstName: formData.get("firstName"),
      lastName: formData.get("lastName"),
      dateOfBirth: formData.get("dateOfBirth"),
      gender: formData.get("gender"),
    };

    // TODO: Add validation checks.

    const res = await axios.post(
      `${SERVER_ADDRESS}/api/v1/auth/setup-profile`,
      {
        ...userData,
      },
      {
        validateStatus: () => true,
      },
    );

    // TODO: Add validation.

    const { status, data } = res;

    if (status === 202) {
      // Successfully update the profile.
      router.push("/dashboard");
    }
  };

  return (
    <div className="min-h-screen">
      {isProfileNotCreated && (
        <UserProfileUpdate
          onSubmit={onSubmit}
          errors={errors}
          setErrors={setErrors}
        />
      )}
    </div>
  );
}
