import {
  UserProfile,
  UserProfileError,
} from "@/components/profile/UserProfileUpdate";
import * as z from "zod";

export const UserProfileSchema = z.object({
  username: z
    .string()
    .min(5, "Username is too short")
    .max(15, "Username too long"),
  firstName: z.string(),
  lastName: z.string(),
  dateOfBirth: z.string(),
  gender: z.string(),
});

export function validateUserProfile(formData: FormData): {
  err?: UserProfileError;
  fields: UserProfile;
} {
  const fields = {
    username: formData.get("username")?.toString(),
    firstName: formData.get("firstName")?.toString(),
    lastName: formData.get("lastName")?.toString(),
    dateOfBirth: formData.get("dateOfBirth")?.toString(),
    gender: formData.get("gender")?.toString(),
  };
  const result = UserProfileSchema.safeParse(fields);

  if (!result.success) {
    const err: UserProfileError = {};
    const issues = result.error.issues;

    for (const issue of issues) {
      err[issue.path[0] as keyof UserProfileError] = issue.message;
    }
    return {
      fields: fields,
      err,
    };
  }

  return {
    fields: fields,
  };
}

export const SignUpSchema = z
  .object({
    email: z.email("Invalid Email"),
    firstName: z.string(),
    lastName: z.string(),
    password: z
      .string()
      .min(8, "Password must be at least 8 characters long")
      .regex(/[A-Z]/, "Password must contain at least one uppercase letter")
      .regex(/[a-z]/, "Password must contain at least one lowercase letter")
      .regex(/\d/, "Password must contain at least one number")
      .regex(
        /[^A-Za-z0-9]/,
        "Password must contain at least one special character",
      ),
    confirmPassword: z
      .string()
      .min(8, "Confirm password must be at least 8 characters long")
      .regex(
        /[A-Z]/,
        "Confirm password must contain at least one uppercase letter",
      )
      .regex(
        /[a-z]/,
        "Confirm password must contain at least one lowercase letter",
      )
      .regex(/\d/, "Confirm password must contain at least one number")
      .regex(
        /[^A-Za-z0-9]/,
        "Confirm password must contain at least one special character",
      ),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });
