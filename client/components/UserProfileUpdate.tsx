"use client";

import { FormEvent } from "react";

const UserProfileUpdate = ({
  initialData,
  onSubmit,
}: {
  initialData?: UserProfile;
  onSubmit?: (eve: FormEvent<HTMLFormElement>) => void;
}) => {
  return <form onSubmit={onSubmit}>Update profile component.</form>;
};

export default UserProfileUpdate;

export type UserProfile = {
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
};
