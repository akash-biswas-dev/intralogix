"use client";

import { Button } from "@/components/ui/button";
import { cn } from "@/lib/utils";
import { Activity, LayoutDashboard, Plus, User } from "lucide-react";
import Link from "next/link";
import { usePathname } from "next/navigation";

interface Workspace {
  id: string;
  name: string;
  initials: string;
  role: "Owner" | "Admin" | "Member";
  color: string;
}

interface SidebarProps {
  workspaces?: Workspace[];
}

const defaultWorkspaces: Workspace[] = [
  {
    id: "1",
    name: "Acme Corp",
    initials: "AC",
    role: "Owner",
    color: "bg-green-100 text-green-700",
  },
  {
    id: "2",
    name: "Retail Hub",
    initials: "RH",
    role: "Admin",
    color: "bg-blue-100 text-blue-700",
  },
  {
    id: "3",
    name: "Side Project",
    initials: "SP",
    role: "Member",
    color: "bg-pink-100 text-pink-700",
  },
];

const homeNav = [
  { label: "Dashboard", href: "/dashboard", icon: LayoutDashboard },
  { label: "Profile", href: "/profile", icon: User },
  { label: "Activity", href: "/activity", icon: Activity },
];

const roleBadgeVariant: Record<string, string> = {
  Owner: "bg-green-50 text-green-700 border-green-200",
  Admin: "bg-blue-50 text-blue-700 border-blue-200",
  Member: "bg-muted text-muted-foreground border-border",
};

export function Sidebar({ workspaces = defaultWorkspaces }: SidebarProps) {
  const pathname = usePathname();

  return (
    <aside className="w-56 shrink-0 border-r border-border bg-background flex flex-col gap-5 py-4 px-2.5 overflow-y-auto">
      {/* Home section */}
      <div>
        <p className="text-[10px] font-medium text-muted-foreground uppercase tracking-widest px-2 mb-2">
          Home
        </p>
        <div className="flex flex-col gap-0.5">
          {homeNav.map(({ label, href, icon: Icon }) => (
            <Link key={href} href={href}>
              <Button
                variant="ghost"
                size="sm"
                className={cn(
                  "w-full justify-start gap-2 text-muted-foreground font-normal h-8 px-2",
                  pathname === href && "bg-accent text-foreground font-medium",
                )}
              >
                <Icon className="h-3.5 w-3.5 shrink-0" />
                {label}
              </Button>
            </Link>
          ))}
        </div>
      </div>

      {/* Workspaces section */}
      <div>
        <p className="text-[10px] font-medium text-muted-foreground uppercase tracking-widest px-2 mb-2">
          Workspaces
        </p>
        <div className="flex flex-col gap-0.5">
          {workspaces.map((ws) => (
            <Link key={ws.id} href={`/dashboard/workspace/${ws.id}`}>
              <Button
                variant="ghost"
                size="sm"
                className={cn(
                  "w-full justify-start gap-2 text-muted-foreground font-normal h-9 px-2",
                  pathname.includes(ws.id) &&
                    "bg-accent text-foreground font-medium",
                )}
              >
                <div
                  className={cn(
                    "w-5 h-5 rounded text-[10px] font-semibold flex items-center justify-center shrink-0",
                    ws.color,
                  )}
                >
                  {ws.initials}
                </div>
                <span className="truncate text-xs">{ws.name}</span>
                <span
                  className={cn(
                    "ml-auto text-[9px] border rounded px-1.5 py-0.5 font-medium shrink-0",
                    roleBadgeVariant[ws.role],
                  )}
                >
                  {ws.role}
                </span>
              </Button>
            </Link>
          ))}

          {/* New workspace */}
          <Button
            variant="ghost"
            size="sm"
            className="w-full justify-start gap-2 text-muted-foreground font-normal h-8 px-2 mt-1"
          >
            <Plus className="h-3.5 w-3.5 shrink-0" />
            <span className="text-xs">New workspace</span>
          </Button>
        </div>
      </div>
    </aside>
  );
}
