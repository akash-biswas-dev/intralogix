"use server";


import { cookies } from "next/headers";
import { redirect } from "next/navigation";

import * as z from 'zod'

import { SETUP_PROFILE_SESSION, SESSION } from "@/lib/constants";
import { getBaseAxios } from "@/lib/axios";
import { LoginFormError } from "./page";





const UserCredential = z.object({
    emailOrUsername: z.string().min(5, "Invalid email or Username"),
    password: z
        .string()
        .min(8, "Password is too short.")
        .max(15, "Password is too long."),
});

export async function login(prev: LoginFormError, formData: FormData) {

    const credentials = {
        emailOrUsername: formData.get('emailOrUsername'),
        password: formData.get('password'),
        rememberMe: formData.get('rememberMe') ? 'true' : 'false'
    }

    const parsedObject = UserCredential.safeParse({
        emailOrUsername: credentials.emailOrUsername,
        password: credentials.password,
    });

    if (!parsedObject.success) {
        const err: LoginFormError = {};

        const issues = parsedObject.error.issues;

        for (const issue of issues) {
            err[issue.path[0] as keyof LoginFormError] = issue.message;
        }

        return err;
    }

    // Send the credentials to the server.

    const axios = getBaseAxios()

    const res = await axios.post('/api/v1/auth', {
        emailOrUsername: credentials.emailOrUsername,
        password: credentials.password,
    },
        {
            params: {
                rememberMe: credentials.rememberMe
            },
            maxRedirects: 0
        }
    );



    const { status, data } = res;


    const cookieStore = await cookies();

    // TODO: handle the response if user newly created.

    if (status === 307) {
        cookieStore.set(SETUP_PROFILE_SESSION, data.token, {
            httpOnly: true,
            maxAge: data.maxAge,
            path: '/setup-profile'
        })
        redirect('/setup-profile', 'replace')
    }

    console.log("Setup Profile Token", data);


    // If the user isn't exist or some error occurred at server.
    if (status !== 201) {
        // User not successfully logged in.

        if (data?.err) {
            console.error(data.err)
            return {
                error: data.err
            }
        }
        console.error('Some internal error occurred.')

        return {
            error: 'Service unavailable.'
        }
    }

    const day = 24 * 60 * 60;

    cookieStore.set(SESSION, data.data.token, {
        httpOnly: true,
        maxAge: data.data.expiration // Age in seconds.
    });

    redirect('/dashboard')
}

// Logout user.

export async function logout() {

    const cookieStore = await cookies()

    cookieStore.delete(SETUP_PROFILE_SESSION);
    cookieStore.delete(SESSION);
    redirect('/auth');
}