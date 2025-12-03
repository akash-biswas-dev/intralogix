import * as z from "zod";

const PasswordValidation = z
  .string()
  .min(8, { error: "Password Is to short" })
  .max(15, { error: "Password is too long" });

export const UserCredentials = z.object({
  usernameOrEmail: z
    .string()
    .min(5, { error: "Username or email min length 5." }),
  password: PasswordValidation,
});

export const NewUserForm = z.object({
  username: z.string().min(5, { error: "The username min length must be 5." }),
  email: z.email({ error: "Invalid email address." }),
  password: PasswordValidation,
});
