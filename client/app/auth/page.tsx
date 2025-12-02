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
import { login, LoginFormState } from "./action";
import { useActionState } from "react";
import { Checkbox } from "@/components/ui/checkbox";
import { DatePicker } from "@/components/DatePicker";

export default function Auth() {
  const initialState: LoginFormState = {};
  const [state, formAction] = useActionState(login, initialState);

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
