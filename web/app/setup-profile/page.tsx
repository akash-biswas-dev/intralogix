import UserProfileUpdate from "@/components/UserProfileUpdate";
import { getAxiosWithCookie } from "@/lib/axios.server";
import { redirect } from "next/navigation";
import { SETUP_PROFILE_SESSION } from "@/lib/constants";

export default async function SetupProfile() {
    const axios = await getAxiosWithCookie(SETUP_PROFILE_SESSION);

    const res = await axios.get('/api/v1/users/profile');
    const { status } = res;


    if (status === 200) {
        redirect('/dashboard', 'replace')
    }

    if (status !== 403) {
        redirect('/auth');
    }

    return <div className="min-h-screen">

        <UserProfileUpdate />
    </div>
}