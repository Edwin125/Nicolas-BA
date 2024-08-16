/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
        return [
            {
                source: '/:path*',
                destination: 'http://webchatbot:8080/:path*',
            },
        ]
    },
};

export default nextConfig;
