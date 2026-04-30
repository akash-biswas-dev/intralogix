import { Card, CardContent } from "@/components/ui/card";
import { Briefcase, Users, CheckSquare, Clock } from "lucide-react";

interface StatCardProps {
  label: string;
  value: string | number;
  sub?: string;
  subColor?: string;
  icon: React.ReactNode;
}

function StatCard({
  label,
  value,
  sub,
  subColor = "text-muted-foreground",
  icon,
}: StatCardProps) {
  return (
    <Card className="border-border shadow-none">
      <CardContent className="p-4">
        <div className="flex items-start justify-between mb-3">
          <p className="text-xs text-muted-foreground">{label}</p>
          <div className="text-muted-foreground opacity-50">{icon}</div>
        </div>
        <p className="text-2xl font-medium tracking-tight">{value}</p>
        {sub && <p className={`text-xs mt-1 ${subColor}`}>{sub}</p>}
      </CardContent>
    </Card>
  );
}

interface StatsRowProps {
  totalWorkspaces?: number;
  totalMembers?: number;
  openTasks?: number;
  lastActiveWorkspace?: string;
}

export function StatsRow({
  totalWorkspaces = 3,
  totalMembers = 24,
  openTasks = 11,
  lastActiveWorkspace = "Acme Corp",
}: StatsRowProps) {
  return (
    <div className="grid grid-cols-2 md:grid-cols-4 gap-3">
      <StatCard
        label="Total workspaces"
        value={totalWorkspaces}
        sub="2 active"
        subColor="text-green-600"
        icon={<Briefcase className="h-4 w-4" />}
      />
      <StatCard
        label="Members across all"
        value={totalMembers}
        sub="+3 this month"
        icon={<Users className="h-4 w-4" />}
      />
      <StatCard
        label="Open tasks"
        value={openTasks}
        sub="4 due today"
        subColor="text-amber-600"
        icon={<CheckSquare className="h-4 w-4" />}
      />
      <StatCard
        label="Last active"
        value={lastActiveWorkspace}
        sub="2 hours ago"
        icon={<Clock className="h-4 w-4" />}
      />
    </div>
  );
}
