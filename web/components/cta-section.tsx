"use client";

import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import { ArrowRight } from "lucide-react";
import { NexusLogo } from "./nexus-logo";

export function CtaSection() {
  return (
    <section className="bg-muted/30 py-28 px-6 border-t border-border relative overflow-hidden">
      {/* Subtle dot grid */}
      <div
        className="absolute inset-0 opacity-[0.04] pointer-events-none"
        style={{
          backgroundImage: `radial-gradient(hsl(var(--foreground)) 1px, transparent 1px)`,
          backgroundSize: "28px 28px",
        }}
      />
      <div
        className="absolute inset-0 pointer-events-none"
        style={{
          background:
            "radial-gradient(ellipse at center, transparent 30%, hsl(var(--background)) 75%)",
        }}
      />

      <div className="max-w-3xl mx-auto text-center relative z-10">
        <p className="text-xs font-semibold tracking-[0.18em] text-muted-foreground uppercase mb-6">
          Ready to launch?
        </p>
        <h2 className="text-4xl md:text-5xl font-bold text-foreground mb-6 leading-tight">
          Your business deserves{" "}
          <span className="text-muted-foreground font-semibold">
            one unified sphere.
          </span>
        </h2>
        <p className="text-muted-foreground text-lg mb-10 max-w-md mx-auto">
          Create your first workspace in under a minute. No credit card
          required. Scale at your own pace.
        </p>
        <Link href="/auth">
          <Button
            size="lg"
            className="gap-2 text-base px-10 py-6 hover:scale-[1.03] transition-transform"
          >
            Create your workspace
            <ArrowRight size={18} />
          </Button>
        </Link>
      </div>
    </section>
  );
}

const FOOTER_LINKS: Record<string, string[]> = {
  Product: ["Features", "How it works", "Pricing", "Changelog"],
  Services: ["Branches", "Inventory", "Analytics AI", "Online Shop"],
  Company: ["About", "Blog", "Careers", "Contact"],
  Legal: ["Privacy Policy", "Terms of Service", "Cookie Policy"],
};

export function Footer() {
  return (
    <footer className="bg-background border-t border-border px-6 py-16">
      <div className="max-w-7xl mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-5 gap-12 mb-12">
          <div className="md:col-span-2">
            <NexusLogo size={28} />
            <p className="text-muted-foreground text-sm mt-4 leading-relaxed max-w-xs">
              Multi-tenant business infrastructure for every scale. One
              workspace per business. Every operation, unified.
            </p>
          </div>

          {Object.entries(FOOTER_LINKS).map(([group, links]) => (
            <div key={group}>
              <h4 className="text-foreground text-xs font-semibold tracking-wider uppercase mb-4">
                {group}
              </h4>
              <ul className="flex flex-col gap-2.5">
                {links.map((link) => (
                  <li key={link}>
                    <a
                      href="#"
                      className="text-muted-foreground hover:text-foreground text-sm transition-colors"
                    >
                      {link}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>

        <Separator className="mb-8" />

        <div className="flex flex-col sm:flex-row items-center justify-between gap-4">
          <p className="text-muted-foreground text-xs">
            © {new Date().getFullYear()} NexusSphere. All rights reserved.
          </p>
          <p className="text-muted-foreground text-xs">
            Built for businesses that move fast.
          </p>
        </div>
      </div>
    </footer>
  );
}
