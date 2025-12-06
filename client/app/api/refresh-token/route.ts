import axios from "axios";
import { cookies } from "next/headers";
import { NextResponse } from "next/server";

const SERVER_ADDRESS =
  process.env.SERVER_BACKEND_URL || "http://localhost:9000";

export async function POST() {
  console.log("Requesting....");
  const cookieStore = await cookies();

  const tokenCookie = cookieStore.get("refresh-token");

  const badResponse = new NextResponse(null, {
    status: 401,
    headers: {
      "X-Message": "The token is invalid or expired.",
    },
  });

  if (!tokenCookie) {
    console.error("No token found!");
    return badResponse;
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
  const { data, status } = res;
  if (status === 401) {
    cookieStore.delete("refresh-token");
  }
  if (status === 201) {
    const body = {
      authorization: data.token,
      isTemporary: false,
    };
    return new Response(JSON.stringify(body), {
      status: 201,
    });
  }

  if (status === 307) {
    const body = {
      authorization: data.token,
      isTemporary: true,
    };
    return new Response(JSON.stringify(body), {
      status: 201,
    });
  }

  return badResponse;
}
