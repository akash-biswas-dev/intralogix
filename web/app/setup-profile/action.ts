'use server';

import { UpdateProfileState, UserProfileError } from '@/components/UserProfileUpdate';
import { getAxiosWithAuthorization } from '@/lib/axios.server';
import { redirect } from 'next/navigation';
import * as z from 'zod';


export async function updateProfile(perState: UpdateProfileState, formData: FormData) {

    const data = {
        username: formData.get("username")?.toString(),
        firstName: formData.get("firstName")?.toString(),
        lastName: formData.get("lastName")?.toString(),
        dateOfBirth: formData.get("dateOfBirth")?.toString(),
        gender: formData.get("gender")?.toString(),
    };

    const result = UserProfileSchema.safeParse(data);

    if (!result.success) {
        const err: UserProfileError = {};
        const issues = result.error.issues

        for (const issue of issues) {
            err[issue.path[0] as keyof UserProfileError] = issue.message;
        }

        return {
            state: {
                username: data.username,
                firstName: data.firstName,
                lastName: data.lastName,
                dateOfBirth: data.dateOfBirth,
                gender: data.gender,
            }, err: err
        }
    }


    const { username, firstName, lastName, dateOfBirth, gender } = result.data;

    const axios = await getAxiosWithAuthorization();

    const res = await axios.post('/api/v1/users/profile', {
        username,
        firstName,
        lastName,
        dateOfBirth,
        gender,
    });

    const { status } = res;



    redirect('/dashboard');
}


export const UserProfileSchema = z.object({
    username: z
        .string()
        .min(5, "Username is too short")
        .max(15, "Username too long"),
    firstName: z.string(),
    lastName: z.string(),
    dateOfBirth: z.string(),
    gender: z.string(),
});