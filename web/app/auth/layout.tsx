import { cookies } from "next/headers"
import { redirect } from "next/navigation";




export default async function AuthLayout({ children }: {
    children: React.ReactNode
}) {

    const cookieStore = await cookies()

    const isUserLoggedIn = false;


    if (isUserLoggedIn) redirect('/dashboard')


    return (
        <div className="w-full min-h-screen relative">{children}</div>
    )
}