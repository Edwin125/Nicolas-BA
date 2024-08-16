/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        "./pages/**/*.{js,ts,jsx,tsx,mdx}",
        "./components/**/*.{js,ts,jsx,tsx,mdx}",
        "./app/**/*.{js,ts,jsx,tsx,mdx}",
    ],
    theme: {
        extend: {
            backgroundImage: {
                "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
                "gradient-conic":
                    "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
            },
            animation: {
                blink: 'blink 1.5s infinite both'
            },
            keyframes: {
                blink: {
                    '0%, 100%': {opacity: '.1'},
                    '20%': {opacity: '1'}
                }
            }
        },
        fontFamily: {
            sans: ['__Inter_aaf875', '__Inter_Fallback_aaf875',]
        }
    },
    plugins: [],
};