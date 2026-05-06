"use client";

import { Building2, Layers3, Zap } from "lucide-react";
import { LucideIcon } from "lucide-react";

interface Step {
  num: string;
  icon: LucideIcon;
  title: string;
  desc: string;
}

const STEPS: Step[] = [
  {
    num: "01",
    icon: Building2,
    title: "Create your workspace",
    desc: "Sign up and spin up an isolated workspace for your business in seconds. Your workspace is your private command centre — no other business has access.",
  },
  {
    num: "02",
    icon: Layers3,
    title: "Enable the services you need",
    desc: "Activate only the services relevant to your business model. Running a retail chain? Enable Branches, Inventory, and Sales. Building online? Add the Shop builder.",
  },
  {
    num: "03",
    icon: Zap,
    title: "Invite your team & scale",
    desc: "Add team members, assign them to Groups, and control exactly who sees what with role-based access. Then let NexusSphere's AI analytics guide your growth.",
  },
];

export function HowItWorksSection() {
  return (
    <section
      id="how-it-works"
      className="bg-muted/30 py-28 px-6 border-t border-border"
    >
      <div className="max-w-6xl mx-auto">
        <div className="text-center mb-20">
          <p className="text-xs font-semibold tracking-[0.18em] text-muted-foreground uppercase mb-4">
            Simple by design
          </p>
          <h2 className="text-4xl md:text-5xl font-bold text-foreground">
            Up and running{" "}
            <span className="text-muted-foreground font-semibold">
              in minutes
            </span>
          </h2>
        </div>

        <div className="relative">
          {/* Connecting line desktop */}
          <div className="hidden lg:block absolute top-8 left-[calc(16.66%+28px)] right-[calc(16.66%+28px)] h-px bg-border" />

          <div className="grid grid-cols-1 lg:grid-cols-3 gap-12 lg:gap-8">
            {STEPS.map((step, i) => {
              const Icon = step.icon;
              return (
                <div
                  key={i}
                  className="flex flex-col items-center lg:items-start text-center lg:text-left gap-5"
                >
                  <div className="relative shrink-0">
                    <div className="w-16 h-16 rounded-2xl bg-card border border-border flex items-center justify-center text-foreground">
                      <Icon size={22} />
                    </div>
                    <div className="absolute -top-2 -right-2 w-5 h-5 rounded-full bg-foreground text-background flex items-center justify-center text-[9px] font-bold">
                      {i + 1}
                    </div>
                  </div>

                  <div>
                    <p className="text-[11px] font-semibold tracking-widest text-muted-foreground uppercase mb-2">
                      Step {step.num}
                    </p>
                    <h3 className="text-xl font-bold text-foreground mb-3">
                      {step.title}
                    </h3>
                    <p className="text-muted-foreground text-sm leading-relaxed">
                      {step.desc}
                    </p>
                  </div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </section>
  );
}
