'use server';

import { getBaseAxios } from "./axios";
import { cookies } from "next/headers";

const axios = getBaseAxios();

export async function getAxiosWithAuthorization() {
    return getAxiosWithCookie('sessionid');
}

export async function getAxiosWithCookie(name: string) {

    const instance = axios;
    const cookieStore = await cookies();

    const cookie = cookieStore.get(name);


    if (cookie && cookie.value) {
        instance.defaults.headers.Authorization = `Bearer ${cookie.value}`
    }

    return instance;
}
