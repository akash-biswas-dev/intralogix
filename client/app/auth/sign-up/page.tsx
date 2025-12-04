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
import { NewUserForm } from "@/schemas/users";
import { useRouter } from "next/navigation";
import axios from "axios";

export default function SignUp() {
  const router = useRouter();

  const signUp = async (
    _state: NewUserFormState,
    formdata: FormData,
  ): Promise<NewUserFormState> => {
    // TODO:Fix this
    const result = NewUserForm.safeParse({
      email: formdata.get("email"),
      username: formdata.get("username"),
      password: formdata.get("password"),
    });

    if (result.error) {
      return {};
    }

    const newUser = result.data;

    const res = await axios.post("/api/v1/auth/register", newUser);

    const { status } = res;
    if (status === 201) {
      router.push("/dashboard");
      return {};
    }
    return {};
  };

  const initialState: NewUserFormState = {};

  const [state, action] = useActionState(signUp, initialState);

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription
          className={state.message ? "text-red-600 font-bold" : ""}
        >
          {state.message ? state.message : "Enter your deatails."}
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

export type NewUserFormState = {
  message?: string;
  fields?: {
    email?: string;
    password?: string;
    username?: string;
  };
};
