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
import { useState } from "react";
import google from "@/public/google-logo.webp";
import Image from "next/image";
import Link from "next/link";

export default function Auth() {
  const [userCredential, setUserCredentials] = useState<{
    email?: string;
    password?: string;
  }>({});

  const onSubmit = () => {
    console.log(userCredential);
  };

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription>Enter your login credentials</CardDescription>
        <CardAction>
          <Link href="/auth/sign-up">
            <Button type="button" variant="outline">
              Sign Up
            </Button>
          </Link>
        </CardAction>
      </CardHeader>
      <CardContent>
        <form onSubmit={onSubmit}>
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel>Username or Email</FieldLabel>
                <Input
                  type="text"
                  onChange={(eve) =>
                    setUserCredentials((pre) => ({
                      ...pre,
                      email: eve.target.value,
                    }))
                  }
                />
              </Field>
              <Field>
                <FieldLabel>Password</FieldLabel>
                <PasswordInputWithToggle
                  onChange={(eve) =>
                    setUserCredentials((pre) => ({
                      ...pre,
                      password: eve.target.value,
                    }))
                  }
                />
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
