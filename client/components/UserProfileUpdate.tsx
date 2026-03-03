"use client";

import { FormEvent } from "react";
import { Field } from "./ui/field";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./ui/card";

const UserProfileUpdate = ({
  initialData,
  onSubmit,
}: {
  initialData?: UserProfile;
  onSubmit?: (eve: FormEvent<HTMLFormElement>) => void;
}) => {
  return (
    <Card className="max-w-md">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription>Setup your profile</CardDescription>
      </CardHeader>

      <CardContent>
        <form onSubmit={onSubmit}></form>
      </CardContent>
    </Card>
  );
};

export default UserProfileUpdate;

export type UserProfile = {
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
};
