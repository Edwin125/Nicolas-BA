import axios from "axios";
import Button from "@/app/_components/Button";

axios.defaults.baseURL = "http://localhost:8080"

export default async function Page() {

    return <Button />
}