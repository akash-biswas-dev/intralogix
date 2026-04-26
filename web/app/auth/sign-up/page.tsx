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

  const [formErrors, setFormErrors] = useState(error);
  const [preFormErrors, setPreFormErrors] = useState(error);

  if (preFormErrors !== error) {
    setFormErrors(error);
    setPreFormErrors(error);
  }



  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>NexusSphere</CardTitle>
        <CardDescription
          className={formErrors.error ? "text-red-600 font-bold" : ""}
        >
          {formErrors.error ? formErrors.error : "Enter your deatails."}
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
                    setFormErrors((pre) => ({ ...pre, email: undefined }))
                  }
                  required={true}
                />
                {formErrors.email && (
                  <FieldDescription className="text-red-600 font-bold">
                    {formErrors.email}
                  </FieldDescription>
                )}
              </Field>
              <div className="flex gap-4">
                {/* Password */}
                <Field>
                  <FieldLabel htmlFor="user-password">Password</FieldLabel>
                  <Input
                    id="user-password"
                    name="password"
                    type="password"
                    className={formErrors.password ? "outline-red-600" : ""}
                    onFocus={() =>
                      setFormErrors((pre) => ({ ...pre, password: undefined }))
                    }
                    required={true}
                  />
                  {formErrors.password && (
                    <FieldDescription className="text-red-600 font-bold">
                      {formErrors.password}
                    </FieldDescription>
                  )}
                </Field>

                {/* Confirm Password */}
                <Field>
                  <FieldLabel htmlFor="user-confirm-password">
                    Confirm Password
                  </FieldLabel>
                  <PasswordInputWithToggle
                    name="confirmPassword"
                    id="user-confirm-password"
                    className={formErrors.confirmPassword ? "outline-red-600" : ""}
                    onFocus={() =>
                      setFormErrors((pre) => ({ ...pre, confirmPassword: undefined }))
                    }
                    required={true}
                  />
                  {formErrors.confirmPassword && (
                    <FieldDescription className="text-red-600 font-bold">
                      {formErrors.confirmPassword}
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
}

export interface SignUpErrors {
  error?: string;
  email?: string;
  password?: string;
  confirmPassword?: string;
}
