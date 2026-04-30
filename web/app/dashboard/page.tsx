import SectionHeading from "@/components/section-heading";
import { QuickActions } from "@/components/dashboard/quick-actions";
import { RecentActivity } from "@/components/dashboard/recent-activity";
import { Sidebar } from "@/components/dashboard/sidebar";
import { StatsRow } from "@/components/dashboard/stats-row";
import { WorkspaceGrid } from "@/components/dashboard/workspace-grid";
import { Button } from "@/components/ui/button";
import { Plus } from "lucide-react";
import WelcomeMessage from "@/components/dashboard/welcome-message";

export default function DashboardPage({}) {
  // Replace with your actual session/user fetch

  return (
    <div className="flex flex-1 overflow-hidden">
      <div className="flex-1 overflow-y-auto bg-muted/30">
        <div className="p-7 flex flex-col gap-6 max-w-6xl mx-auto">
          {/* Page header */}
          <div className="flex items-start justify-between">
            <WelcomeMessage />
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
      </div>
    </div>
  );
}
