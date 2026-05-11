"use server";

import { cookies } from "next/headers";
import { SESSION } from "@/lib/constants";

import axios from "axios";
const API = process.env.SERVER_URL;

export async function getAxiosWithAuthorization() {
  const cookieStore = await cookies();
  const cookie = cookieStore.get(SESSION);

  const instance = axios.create({
    baseURL: API,
    validateStatus: () => true,
  });

  if (cookie?.value) {
    instance.defaults.headers.Authorization = `Bearer ${cookie.value}`;
  }

  return instance;
}
