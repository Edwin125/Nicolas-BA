import {Inter} from "next/font/google";
import "../globals.css";
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';

const inter = Inter({subsets: ["latin"]});

export const metadata = {
    title: "Chat App",
    description: "Universitätsklinikum Bonn\n",
};

export default function RootLayout({children}) {
    return (
        <html lang="en">
        <body className={inter.className}>{children}</body>
        </html>
    );
}
