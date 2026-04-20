import { cookies } from "next/headers";
import { redirect } from "next/navigation";


export async function fetchAuthorization() {
    const cookieStore = await cookies()
    const cookie = cookieStore.get("sessionid");

    if (!cookie || !cookie.value) {
        redirect("/auth")
    }

    // Todo : Fetch user from server and return the user.


    return {
        firstName: "Akash",
        lastName: "Biswas",
        username: "akash"
    }
}