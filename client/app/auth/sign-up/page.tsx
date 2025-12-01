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
import { Field, FieldGroup, FieldLabel, FieldSet } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import { useState } from "react";

export default function SignUp() {
  const [userform, setUserForm] = useState<{
    email?: string;
    username?: string;
    password?: string;
    confirmPassword?: string;
  }>({});

  const onSubmit = () => {
    console.log(userform);
  };

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription>Enter your login credentials</CardDescription>
        <CardAction>
          <Link href="/auth">
            <Button type="button" variant="outline">
              Log In
            </Button>
          </Link>
        </CardAction>
      </CardHeader>
      <CardContent>
        <form onSubmit={onSubmit}>
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel>Email</FieldLabel>
                <Input
                  type="email"
                  onChange={(eve) =>
                    setUserForm((pre) => ({
                      ...pre,
                      email: eve.target.value,
                    }))
                  }
                />
              </Field>
              <Field>
                <FieldLabel>Username</FieldLabel>
                <Input
                  type="text"
                  onChange={(eve) =>
                    setUserForm((pre) => ({
                      ...pre,
                      username: eve.target.value,
                    }))
                  }
                />
              </Field>
              <Field>
                <FieldLabel>Password</FieldLabel>
                <Input
                  type="password"
                  onChange={(eve) =>
                    setUserForm((pre) => ({
                      ...pre,
                      password: eve.target.value,
                    }))
                  }
                />
              </Field>
              <Field>
                <FieldLabel>Confirm Password</FieldLabel>
                <PasswordInputWithToggle
                  onChange={(eve) =>
                    setUserForm((pre) => ({
                      ...pre,
                      confirmPassword: eve.target.value,
                    }))
                  }
                />
              </Field>
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
