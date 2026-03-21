"use client";

import { Dispatch, FormEvent, SetStateAction } from "react";
import {
  Field,
  FieldDescription,
  FieldGroup,
  FieldLabel,
  FieldSet,
} from "./ui/field";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./ui/card";
import { Input } from "./ui/input";
import { DatePicker } from "./DatePicker";
import { Button } from "./ui/button";
import OptionPicker from "./OptionPicker";

import * as z from "zod";

const UserProfileUpdate = ({
  initialData,
  errors,
  setErrors,
  onSubmit,
}: {
  initialData?: UserProfile;
  errors?: UserProfileError;
  setErrors?: Dispatch<SetStateAction<UserProfileError>>;
  onSubmit?: (eve: FormEvent<HTMLFormElement>) => void;
}) => {
  const genderOptions = [
    {
      key: "MALE",
      name: "Male",
    },
    {
      key: "FEMALE",
      name: "Female",
    },
    {
      key: "OTHERS",
      name: "Others",
    },
  ];

  return (
    <Card className="max-w-md w-full absolute top-1/2 left-1/2 -translate-1/2">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription>Setup your profile</CardDescription>
      </CardHeader>

      <CardContent>
        <form onSubmit={onSubmit} className="relative">
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="username">Username</FieldLabel>
                <FieldDescription>
                  This username is used to refer you in the grous, workspace.
                </FieldDescription>

                {/*Username*/}
                <Input
                  id="username"
                  type="text"
                  name="username"
                  value={initialData?.username}
                  className={errors?.usernmae ? "outline-red-600" : ""}
                  onFocus={() =>
                    setErrors &&
                    setErrors((pre) => ({
                      ...pre,
                      error: undefined,
                      username: undefined,
                    }))
                  }
                />
                {errors?.firstName && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors?.firstName}
                  </FieldDescription>
                )}
              </Field>

              <div className="flex gap-2">
                {/*First Name*/}
                <Field>
                  <FieldLabel htmlFor="firstname">First Name</FieldLabel>
                  <Input
                    id="firstName"
                    type="text"
                    name="firstName"
                    value={initialData?.firstName}
                    className={errors?.firstName ? "outline-red-600" : ""}
                    onFocus={() =>
                      setErrors &&
                      setErrors((pre) => ({
                        ...pre,
                        error: undefined,
                        firstName: undefined,
                      }))
                    }
                  />
                  {errors?.firstName && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors?.firstName}
                    </FieldDescription>
                  )}
                </Field>

                {/*Last Name*/}
                <Field>
                  <FieldLabel htmlFor="lastname">Last Name</FieldLabel>
                  <Input
                    value={initialData?.lastName}
                    id="lastname"
                    type="text"
                    name="lastName"
                    className={errors?.lastName ? "outline-red-600" : ""}
                    onFocus={() =>
                      setErrors &&
                      setErrors((pre) => ({
                        ...pre,
                        error: undefined,
                        lastName: undefined,
                      }))
                    }
                  />
                  {errors?.lastName && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors?.lastName}
                    </FieldDescription>
                  )}
                </Field>
              </div>
              <div className="flex gap-2">
                {/*Date of birth  */}
                <Field>
                  <DatePicker
                    name="dateOfBirth"
                    label="Date of Birth"
                    onFocusAction={() =>
                      setErrors &&
                      setErrors((pre) => ({
                        ...pre,
                        error: undefined,
                        lastName: undefined,
                      }))
                    }
                  />
                  {errors?.dateOfBirth && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors?.dateOfBirth}
                    </FieldDescription>
                  )}
                </Field>

                {/*Gender */}
                <Field>
                  <OptionPicker
                    fieldName="gender"
                    label="Gender"
                    options={genderOptions}
                  />

                  {errors?.dateOfBirth && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors?.dateOfBirth}
                    </FieldDescription>
                  )}
                </Field>
              </div>
              <Field orientation="horizontal">
                <Button type="submit">Save Profile</Button>
              </Field>
            </FieldGroup>
          </FieldSet>
        </form>
      </CardContent>
    </Card>
  );
};

export default UserProfileUpdate;

export type UserProfile = {
  username?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
};
export type UserProfileError = {
  error?: string;
  usernmae?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
};
export const UserProfileSchema = z.object({
  username: z
    .string()
    .min(5, "Username is too short")
    .max(15, "Username too long"),
  firstName: z.string(),
  lastName: z.string(),
  dateOfBirth: z.string(),
  gender: z.string(),
});
