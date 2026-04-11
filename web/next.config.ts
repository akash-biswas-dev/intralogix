import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
  reactCompiler: true,
  output: "export",
  trailingSlash: true,
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: "http://localhost:9000/api/:path*",
      },
    ];
  },
};

export default nextConfig;
