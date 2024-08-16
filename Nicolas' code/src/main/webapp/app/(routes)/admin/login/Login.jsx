import {useState} from 'react';
import {useRouter} from 'next/navigation';
import axios from "axios";
import {login} from "@/app/lib/auth";

const Login = () => {
    const [password, setPassword] = useState('');
    const router = useRouter();

    const handleSubmit = async (e) => {
        e.preventDefault();
        axios.post("/request/admin/login?password=" + password)
            .then(res => {
                login(res)
                router.push('/admin/dashboard/analyse');
            })
            .catch(e => {
                alert("Login failed!")
                console.log(e)
            })
    };

    return (
        <div className="min-h-screen flex items-center justify-center">
            <form onSubmit={handleSubmit} className="bg-white p-6 rounded shadow-md">
                <h1 className="text-2xl mb-4">Admin Login</h1>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="border p-2 mb-4 w-full"
                    placeholder="Enter your password"
                />
                <div>
                    <button type="submit" className="bg-blue-500 text-white py-2 px-4 rounded">
                        Login
                    </button>
                </div>
            </form>
        </div>
    );
};

export default Login;
