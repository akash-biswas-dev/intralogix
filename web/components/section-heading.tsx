export default function SectionHeading({
  heading,
  description,
}: {
  heading: string;
  description?: string;
}) {
  return (
    <div>
      <h1 className="text-xl font-semibold tracking-tight">
        {/* Hello, {`${firstName} ${lastName}`} */}
        {heading}
      </h1>
      {description && (
        <p className="text-sm text-muted-foreground mt-0.5">
          {/* Here&apos;s what&apos;s happening across your workspaces. */}
          {description}
        </p>
      )}
    </div>
  );
}
