"use server";

import { cookies } from "next/headers";
import { redirect } from "next/navigation";

import { getAxios } from "@/lib/axios";
import * as z from "zod";

import { SESSION } from "@/lib/constants";
import { LoginFormError } from "./page";

const UserCredential = z.object({
  emailOrUsername: z.string().min(5, "Invalid email or Username"),
  password: z
    .string()
    .min(8, "Password is too short.")
    .max(15, "Password is too long."),
});

export async function login(prev: LoginFormError, formData: FormData) {
  const credentials = {
    emailOrUsername: formData.get("emailOrUsername"),
    password: formData.get("password"),
    rememberMe: formData.get("rememberMe") ? "true" : "false",
  };

  const result = UserCredential.safeParse({
    emailOrUsername: credentials.emailOrUsername,
    password: credentials.password,
  });

  if (!result.success) {
    const err: LoginFormError = {};

    const issues = result.error.issues;

    for (const issue of issues) {
      err[issue.path[0] as keyof LoginFormError] = issue.message;
    }

    return err;
  }

  // Send the credentials to the server.

  const { emailOrUsername, password } = result.data;

  const axios = getAxios();

  const res = await axios.post(
    "/api/v1/auth",
    {
      emailOrUsername,
      password,
    },
    {
      params: {
        rememberMe: credentials.rememberMe,
      },
      maxRedirects: 0,
    },
  );

  const { status, data } = res;

  const cookieStore = await cookies();

  // TODO: handle the response if user newly created.

  if (status !== 201 && status !== 307) {
    if (data?.error) {
      return {
        error: data.error,
      };
    }

    return {
      error: "Service unavailable.",
    };
  }

  cookieStore.set(SESSION, data.token, {
    httpOnly: true,
    maxAge: data.maxAge,
    path: "/",
  });

  const url = status === 307 ? "/profile" : "/dashboard";

  // If the user isn't exist or some error occurred at server.

  redirect(url);
}

// Logout user.

export async function logout() {
  const cookieStore = await cookies();

  cookieStore.delete(SESSION);
  redirect("/auth");
}
