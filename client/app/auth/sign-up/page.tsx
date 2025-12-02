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
import { useActionState } from "react";
import { Field, FieldGroup, FieldLabel, FieldSet } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import { NewUserFormState, signUp } from "./../action";
import { DatePicker } from "@/components/DatePicker";

export default function SignUp() {
  const initialState: NewUserFormState = {};
  const [state, action] = useActionState(signUp, initialState);
  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription
          className={state.message ? "text-red-600 font-bold" : ""}
        >
          {state.message ? state.message : "Enter your login credentials"}
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
        <form action={action}>
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input id="email" name="email" type="email" />
              </Field>
              <Field>
                <FieldLabel htmlFor="user-username">Username</FieldLabel>
                <Input id="user-username" name="username" type="text" />
              </Field>
              <div className="flex gap-4">
                <Field>
                  <FieldLabel htmlFor="user-password">Password</FieldLabel>
                  <Input id="user-password" name="password" type="password" />
                </Field>
                <Field>
                  <FieldLabel htmlFor="user-confirm-password">
                    Confirm Password
                  </FieldLabel>
                  <PasswordInputWithToggle
                    name="confirmPassword"
                    id="user-confirm-password"
                  />
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
