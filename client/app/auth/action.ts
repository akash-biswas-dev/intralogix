"use server";
import { cookies } from "next/headers";
import * as z from "zod";
import axios, { REFRESH_TOKEN, ACCESS_TOKEN, post } from "@/lib/axios-config";
import { redirect } from "next/navigation";

const PasswordValidation = z
  .string()
  .min(8, { error: "Password Is to short" })
  .max(15, { error: "Password is too long" });

const UserCredentials = z.object({
  usernameOrEmail: z
    .string()
    .min(5, { error: "Username or email min length 5." }),
  password: PasswordValidation,
});

export type LoginFormState = {
  message?: string;
  fields?: {
    usernameOrEmail?: string;
    password?: string;
  };
};
export async function login(
  _previousState: LoginFormState,
  formdata: FormData,
): Promise<LoginFormState> {
  const result = UserCredentials.safeParse({
    usernameOrEmail: formdata.get("usernameOrEmail"),
    password: formdata.get("password"),
  });

  if (!result.success) {
    const err = z.treeifyError(result.error);
    const errors: LoginFormState = {
      fields: {
        usernameOrEmail:
          err.properties?.usernameOrEmail && "Enter a valid username or email.",
        password:
          err.properties?.password && "Password length min 8 and max 15.",
      },
    };
    return errors;
  }

  const rememberMe = formdata.get("rememberMe") ? true : false;

  const { usernameOrEmail, password } = result.data;
  try {
    const res = await post<
      {
        accessToken: string;
        refreshToken: null | string;
      },
      {
        usernameOrEmail: string;
        password: string;
      }
    >(
      "/api/v1/auth",
      {
        usernameOrEmail,
        password,
      },
      {
        params: {
          rememberMe,
        },
      },
    );
    const { status, data } = res;

    if (status !== 201) {
      return { message: "Something went wrong" };
    }

    const cookieStore = await cookies();
    const { accessToken, refreshToken } = data;
    cookieStore.set(ACCESS_TOKEN, accessToken);
    if (rememberMe && refreshToken !== null) {
      cookieStore.set(REFRESH_TOKEN, refreshToken);
    }
    redirect("/dashboard");
  } catch (err) {
    if (err instanceof Error) {
      return {
        message: err.message,
      };
    }
    console.error(String(err));
  }
  return {};
}

const NewUserForm = z.object({
  username: z.string().min(5, { error: "The username min length must be 5." }),
  email: z.email({ error: "Invalid email address." }),
  password: PasswordValidation,
});

export type NewUserFormState = {
  message?: string;
  fields?: {
    email?: string;
    password?: string;
    username?: string;
  };
};
export async function signUp(
  state: NewUserFormState,
  formdata: FormData,
): Promise<NewUserFormState> {
  const result = NewUserForm.safeParse({
    email: formdata.get("email"),
    username: formdata.get("username"),
    password: formdata.get("password"),
  });

  console.log(formdata.get("dateOfBirth"));

  if (result.error) {
    return {};
  }

  const newUser = result.data;

  const res = await axios.post("/api/v1/auth/register", newUser);

  const { status } = res;
  if (status === 201) {
    redirect("/auth");
  }
  return {};
}
