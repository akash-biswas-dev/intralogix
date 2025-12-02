import { NextRequest } from "next/server";

export default async function proxy(req: NextRequest) {
  const cookie = req.cookies.get("access-token");
  if (!cookie || cookie.value === "") {
    return;
  }
  req.headers.set("X-Access-Token", cookie.value);
}

export const config = {
  matcher: ["/dashboard/:path*"],
};
