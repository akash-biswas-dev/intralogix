import { UserProfile, UserProfileError } from "@/components/UserProfileUpdate";
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
