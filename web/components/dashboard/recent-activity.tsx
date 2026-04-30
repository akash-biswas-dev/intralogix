import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";

interface ActivityItem {
  id: string;
  actor: string;
  action: string;
  workspace: string;
  time: string;
  dotColor: string;
}

const defaultActivity: ActivityItem[] = [
  {
    id: "1",
    actor: "Priya S.",
    action: "added 3 products to",
    workspace: "Acme Corp",
    time: "10m ago",
    dotColor: "bg-green-500",
  },
  {
    id: "2",
    actor: "You",
    action: "updated role permissions in",
    workspace: "Retail Hub",
    time: "2h ago",
    dotColor: "bg-blue-500",
  },
  {
    id: "3",
    actor: "Rahul M.",
    action: "created a new vendor in",
    workspace: "Acme Corp",
    time: "5h ago",
    dotColor: "bg-amber-500",
  },
  {
    id: "4",
    actor: "You",
    action: "invited Kiran T. to",
    workspace: "Side Project",
    time: "1d ago",
    dotColor: "bg-pink-500",
  },
];

interface RecentActivityProps {
  items?: ActivityItem[];
}

export function RecentActivity({
  items = defaultActivity,
}: RecentActivityProps) {
  return (
    <Card className="border-border shadow-none">
      <CardHeader className="pb-2 px-4 pt-4">
        <CardTitle className="text-sm font-medium">Recent activity</CardTitle>
      </CardHeader>
      <CardContent className="px-4 pb-4">
        <div className="flex flex-col">
          {items.map((item, i) => (
            <div
              key={item.id}
              className={cn(
                "flex items-start gap-2.5 py-2.5",
                i !== items.length - 1 && "border-b border-border",
              )}
            >
              <div
                className={cn(
                  "w-1.5 h-1.5 rounded-full mt-1.5 shrink-0",
                  item.dotColor,
                )}
              />
              <p className="text-xs text-muted-foreground leading-relaxed flex-1">
                <span className="font-medium text-foreground">
                  {item.actor}
                </span>{" "}
                {item.action}{" "}
                <span className="font-medium text-foreground">
                  {item.workspace}
                </span>
              </p>
              <span className="text-[11px] text-muted-foreground shrink-0 whitespace-nowrap">
                {item.time}
              </span>
            </div>
          ))}
        </div>
      </CardContent>
    </Card>
  );
}
