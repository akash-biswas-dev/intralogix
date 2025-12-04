const UserProfileUpdate = ({
  userProfileData,
  action,
}: {
  userProfileData?: UserProfile;
  action?: (formdata: FormData) => void;
}) => {
  console.log(userProfileData);
  return <form action={action}>Update profile component.</form>;
};

export default UserProfileUpdate;

export type UserProfile = {
  firstName?: string;
  lastName?: string;
};
