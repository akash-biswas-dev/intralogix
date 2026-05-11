"use client";

import {
  Users,
  Building2,
  Boxes,
  Globe,
  Package,
  Layers,
  Truck,
  ShoppingCart,
  BarChart3,
} from "lucide-react";
import { LucideIcon } from "lucide-react";

interface Service {
  icon: LucideIcon;
  title: string;
  desc: string;
  wide?: boolean;
}

const SERVICES: Service[] = [
  {
    icon: BarChart3,
    title: "AI-Powered Analytics",
    desc: "Type a plain-English question — NexusSphere's AI generates real-time charts, forecasts, and business insights on the fly. No dashboards to configure.",
    wide: true,
  },
  {
    icon: Building2,
    title: "Branch Management",
    desc: "Map every physical store, hospital, or outlet. Each branch operates independently yet reports back to your unified workspace.",
  },
  {
    icon: Boxes,
    title: "Warehouses",
    desc: "Know exactly what's stored, where, and how much. Assign warehouses to branches and track asset movement in real time.",
  },
  {
    icon: Globe,
    title: "Online Shop Builder",
    desc: "AI generates a full e-commerce storefront from your product catalog. One click to host. Connect your own domain.",
    wide: true,
  },
  {
    icon: Users,
    title: "Groups & RBAC",
    desc: "Create groups for departments or projects. Assign granular roles — who can view, edit, or admin each service.",
  },
  {
    icon: ShoppingCart,
    title: "Sales Management",
    desc: "Log every sale, track margins, manage customer history and payment records all in one place.",
  },
  {
    icon: Package,
    title: "Product Management",
    desc: "Manage everything from sellable goods to machinery assets. Categorise, tag, and price with flexibility.",
  },
  {
    icon: Layers,
    title: "Inventory Control",
    desc: "Live stock levels, low-stock alerts, and reorder workflows. Never miss a fulfillment window.",
  },
  {
    icon: Truck,
    title: "Shipment Management",
    desc: "Build and manage your own logistics fleet. Assign vehicles, plan routes, and track deliveries end-to-end.",
  },
];

function ServiceCard({ service }: { service: Service }) {
  const Icon = service.icon;
  return (
    <div
      className={`group relative overflow-hidden rounded-xl border border-border bg-card p-6 flex flex-col gap-4
        transition-all duration-200 hover:border-foreground/20 hover:shadow-md cursor-default
        ${service.wide ? "md:col-span-2" : ""}`}
    >
      {/* Top accent line on hover */}
      <div className="absolute top-0 left-0 right-0 h-px bg-foreground opacity-0 group-hover:opacity-10 transition-opacity duration-300" />

      <div className="w-10 h-10 rounded-lg bg-muted flex items-center justify-center text-foreground transition-transform duration-200 group-hover:scale-110">
        <Icon size={20} />
      </div>

      <div>
        <h3 className="text-sm font-semibold text-foreground mb-1.5">
          {service.title}
        </h3>
        <p className="text-muted-foreground text-sm leading-relaxed">
          {service.desc}
        </p>
      </div>
    </div>
  );
}

export function FeaturesSection() {
  return (
    <section
      id="features"
      className="bg-background py-28 px-6 border-t border-border"
    >
      <div className="max-w-7xl mx-auto">
        <div className="text-center mb-16">
          <p className="text-xs font-semibold tracking-[0.18em] text-muted-foreground uppercase mb-4">
            Everything your business needs
          </p>
          <h2 className="text-4xl md:text-5xl font-bold text-foreground mb-4">
            Nine services.{" "}
            <span className="text-muted-foreground font-semibold">
              One workspace.
            </span>
          </h2>
          <p className="text-muted-foreground text-lg max-w-xl mx-auto">
            NexusSphere bundles every critical business function into a single
            platform — isolated per business, connected by design.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3">
          {SERVICES.map((service) => (
            <ServiceCard key={service.title} service={service} />
          ))}
        </div>
      </div>
    </section>
  );
}
