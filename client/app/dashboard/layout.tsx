import { ReactNode } from "react";

export default function DashboardLayout({ children }: { children: ReactNode }) {
  return (
    <div>
      Dashboard layout
      {children}
    </div>
  );
}
