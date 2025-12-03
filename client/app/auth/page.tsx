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
import {
  Field,
  FieldDescription,
  FieldGroup,
  FieldLabel,
  FieldSet,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import google from "@/public/google-logo.webp";
import Image from "next/image";
import Link from "next/link";

import { useActionState } from "react";
import { Checkbox } from "@/components/ui/checkbox";
import { UserCredentials } from "@/schemas/users";
import { login } from "./action";
import * as z from "zod";

export default function Auth() {
  const loginAction = async (
    _previousState: LoginFormState,
    formdata: FormData,
  ): Promise<LoginFormState> => {
    const result = UserCredentials.safeParse({
      usernameOrEmail: formdata.get("usernameOrEmail"),
      password: formdata.get("password"),
    });

    if (!result.success) {
      const err = z.treeifyError(result.error);
      const errors: LoginFormState = {
        fields: {
          usernameOrEmail:
            err.properties?.usernameOrEmail &&
            "Enter a valid username or email.",
          password:
            err.properties?.password && "Password length min 8 and max 15.",
        },
      };
      return errors;
    }

    const rememberMe = formdata.get("rememberMe") ? true : false;

    const { usernameOrEmail, password } = result.data;

    const res = await login({ usernameOrEmail, password, rememberMe });
    if (res === null) {
      return {
        message: "Some error occurred",
      };
    }
    return {};
  };

  const initialState: LoginFormState = {};
  const [state, formAction] = useActionState(loginAction, initialState);

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        {state.message ? (
          <CardDescription className="text-red-600 font-bold">
            {state.message}
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
      <CardContent>
        <form action={formAction}>
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="username-or-email">
                  Username or Email
                </FieldLabel>
                <Input
                  id="username-or-email"
                  type="text"
                  name="usernameOrEmail"
                  className={
                    state.fields?.usernameOrEmail ? "outline-red-600" : ""
                  }
                />
                {state.fields?.usernameOrEmail && (
                  <FieldDescription className="text-red-600 font-bold">
                    {state.fields.usernameOrEmail}
                  </FieldDescription>
                )}
              </Field>
              <Field>
                <FieldLabel htmlFor="password">Password</FieldLabel>
                <PasswordInputWithToggle
                  id="password"
                  name="password"
                  className={state.fields?.password ? "outline-red-600" : ""}
                />

                {state.fields?.password && (
                  <FieldDescription className="text-red-600 font-bold">
                    {state.fields.password}
                  </FieldDescription>
                )}
              </Field>
              <Field orientation="horizontal">
                <Checkbox name="rememberMe" id="remember-me"></Checkbox>
                <FieldLabel htmlFor="remember-me" className="cursor-pointer">
                  Remember Me
                </FieldLabel>
              </Field>
              <Field orientation="horizontal">
                <Button type="submit">Sign In</Button>
              </Field>

              <Button
                type="button"
                variant="outline"
                className="flex gap-2 w-full"
              >
                <Image
                  src={google}
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

export type LoginFormState = {
  message?: string;
  fields?: {
    usernameOrEmail?: string;
    password?: string;
  };
};
