"use client";
import UserProfileUpdate, {
    UserProfileError,
} from "@/components/UserProfileUpdate";
import { useRouter } from "next/navigation";
import { FormEvent, use, useEffect, useState } from "react";
import axios from "axios";
import { SERVER_ADDRESS } from "@/context/AuthContext";
import { Divide } from "lucide-react";

export default function UpdateProfile() {
    const router = useRouter();
    // If users profile already setup then isAlreadySetup is

    const [isProfileNotCreated, setIsProfileNotCreated] = useState(true);

    useEffect(() => {
        const isProfileAuthenticated = async () => {
            const res = await axios.get(
                `${SERVER_ADDRESS}/api/v1/auth/setup-profile`,
                {
                    validateStatus: () => true,
                    withCredentials: true,
                },
            );

            const { status, data } = res;

            if (status === 200) setIsProfileNotCreated(true);

            if (data?.data?.error) {
                console.error(data.data.error);
            }
            router.push("/dashboard");
        };
    }, [isProfileNotCreated, router]);

    const onSubmit = (eve: FormEvent<HTMLFormElement>) => {
        eve.preventDefault();
        const formData = new FormData(eve.currentTarget);

        const userData = {
            firstName: formData.get("firstName"),
            lastName: formData.get("lastName"),
            dateOfBirth: formData.get("dateOfBirth"),
            gender: formData.get("gender"),
        };

        console.log(userData);
    };

    const [errors, setErrors] = useState<UserProfileError>({});

    return (
        <div className="min-h-screen">
            {isProfileNotCreated && <UserProfileUpdate onSubmit={onSubmit} />}
        </div>
    );
}
