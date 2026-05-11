"use client";

import { createContext, useContext } from "react";

const UserContext = createContext<User>({
  firstName: "",
  lastName: "",
  username: "",
  email: "",
});

export const UserContextProvider = ({
  children,
  user,
}: {
  user: User;
  children: React.ReactNode;
}) => {
  return <UserContext.Provider value={user}>{children}</UserContext.Provider>;
};

export default function useUserContext() {
  return useContext(UserContext);
}

export interface User {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
}
