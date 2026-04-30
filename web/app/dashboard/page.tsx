import Gretings from "@/components/dashboard/gretings";
import { QuickActions } from "@/components/dashboard/quick-actions";
import { RecentActivity } from "@/components/dashboard/recent-activity";
import { StatsRow } from "@/components/dashboard/stats-row";
import { WorkspaceGrid } from "@/components/dashboard/workspace-grid";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";

export default function DashboardPage({}) {
  // Replace with your actual session/user fetch

  return (
    <div className="p-7 flex flex-col gap-6 max-w-6xl mx-auto">
      {/* Page header */}
      <div className="flex items-start justify-between">
        <Gretings />
        <Button size="sm" className="gap-1.5 text-xs h-8">
          <Plus className="h-3.5 w-3.5" />
          New workspace
        </Button>
      </div>

      {/* Stats */}
      <StatsRow />

      {/* Workspace cards */}
      <WorkspaceGrid />

      {/* Bottom row */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <RecentActivity />
        <QuickActions />
      </div>
    </div>
  );
}
