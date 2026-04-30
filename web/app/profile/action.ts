"use server";

import {
  UpdateProfileState,
  UserProfileError,
} from "@/components/profile/UserProfileUpdate";
import { getAxiosWithAuthorization } from "@/lib/axios.server";
import { SESSION, SETUP_PROFILE_SESSION } from "@/lib/constants";
import { validateUserProfile } from "@/schema/user";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export async function setUpProfile(
  pre: UpdateProfileState,
  formData: FormData,
): Promise<UpdateProfileState> {
  const axios = await getAxiosWithAuthorization();

  const { fields, err } = validateUserProfile(formData);

  console.log(fields);

  if (err && !fields) {
    return {
      state: fields,
      err: err,
    };
  }

  // Make request to update the profile.
  const res = await axios.put("/api/v1/users/profile", fields);

  const { status, data } = res;

  // If failed then return the error.
  if (status !== 204) {
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

  const cookieStore = await cookies();

  const refreshRes = await axios.get("/api/v1/auth/refresh-authorization");

  cookieStore.delete({
    name: SETUP_PROFILE_SESSION,
    path: "/setup-profile",
  });

  //   If failed to refresh the token.
  if (refreshRes.status !== 200) {
    redirect("/auth", "replace");
  }

  const { token, maxAge } = refreshRes.data;
  cookieStore.set(SESSION, token, {
    httpOnly: true,
    maxAge: maxAge,
  });
  redirect("/dashboard", "replace");
}
