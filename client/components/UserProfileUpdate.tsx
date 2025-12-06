"use client";

const UserProfileUpdate = ({
  initialData,
  authorization
}: {
    initialData?: UserProfile;
    authorization: string;
}) => {

  const action = (formData:FormData) => {

  }

  return <form action={action}>Update profile component.</form>;
};

export default UserProfileUpdate;

export type UserProfile = {
  firstName?: string;
  lastName?: string;
};
