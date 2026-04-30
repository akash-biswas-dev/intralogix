import { Card, CardContent } from "@/components/ui/card";
import { cn } from "@/lib/utils";
import { Users, Store, GitBranch, ShoppingBag } from "lucide-react";
import Link from "next/link";

interface Workspace {
  id: string;
  name: string;
  initials: string;
  role: "Owner" | "Admin" | "Member";
  color: string;
  members: number;
  stores?: number;
  branches?: number;
  shops?: number;
  lastOpened: string;
}

interface WorkspaceGridProps {
  workspaces?: Workspace[];
}

const defaultWorkspaces: Workspace[] = [
  {
    id: "1",
    name: "Acme Corp",
    initials: "AC",
    role: "Owner",
    color: "bg-green-100 text-green-700",
    members: 8,
    branches: 3,
    shops: 1,
    lastOpened: "2 hours ago",
  },
  {
    id: "2",
    name: "Retail Hub",
    initials: "RH",
    role: "Admin",
    color: "bg-blue-100 text-blue-700",
    members: 12,
    stores: 5,
    shops: 2,
    lastOpened: "Yesterday",
  },
  {
    id: "3",
    name: "Side Project",
    initials: "SP",
    role: "Member",
    color: "bg-pink-100 text-pink-700",
    members: 4,
    stores: 1,
    shops: 0,
    lastOpened: "3 days ago",
  },
];

const roleBadge: Record<string, string> = {
  Owner: "bg-green-50 text-green-700",
  Admin: "bg-blue-50 text-blue-700",
  Member: "bg-muted text-muted-foreground",
};

export function WorkspaceGrid({
  workspaces = defaultWorkspaces,
}: WorkspaceGridProps) {
  return (
    <div>
      <h2 className="text-sm font-medium mb-3">Your workspaces</h2>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-3">
        {workspaces.map((ws) => (
          <Link key={ws.id} href={`/dashboard/workspace/${ws.id}`}>
            <Card className="border-border shadow-none hover:border-foreground/20 transition-colors cursor-pointer h-full">
              <CardContent className="p-4">
                {/* Header */}
                <div className="flex items-center gap-2.5 mb-4">
                  <div
                    className={cn(
                      "w-9 h-9 rounded-lg text-sm font-semibold flex items-center justify-center shrink-0",
                      ws.color,
                    )}
                  >
                    {ws.initials}
                  </div>
                  <div className="min-w-0">
                    <p className="text-sm font-medium truncate">{ws.name}</p>
                    <span
                      className={cn(
                        "text-[10px] font-medium px-1.5 py-0.5 rounded",
                        roleBadge[ws.role],
                      )}
                    >
                      {ws.role}
                    </span>
                  </div>
                </div>

                {/* Meta stats */}
                <div className="flex flex-wrap gap-3">
                  <MetaItem
                    icon={<Users className="h-3 w-3" />}
                    value={ws.members}
                    label="members"
                  />
                  {ws.branches !== undefined && (
                    <MetaItem
                      icon={<GitBranch className="h-3 w-3" />}
                      value={ws.branches}
                      label="branches"
                    />
                  )}
                  {ws.stores !== undefined && (
                    <MetaItem
                      icon={<Store className="h-3 w-3" />}
                      value={ws.stores}
                      label="stores"
                    />
                  )}
                  {ws.shops !== undefined && (
                    <MetaItem
                      icon={<ShoppingBag className="h-3 w-3" />}
                      value={ws.shops}
                      label="shops"
                    />
                  )}
                </div>

                {/* Last opened */}
                <div className="mt-3 pt-3 border-t border-border">
                  <p className="text-[11px] text-muted-foreground">
                    Last opened {ws.lastOpened}
                  </p>
                </div>
              </CardContent>
            </Card>
          </Link>
        ))}
      </div>
    </div>
  );
}

function MetaItem({
  icon,
  value,
  label,
}: {
  icon: React.ReactNode;
  value: number;
  label: string;
}) {
  return (
    <div className="flex items-center gap-1 text-xs text-muted-foreground">
      {icon}
      <span className="font-medium text-foreground">{value}</span> {label}
    </div>
  );
}
