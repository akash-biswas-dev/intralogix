"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardAction,
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
import Link from "next/link";
import { useActionState, useState } from "react";
import { signUp } from "./action";
import PasswordInputWithToggle from "@/components/PasswordInputWithToggel";

export default function SignUp() {
  const [signUpForm, formAction, isLoading] = useActionState<
    SignUpForm,
    FormData
  >(signUp, { state: {}, error: {} });

  const { state, error } = signUpForm;

  const [errors, setErrors] = useState(error);
  const [preFormErrors, setPreFormErrors] = useState(error);

  if (preFormErrors !== error) {
    setErrors(error);
    setPreFormErrors(error);
  }

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>NexusSphere</CardTitle>
        <CardDescription
          className={errors.error ? "text-red-600 font-bold" : ""}
        >
          {errors.error ? errors.error : "Enter your deatails."}
        </CardDescription>
        <CardAction>
          <Link href="/auth">
            <Button type="button" variant="outline">
              Log In
            </Button>
          </Link>
        </CardAction>
      </CardHeader>
      <CardContent>
        <form action={formAction}>
          <FieldSet>
            <FieldGroup>
              {/* Email */}
              <Field>
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input
                  id="email"
                  name="email"
                  type="text"
                  defaultValue={state.email}
                  onFocus={() =>
                    setErrors((pre) => ({ ...pre, email: undefined }))
                  }
                  required={true}
                />
                {errors.email && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors.email}
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

              <div className="flex gap-4">
                {/* Password */}
                <Field>
                  <FieldLabel htmlFor="user-password">Password</FieldLabel>
                  <Input
                    defaultValue={state.password}
                    id="user-password"
                    name="password"
                    type="password"
                    className={errors.password ? "outline-red-600" : ""}
                    onFocus={() =>
                      setErrors((pre) => ({ ...pre, password: undefined }))
                    }
                    required={true}
                  />
                  {errors.password && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors.password}
                    </FieldDescription>
                  )}
                </Field>

                {/* Confirm Password */}
                <Field>
                  <FieldLabel htmlFor="user-confirm-password">
                    Confirm Password
                  </FieldLabel>
                  <PasswordInputWithToggle
                    defaultValue={state.confirmPassword}
                    name="confirmPassword"
                    id="user-confirm-password"
                    className={errors.confirmPassword ? "outline-red-600" : ""}
                    onFocus={() =>
                      setErrors((pre) => ({
                        ...pre,
                        confirmPassword: undefined,
                      }))
                    }
                    required={true}
                  />
                  {errors.confirmPassword && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors.confirmPassword}
                    </FieldDescription>
                  )}
                </Field>
              </div>
              <Field orientation="horizontal">
                <Button type="submit">Sign Up</Button>
              </Field>
            </FieldGroup>
          </FieldSet>
        </form>
      </CardContent>
    </Card>
  );
}

export interface SignUpForm {
  state: SignUpFormState;
  error: SignUpErrors;
}

export interface SignUpFormState {
  email?: string;
  firstName?: string;
  lastName?: string;
  password?: string;
  confirmPassword?: string;
}

export interface SignUpErrors {
  error?: string;
  email?: string;
  firstName?: string;
  lastName?: string;
  password?: string;
  confirmPassword?: string;
}
