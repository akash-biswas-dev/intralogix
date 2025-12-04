"use server";
import axios from "axios";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

const SERVER_ADDRESS =
  process.env.SERVER_BACKEND_URL || "http://localhost:9000";

export async function login({
  usernameOrEmail,
  password,
  rememberMe,
}: {
  usernameOrEmail: string;
  password: string;
  rememberMe: boolean;
}) {
  const res = await axios.post(
    `${SERVER_ADDRESS}/api/v1/auth`,
    {
      usernameOrEmail,
      password,
    },
    {
      params: {
        rememberMe,
      },
      validateStatus: function () {
        return true;
      },
    },
  );

  const { status, data } = res;
  if (status !== 201) {
    return null;
  }
  const cookieStore = await cookies();
  const { token, age } = data;

  cookieStore.set("refresh-token", token, {
    path: "/api/refresh-token",
    httpOnly: true,
    maxAge: age,
  });

  redirect("/dashboard");
}
