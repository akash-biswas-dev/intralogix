import axios from "axios";
import { cookies } from "next/headers";
import { NextResponse } from "next/server";

const SERVER_ADDRESS =
  process.env.SERVER_BACKEND_URL || "http://localhost:9000";

export default async function POST() {
  const cookieStore = await cookies();

  const tokenCookie = cookieStore.get("refresh-token");

  const badResponse = new NextResponse(null, {
    status: 401,
    headers: {
      "X-Message": "The token is invalid or expired.",
    },
  });

  if (!tokenCookie) {
    return badResponse;
  }

  const res = await axios.post(`${SERVER_ADDRESS}/api/v1/refresh-token`, null, {
    headers: {
      "X-Refresh-Token": tokenCookie.value,
    },
    validateStatus: () => true,
  });
  const { data, status } = res;
  if (status !== 201) {
    return badResponse;
  }

  return new Response(data, {
    status: 201,
  });
}
