import { fetchAuthorization } from "./action";


export default async function DashboardPage() {

    const authorizaition = await fetchAuthorization();


    console.log(authorizaition);
    return (
        <h1>Dashboard</h1>
    );
}