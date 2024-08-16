"use client"
import {useEffect} from 'react';
import {useRouter} from 'next/navigation';
import {getToken} from '@/app/lib/auth';

const Dashboard = () => {
    const router = useRouter();

    useEffect(() => {
        const token = getToken();

        if (!token) {
            // router.push('/admin/login');
        }
    }, []);

    return (
        <div>
            <div className="p-4">
                hello
            </div>
        </div>
    );
};

export default Dashboard;
