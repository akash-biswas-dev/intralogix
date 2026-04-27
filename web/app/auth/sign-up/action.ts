"use server";

import * as z from 'zod';
import { SignUpErrors, SignUpForm } from "./page";
import { getBaseAxios } from '@/lib/axios';
import { redirect } from 'next/navigation';


const SignUpSchema = z.object({
    email: z
        .string()
        .email("Invalid Email"),
    password: z
        .string()
        .min(8, "Password must be at least 8 characters long")
        .regex(/[A-Z]/, "Password must contain at least one uppercase letter")
        .regex(/[a-z]/, "Password must contain at least one lowercase letter")
        .regex(/\d/, "Password must contain at least one number")
        .regex(/[^A-Za-z0-9]/, "Password must contain at least one special character"),
    confirmPassword: z
        .string()
        .min(8, "Confirm password must be at least 8 characters long")
        .regex(/[A-Z]/, "Confirm password must contain at least one uppercase letter")
        .regex(/[a-z]/, "Confirm password must contain at least one lowercase letter")
        .regex(/\d/, "Confirm password must contain at least one number")
        .regex(/[^A-Za-z0-9]/, "Confirm password must contain at least one special character"),
}).refine((data) => data.password === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
});


export async function signUp(error: SignUpForm, formData: FormData): Promise<SignUpForm> {

    const formFields = {
        email: formData.get('email')?.toString(),
        password: formData.get('password')?.toString(),
        confirmPassword: formData.get('confirmPassword')?.toString(),
    }

    const result = SignUpSchema.safeParse(formFields);

    if (!result.success) {
        const err: SignUpErrors = {};
        const issues = result.error.issues;

        for (const issue of issues) {
            err[issue.path[0] as keyof SignUpErrors] = issue.message;
        }

        return {
            state: {
                email: formFields.email,
            },
            error: err,
        }
    }

    const { email, password } = result.data;


    const axios = getBaseAxios();

    const res = await axios.post('/api/v1/auth/register', {
        email,
        password,
    })

    const { status } = res;

    if (status === 201) {
        redirect('/auth')
    }

    return {
        state: {
            email: formFields.email,
        },
        error: {
            error: 'Something went wrong. Please try again later.'
        },
    }

}