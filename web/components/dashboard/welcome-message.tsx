"use client";

import useUserContext from "@/context/user-context";
import SectionHeading from "../section-heading";

export default function WelcomeMessage() {
  const { firstName, lastName } = useUserContext();

  const heading = `Hello, Welcome back ${firstName} ${lastName}`;

  return <SectionHeading heading={heading} />;
}
