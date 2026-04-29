"use client";

import PasswordInputWithToggle from "@/components/PasswordInputWithToggel";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardAction,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Checkbox } from "@/components/ui/checkbox";
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
import { login } from "./action";

export default function Auth() {
  const [errorStatus, formAction, isLoading] = useActionState<
    LoginFormError,
    FormData
  >(login, {});

  // Track the previous error state to know when the action has returned new errors
  const [prevServerErrors, setPrevServerErrors] = useState(errorStatus);
  const [errors, setErrors] = useState(errorStatus);

  // Update state directly during render instead of using useEffect.
  // This prevents an unnecessary double re-render when the server returns a new state.
  if (errorStatus !== prevServerErrors) {
    setPrevServerErrors(errorStatus);
    setErrors(errorStatus);
  }

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>NexusSphere</CardTitle>
        {errors.error ? (
          <CardDescription className="text-red-600 font-bold">
            {errors.error}
          </CardDescription>
        ) : (
          <CardDescription>Enter your login credentials</CardDescription>
        )}
        <CardAction>
          <Link href="/auth/sign-up">
            <Button type="button" variant="outline">
              Sign Up
            </Button>
          </Link>
        </CardAction>
      </CardHeader>

      {/* Content of the form. */}
      <CardContent>
        <form action={formAction}>
          <FieldSet>
            <FieldGroup>
              {/* Email or Username  */}
              <Field>
                <FieldLabel htmlFor="username-or-email">
                  Email Or Username
                </FieldLabel>
                <Input
                  id="email-or-username"
                  type="text"
                  name="emailOrUsername"
                  className={errors.emailOrUsername ? "outline-red-600" : ""}
                  onFocus={() =>
                    setErrors((pre) => ({
                      ...pre,
                      error: undefined,
                      emailOrUsername: undefined,
                    }))
                  }
                />
                {errors.emailOrUsername && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors.emailOrUsername}
                  </FieldDescription>
                )}
              </Field>

              {/* Password */}
              <Field>
                <FieldLabel htmlFor="password">Password</FieldLabel>
                <PasswordInputWithToggle
                  id="password"
                  name="password"
                  className={errors.password ? "outline-red-600" : ""}
                  onFocus={() =>
                    setErrors((pre) => ({
                      ...pre,
                      error: undefined,
                      password: undefined,
                    }))
                  }
                />

                {errors.password && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors.password}
                  </FieldDescription>
                )}
              </Field>

              {/* Checkbox to remember me or not. */}
              <Field orientation="horizontal">
                <Checkbox name="rememberMe" id="remember-me"></Checkbox>
                <FieldLabel htmlFor="remember-me" className="cursor-pointer">
                  Remember Me
                </FieldLabel>
              </Field>

              {/* Submit button */}
              <Field orientation="horizontal">
                <Button type="submit">
                  {isLoading ? "Saving..." : "Save Profile"}
                </Button>
              </Field>

              {/* Sign-in with google. */}
              <Button
                type="button"
                variant="outline"
                className="flex gap-2 w-full p-2"
              >
                <img
                  src={"/google-logo.webp"}
                  alt="google"
                  className="h-full object-contain w-fit"
                />
                <span>SignIn with google</span>
              </Button>
            </FieldGroup>
          </FieldSet>
        </form>
      </CardContent>
    </Card>
  );
}

export interface LoginFormError {
  error?: string;
  emailOrUsername?: string;
  password?: string;
}
