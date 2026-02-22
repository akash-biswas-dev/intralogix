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

import { Checkbox } from "@/components/ui/checkbox";
import { useState } from "react";

import axios from "axios";
import useAuthContext, { Authorization } from "@/context/AuthContext";

const SERVER_ADDRESS =
  process.env.NEXT_PUBLIC_SERVER_ADDRESS || "http://localhost:9000";

export default function Auth() {
  const { updateAuthorization } = useAuthContext();

  const [errors, setErrors] = useState<{
    emailOrUsername?: string;
    password?: string;
    error?: string;
  }>({});

  const formAction = async (formData: FormData) => {
    const userCredentials = {
      emailOrUsername: formData.get("emailOrUsername"),
      password: formData.get("password"),
      rememberMe: formData.get("rememberMe"),
    };

    console.log(userCredentials);

    console.log(SERVER_ADDRESS);

    // axios.post<Authorization | string | null>(
    //   "/api/v1/auth",
    //   {
    //     emailOrUsername: userCredentials.emailOrUsername,
    //     password: userCredentials.password,
    //   },
    //   {
    //     params: {
    //       rememberMe,
    //     },
    //   },
    // );
  };

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
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
      <CardContent>
        <form action={formAction}>
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="username-or-email">
                  Email Or Username
                </FieldLabel>
                <Input
                  id="username-or-email"
                  type="text"
                  name="usernameOrEmail"
                  className={errors.emailOrUsername ? "outline-red-600" : ""}
                />
                {errors.emailOrUsername && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors.emailOrUsername}
                  </FieldDescription>
                )}
              </Field>
              <Field>
                <FieldLabel htmlFor="password">Password</FieldLabel>
                <PasswordInputWithToggle
                  id="password"
                  name="password"
                  className={errors.password ? "outline-red-600" : ""}
                />

                {errors.password && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors.password}
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
