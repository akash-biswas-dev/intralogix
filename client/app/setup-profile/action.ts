"use client";

import { SERVER_ADDRESS } from "@/context/AuthContext";
import axios from "axios";

export async function fetchProfileStatus(): Promise<boolean | undefined> {
  const res = await axios.get(`${SERVER_ADDRESS}/api/v1/auth/setup-profile`, {
    validateStatus: () => true,
    withCredentials: true,
  });

  const { status, data } = res;

  if (status === 200) return true;

  if (status === 400) {
    console.error(data);
  }

  return false;
}
