import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  /* config options here */
  reactCompiler: true,
  output: "export",
  async rewrites() {
    return [
      {
        source: "/backend/:path*",
        destination: "http://localhost:9000/:path*",
      },
    ];
  },
};

export default nextConfig;
