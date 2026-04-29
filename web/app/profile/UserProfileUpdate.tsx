"use client";

import { useActionState, useState } from "react";
import { DatePicker } from "@/components/DatePicker";
import OptionPicker, { OptionType } from "@/components/OptionPicker";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Field,
  FieldDescription,
  FieldGroup,
  FieldLabel,
  FieldSet,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";

const UserProfileUpdate = ({
  initialData,
  action,
}: {
  initialData?: UserProfile;
  action: (
    pre: UpdateProfileState,
    formData: FormData,
  ) => Promise<UpdateProfileState>;
}) => {
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
  >(action, { state: initialData || {}, err: {} });

  const { state, err } = preState;

  // Track the previous error state to know when the action has returned new errors
  const [prevServerErrors, setPrevServerErrors] = useState(preState.err);
  const [errors, setErrors] = useState(preState.err);

  // Update state directly during render instead of using useEffect.
  // This prevents an unnecessary double re-render when the server returns a new state.
  if (err !== prevServerErrors) {
    setPrevServerErrors(preState.err);
    setErrors(preState.err);
  }

  const selectedGender: OptionType | undefined = state.gender
    ? genderOptions.find((opt) => state.gender === opt.key)
    : undefined;

  return (
    <Card className="max-w-md w-full absolute top-1/2 left-1/2 -translate-1/2">
      <CardHeader>
        <CardTitle>
          Welcome {state.firstName} {state.lastName}
        </CardTitle>
        <CardDescription>
          {errors?.error ? (
            <span className="text-red-600">{errors.error}</span>
          ) : (
            <span>Setup your profile</span>
          )}
        </CardDescription>
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
                    defaultValue={state?.lastName}
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
  err?: UserProfileError;
}

export interface UserProfile {
  username?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
}

export interface UserProfileError {
  error?: string;
  username?: string;
  firstName?: string;
  lastName?: string;
  dateOfBirth?: string;
  gender?: string;
}
