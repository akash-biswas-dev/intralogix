"use client";

import useUserContext from "@/context/user-context";

export default function Gretings() {
  const { firstName, lastName } = useUserContext();

  return (
    <div>
      <h1 className="text-xl font-semibold tracking-tight">
        Hello, {`${firstName} ${lastName}`}
      </h1>
      <p className="text-sm text-muted-foreground mt-0.5">
        Here&apos;s what&apos;s happening across your workspaces.
      </p>
    </div>
  );
}
