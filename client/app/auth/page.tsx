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
import Link from "next/link";

import { Checkbox } from "@/components/ui/checkbox";
import { useState } from "react";

import * as z from "zod";

import useAuthContext, { SERVER_ADDRESS } from "@/context/AuthContext";
import axios from "axios";
import { useRouter } from "next/navigation";

const UserCredential = z.object({
  emailOrUsername: z.string().min(5, "Invalid email or Username"),
  password: z
    .string()
    .min(8, "Password is too short.")
    .max(15, "Password is too long."),
});

export default function Auth() {
  const { updateAuthorization } = useAuthContext();
  const router = useRouter();

  const [errors, setErrors] = useState<{
    emailOrUsername?: string;
    password?: string;
    error?: string;
  }>({});

  const formAction = async (formData: FormData) => {
    const userCredentials = {
      emailOrUsername: formData.get("emailOrUsername"),
      password: formData.get("password"),
      rememberMe: formData.get("rememberMe") ? true : false,
    };

    const parsedObject = UserCredential.safeParse({
      emailOrUsername: userCredentials.emailOrUsername,
      password: userCredentials.password,
    });

    if (!parsedObject.success) {
      const errorProperties = z.treeifyError(parsedObject.error).properties;

      const emailError = errorProperties?.emailOrUsername?.errors?.[0];

      if (emailError) {
        setErrors({ emailOrUsername: emailError });
      }

      const passwordError = errorProperties?.password?.errors?.[0];
      if (passwordError) {
        setErrors((pre) => ({ ...pre, password: passwordError }));
      }
    }

    const res = await axios.post(
      `${SERVER_ADDRESS}/api/v1/auth`,
      {
        emailOrUsername: userCredentials.emailOrUsername,
        password: userCredentials.password,
      },
      {
        params: {
          rememberMe: userCredentials.rememberMe,
        },
        validateStatus: () => true,
      },
    );

    if (res.status === 201) {
      updateAuthorization(res.data.data);
      router.push("/dashboard");
      return;
    }

    if (res.status === 307) {
      router.push("/setup-profile");
      return;
    }
    const err = res.data.err;
    if (err) {
      setErrors({ error: err });
    } else {
      console.error("Service unavailable!");
    }
  };

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
      <CardContent>
        <form action={formAction}>
          <FieldSet>
            <FieldGroup>
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
                      emailOrUsername: undefined,
                    }))
                  }
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
