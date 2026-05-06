"use client";

import { TrendingUp, Shield, Brain, Globe } from "lucide-react";
import { LucideIcon } from "lucide-react";

interface Pillar {
  icon: LucideIcon;
  title: string;
  desc: string;
}

const SCALE_PILLARS: Pillar[] = [
  {
    icon: Brain,
    title: "AI at the core",
    desc: "Natural language analytics means any team member can ask business questions without being a data analyst. The answers shape decisions, not spreadsheets.",
  },
  {
    icon: Globe,
    title: "Multi-location ready",
    desc: "From a single outlet to 500 branches worldwide — NexusSphere's architecture never changes. Each branch is isolated, each workspace is sovereign.",
  },
  {
    icon: Shield,
    title: "Granular access control",
    desc: "Assign roles at workspace, service, or resource level. Groups inherit permissions. Users get exactly what they need — nothing more.",
  },
  {
    icon: TrendingUp,
    title: "One platform, full stack",
    desc: "Replace 6–8 separate SaaS tools with one workspace. Less fragmentation means less data loss, less cost, and one source of truth.",
  },
];

const METRICS = [
  { value: "< 60s", label: "To create a workspace" },
  { value: "9", label: "Services built-in" },
  { value: "100%", label: "Workspace isolation" },
  { value: "0", label: "Code to run analytics AI" },
];

export function ScaleSection() {
  return (
    <section
      id="scale"
      className="bg-background py-28 px-6 border-t border-border"
    >
      <div className="max-w-7xl mx-auto">
        {/* Metrics strip */}
        <div className="grid grid-cols-2 lg:grid-cols-4 gap-3 mb-24">
          {METRICS.map((m) => (
            <div
              key={m.label}
              className="rounded-xl border border-border bg-card p-6 text-center"
            >
              <div className="text-4xl font-bold text-foreground mb-1">
                {m.value}
              </div>
              <div className="text-muted-foreground text-sm">{m.label}</div>
            </div>
          ))}
        </div>

        <div className="grid lg:grid-cols-2 gap-16 items-start">
          <div>
            <p className="text-xs font-semibold tracking-[0.18em] text-muted-foreground uppercase mb-4">
              Built to scale
            </p>
            <h2 className="text-4xl md:text-5xl font-bold text-foreground leading-tight mb-6">
              Grow from startup to{" "}
              <span className="text-muted-foreground font-semibold">
                enterprise
              </span>{" "}
              without switching platforms
            </h2>
            <p className="text-muted-foreground text-lg leading-relaxed">
              NexusSphere was designed to grow with you. Whether you&apos;re
              running one small shop or orchestrating a 200-branch network
              across multiple countries, the platform stays the same — your
              capabilities just expand.
            </p>
          </div>

          <div className="flex flex-col gap-3">
            {SCALE_PILLARS.map((pillar) => {
              const Icon = pillar.icon;
              return (
                <div
                  key={pillar.title}
                  className="group flex items-start gap-4 rounded-xl border border-border bg-card p-5 hover:border-foreground/20 hover:shadow-sm transition-all duration-200"
                >
                  <div className="w-10 h-10 rounded-lg bg-muted flex items-center justify-center text-foreground flex-shrink-0 mt-0.5">
                    <Icon size={18} />
                  </div>
                  <div>
                    <h4 className="text-sm font-semibold text-foreground mb-1">
                      {pillar.title}
                    </h4>
                    <p className="text-muted-foreground text-sm leading-relaxed">
                      {pillar.desc}
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
