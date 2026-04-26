"use client";

import { useActionState, useEffect, useState } from "react";
import { DatePicker } from "./DatePicker";
import OptionPicker, { OptionType } from "./OptionPicker";
import { Button } from "./ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "./ui/card";
import {
  Field,
  FieldDescription,
  FieldGroup,
  FieldLabel,
  FieldSet,
} from "./ui/field";
import { Input } from "./ui/input";

import { updateProfile } from "@/app/setup-profile/action";

const UserProfileUpdate = ({ initialData }: { initialData?: UserProfile }) => {
  const genderOptions: OptionType[] = [
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

  const [preState, formAction, isLoading] = useActionState<
    UpdateProfileState,
    FormData
  >(updateProfile, { state: initialData || {}, err: {} });

  const { state } = preState;

  // Track the previous error state to know when the action has returned new errors
  const [prevServerErrors, setPrevServerErrors] = useState(preState.err);
  const [errors, setErrors] = useState(preState.err);

  // Update state directly during render instead of using useEffect.
  // This prevents an unnecessary double re-render when the server returns a new state.
  if (preState.err !== prevServerErrors) {
    setPrevServerErrors(preState.err);
    setErrors(preState.err);
  }

  const selectedGender: OptionType | undefined = state.gender
    ? genderOptions.find((opt) => state.gender === opt.key)
    : undefined;

  return (
    <Card className="max-w-md w-full absolute top-1/2 left-1/2 -translate-1/2">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription>Setup your profile</CardDescription>
      </CardHeader>

      <CardContent>
        <form action={formAction} className="relative">
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="username">Username</FieldLabel>
                <FieldDescription>
                  This username is used to refer you in the grous, workspace.
                </FieldDescription>

                {/*Username*/}
                <Input
                  defaultValue={state.username}
                  id="username"
                  type="text"
                  name="username"
                  className={errors?.username ? "outline-red-600" : ""}
                  onFocus={() =>
                    setErrors((pre) => ({
                      ...pre,
                      error: undefined,
                      username: undefined,
                    }))
                  }
                />
                {errors?.username && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors?.username}
                  </FieldDescription>
                )}
              </Field>

              <div className="flex gap-2">
                {/*First Name*/}
                <Field>
                  <FieldLabel htmlFor="firstname">First Name</FieldLabel>
                  <Input
                    defaultValue={state.firstName}
                    id="firstName"
                    type="text"
                    name="firstName"
                    className={errors?.firstName ? "outline-red-600" : ""}
                    onFocus={() =>
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
                    defaultValue={state.lastName}
                    id="lastname"
                    type="text"
                    name="lastName"
                    className={errors?.lastName ? "outline-red-600" : ""}
                    onFocus={() =>
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
                    defaultValue={state.dateOfBirth}
                    name="dateOfBirth"
                    label="Date of Birth"
                    onFocusAction={() =>
                      setErrors((pre) => ({
                        ...pre,
                        error: undefined,
                        dateOfBirth: undefined,
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
                    selected={selectedGender}
                    fieldName="gender"
                    label="Gender"
                    options={genderOptions}
                  />
                </Field>
              </div>

              <Field orientation="horizontal">
                <Button type="submit" disabled={isLoading}>
                  {isLoading ? "Saving..." : "Save Profile"}
                </Button>
              </Field>
            </FieldGroup>
          </FieldSet>
        </form>
      </CardContent>
    </Card>
  );
};

export default UserProfileUpdate;

export interface UpdateProfileState {
  state: UserProfile;
  err: UserProfileError;
}

export type UserProfile = {
  username?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
};

export type UserProfileError = {
  error?: string;
  username?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
};
