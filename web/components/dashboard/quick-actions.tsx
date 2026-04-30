"use client";

import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";
import {
  Briefcase,
  UserPlus,
  Package,
  ShieldCheck,
  ChevronRight,
} from "lucide-react";

interface QuickAction {
  id: string;
  label: string;
  sub: string;
  icon: React.ReactNode;
  iconBg: string;
  onClick?: () => void;
}

const defaultActions: QuickAction[] = [
  {
    id: "1",
    label: "Create workspace",
    sub: "Set up a new business",
    icon: <Briefcase className="h-3.5 w-3.5" />,
    iconBg: "bg-green-100 text-green-700",
  },
  {
    id: "2",
    label: "Invite a member",
    sub: "Add someone to a workspace",
    icon: <UserPlus className="h-3.5 w-3.5" />,
    iconBg: "bg-blue-100 text-blue-700",
  },
  {
    id: "3",
    label: "Add product",
    sub: "To any workspace",
    icon: <Package className="h-3.5 w-3.5" />,
    iconBg: "bg-amber-100 text-amber-700",
  },
  {
    id: "4",
    label: "Manage roles",
    sub: "Set access permissions",
    icon: <ShieldCheck className="h-3.5 w-3.5" />,
    iconBg: "bg-pink-100 text-pink-700",
  },
];

interface QuickActionsProps {
  actions?: QuickAction[];
}

export function QuickActions({ actions = defaultActions }: QuickActionsProps) {
  return (
    <Card className="border-border shadow-none">
      <CardHeader className="pb-2 px-4 pt-4">
        <CardTitle className="text-sm font-medium">Quick actions</CardTitle>
      </CardHeader>
      <CardContent className="px-2 pb-3">
        <div className="flex flex-col">
          {actions.map((action) => (
            <button
              key={action.id}
              onClick={action.onClick}
              className="flex items-center gap-3 px-2 py-2.5 rounded-lg hover:bg-accent transition-colors text-left w-full"
            >
              <div
                className={cn(
                  "w-7 h-7 rounded-lg flex items-center justify-center shrink-0",
                  action.iconBg,
                )}
              >
                {action.icon}
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-xs font-medium text-foreground">
                  {action.label}
                </p>
                <p className="text-[11px] text-muted-foreground">
                  {action.sub}
                </p>
              </div>
              <ChevronRight className="h-3.5 w-3.5 text-muted-foreground shrink-0" />
            </button>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
