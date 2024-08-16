"use client"
import Link from "next/link";
import {usePathname} from 'next/navigation';
import clsx from "clsx";

const NavigationItem = ({href, display}) => {
    const pathname = usePathname()

    return (
        <li className="grow">
            <Link
                href={href}
                className={clsx(
                    'flex h-[48px] grow items-center justify-center gap-2 rounded-md bg-gray-50 p-3 ' +
                    'text-sm font-medium hover:bg-sky-100 text-black justify' +
                    'md:flex-none  md:p-2 md:px-3',
                    {
                        'bg-sky-100 text-blue-600': pathname === href,
                    },
                )}
            >
                {display}
            </Link>
        </li>
    )
}

export default NavigationItem;
