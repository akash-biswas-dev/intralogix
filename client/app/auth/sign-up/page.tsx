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
import axios from "axios";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { FormEvent, useState } from "react";
import * as z from "zod";

const UserData = z.object({
  email: z.email("Invalid email provided"),
  password: z
    .string()
    .min(8, "Password is too short")
    .max(15, "Password is too long."),
});

const SERVER_ADDRESS =
  process.env.NEXT_PUBLIC_SERVER_ADDRESS || "http://localhost:9000";

export default function SignUp() {
  const router = useRouter();

  console.log("Rendered....");
  const [errors, setErrors] = useState<{
    email?: string;
    password?: string;
    error?: string;
  }>({});

  const onSubmit = async (eve: FormEvent<HTMLFormElement>) => {
    eve.preventDefault();

    const formData: FormData = new FormData(eve.currentTarget);

    const userData = {
      email: formData.get("email"),
      password: formData.get("password"),
    };

    setErrors({});

    const parsedData = UserData.safeParse(userData);

    if (!parsedData.success) {
      const fieldErrors = z.treeifyError(parsedData.error).properties;

      const emailErrorMessage = fieldErrors?.email?.errors?.[0];
      const passwordErrorMessage = fieldErrors?.password?.errors?.[0];

      if (emailErrorMessage) {
        setErrors((prev) => ({
          ...prev,
          email: emailErrorMessage,
        }));
      }

      if (passwordErrorMessage) {
        setErrors((prev) => ({ ...prev, password: passwordErrorMessage }));
      }
      return;
    }

    const confirmPassword = formData.get("confirmPassword");
    if (confirmPassword !== userData.password) {
      setErrors({ password: "Confirm password don't match." });
      return;
    }

    const res = await axios.post(
      `${SERVER_ADDRESS}/api/v1/api/register`,
      {
        email: userData.email,
        password: userData.password,
      },
      {
        validateStatus: () => {
          return true;
        },
      },
    );

    if (res.status !== 201) {
      setErrors({ error: res.data });
    }
    router.push("/auth");
  };

  console.log(errors);

  return (
    <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
      <CardHeader>
        <CardTitle>Intralogix</CardTitle>
        <CardDescription
          className={errors.error ? "text-red-600 font-bold" : ""}
        >
          {errors.error ? errors.error : "Enter your deatails."}
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
        <form onSubmit={onSubmit}>
          <FieldSet>
            <FieldGroup>
              <Field>
                <FieldLabel htmlFor="email">Email</FieldLabel>
                <Input
                  id="email"
                  name="email"
                  type="text"
                  onFocus={() =>
                    setErrors((pre) => ({ ...pre, email: undefined }))
                  }
                />
                {errors.email && (
                  <FieldDescription className="text-red-600 font-bold">
                    {errors.email}
                  </FieldDescription>
                )}
              </Field>
              <div className="flex gap-4">
                <Field>
                  <FieldLabel htmlFor="user-password">Password</FieldLabel>
                  <Input
                    id="user-password"
                    name="password"
                    type="password"
                    className={errors.password ? "outline-red-600" : ""}
                    onFocus={() =>
                      setErrors((pre) => ({ ...pre, password: undefined }))
                    }
                  />
                  {errors.password && (
                    <FieldDescription className="text-red-600 font-bold">
                      {errors.password}
                    </FieldDescription>
                  )}
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
