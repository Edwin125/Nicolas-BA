"use client"
import NavigationItem from "@/app/(routes)/admin/dashboard/NavigationItem";
import {useRouter} from "next/navigation";
import {useEffect, useState} from "react";
import {getToken, logout} from "@/app/lib/auth";
import LogoutLogo from "@/app/assets/LogoutLogo";

const navigationList = [
    {
        href: "/admin/dashboard/analyse",
        display: "Analyse"
    },
    {
        href: "/admin/dashboard/ids",
        display: "Active IDs"
    },
]

export default function Layout({children}) {
    const router = useRouter();

    const [token, setToken] = useState("")

    useEffect(() => {
        const t = getToken()

        if (!t) {
            router.push('/admin/login');
        }

        setToken(t);
    }, []);

    const navigationItems = navigationList.map((nav, idx) => {
        return (<NavigationItem
            key={idx}
            href={nav.href}
            display={nav.display}
        />)
    })

    const handleLogout = () => {
        logout()
        router.replace("../../admin/login")
    }

    return token && (
        <div>
            <nav className="bg-gray-800 p-4 text-white">
                <ul className="flex space-x-4">
                    {navigationItems}
                    <li className="cursor-pointer flex flex-row items-center" onClick={handleLogout}>
                        <div>
                            <LogoutLogo/>
                        </div>
                    </li>
                </ul>
            </nav>

            <div className="p-4">
                {children}
            </div>
        </div>
    );
}
