import UserProfileUpdate from "@/components/UserProfileUpdate";
import { getAxiosWithCookie } from "@/lib/axios.server";
import { redirect } from "next/navigation";
import { SETUP_PROFILE_SESSION } from "@/lib/constants";

export default async function SetupProfile() {
    const axios = await getAxiosWithCookie(SETUP_PROFILE_SESSION);

    const res = await axios.post('/api/v1/auth/setup-profile');
    const { status, data } = res;

    if (status !== 200) {
        redirect('/auth');
    }

    if (data?.data?.isCompleted) {
        redirect('/dashboard');
    }

    return <UserProfileUpdate />;
}