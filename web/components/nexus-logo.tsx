"use client";

interface NexusLogoProps {
  size?: number;
  showText?: boolean;
  className?: string;
}

export function NexusLogo({
  size = 36,
  showText = true,
  className = "",
}: NexusLogoProps) {
  return (
    <div className={`flex items-center gap-2.5 ${className}`}>
      <svg
        width={size}
        height={size}
        viewBox="0 0 48 48"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <polygon
          points="24,2 43,13 43,35 24,46 5,35 5,13"
          fill="none"
          stroke="hsl(var(--foreground))"
          strokeWidth="1.5"
          strokeLinejoin="round"
          opacity="0.85"
        />
        <polygon
          points="24,10 37,17.5 37,30.5 24,38 11,30.5 11,17.5"
          fill="hsl(var(--foreground))"
          opacity="0.05"
        />
        {(
          [
            [24, 2],
            [43, 13],
            [43, 35],
            [24, 46],
            [5, 35],
            [5, 13],
          ] as [number, number][]
        ).map(([cx, cy], i) => (
          <circle
            key={i}
            cx={cx}
            cy={cy}
            r="2"
            fill="hsl(var(--foreground))"
            opacity="0.6"
          />
        ))}
        <circle cx="24" cy="24" r="4" fill="hsl(var(--foreground))" />
        <line
          x1="5"
          y1="13"
          x2="24"
          y2="24"
          stroke="hsl(var(--foreground))"
          strokeWidth="1.5"
          strokeLinecap="round"
          opacity="0.5"
        />
        <line
          x1="24"
          y1="24"
          x2="43"
          y2="13"
          stroke="hsl(var(--foreground))"
          strokeWidth="1.5"
          strokeLinecap="round"
          opacity="0.5"
        />
        <line
          x1="5"
          y1="35"
          x2="43"
          y2="35"
          stroke="hsl(var(--foreground))"
          strokeWidth="1"
          strokeLinecap="round"
          opacity="0.18"
        />
      </svg>
      {showText && (
        <span className="text-xl font-bold tracking-tight">
          <span className="text-foreground">Nexus</span>
          <span className="text-muted-foreground font-semibold">Sphere</span>
        </span>
      )}
    </div>
  );
}
