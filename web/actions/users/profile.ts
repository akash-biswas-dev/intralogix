"use server";

import {
  UpdateProfileState,
  UserProfileError,
} from "@/app/profile/UserProfileUpdate";
import { getAxiosWithAuthorization } from "@/lib/axios.server";
import { validateUserProfile } from "@/schema/user";
import { redirect } from "next/navigation";

export async function updateProfile(
  _perState: UpdateProfileState,
  formData: FormData,
): Promise<UpdateProfileState> {
  const { fields, err } = validateUserProfile(formData);

  if (err) {
    return {
      state: fields,
      err: err,
    };
  }

  const axios = await getAxiosWithAuthorization();

  // Make request to update the profile.
  const res = await axios.put("/api/v1/users/profile", fields);

  const { status, data } = res;

  // If failed then return the error.
  if (status !== 201) {
    const err: UserProfileError = {};

    if (data.error) {
      err.error = data.error;
    } else {
      err.error = "Something went wrong. Please try again later.";
    }
    return {
      state: fields,
      err: err,
    };
  }

  redirect("/dashboard/profile");
}
