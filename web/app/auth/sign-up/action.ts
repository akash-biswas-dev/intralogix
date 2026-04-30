"use server";

import { SignUpSchema } from "@/schema/user";
import { redirect } from "next/navigation";
import { SignUpErrors, SignUpForm } from "./page";

import { getAxios } from "@/lib/axios";

export async function signUp(
  _error: SignUpForm,
  formData: FormData,
): Promise<SignUpForm> {
  // Extract fields.
  const formFields = {
    email: formData.get("email")?.toString(),
    firstName: formData.get("firstName")?.toString(),
    lastName: formData.get("lastName")?.toString(),
    password: formData.get("password")?.toString(),
    confirmPassword: formData.get("confirmPassword")?.toString(),
  };

  const result = SignUpSchema.safeParse(formFields);

  if (!result.success) {
    const err: SignUpErrors = {};
    const issues = result.error.issues;

    for (const issue of issues) {
      err[issue.path[0] as keyof SignUpErrors] = issue.message;
    }

    return {
      state: {
        ...formFields,
      },
      error: err,
    };
  }

  const { email, password, firstName, lastName } = result.data;

  const axios = getAxios();

  const res = await axios.post("/api/v1/auth/register", {
    email,
    password,
    firstName,
    lastName,
  });

  const { status } = res;

  if (status === 201) {
    redirect("/auth");
  }

  return {
    state: {
      ...formFields,
    },
    error: {
      error: "Something went wrong. Please try again later.",
    },
  };
}
