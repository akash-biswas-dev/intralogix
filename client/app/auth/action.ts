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
    httpOnly: true,
    maxAge: age,
  });

  redirect("/home");
}

export async function isUserAuthorized(): Promise<Authorization | null> {
  const cookieStore = await cookies();

  const tokenCookie = cookieStore.get("refresh-token");

  if (!tokenCookie) {
    console.error("No token found!");
    return null;
  }

  const res = await axios.post(
    `${SERVER_ADDRESS}/api/v1/auth/refresh-token`,
    null,
    {
      headers: {
        "X-Refresh-Token": tokenCookie.value,
      },
      validateStatus: () => true,
    },
  );

  if (res.status === 307) {
    return {
      authorization: res.data.token,
      isTemporary: true,
    };
  }

  if (res.status === 201) {
    return {
      authorization: res.data.token,
      isTemporary: false,
    };
  }

  return null;
}

export type Authorization = {
  authorization: string;
  isTemporary: boolean;
};
