'use client';

import { Button } from "@/components/ui/button";
import { Card, CardAction, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Field, FieldDescription, FieldGroup, FieldLabel, FieldSet } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import Link from "next/link";
import { useActionState, useState } from "react";
import { signUp} from "./action";
import PasswordInputWithToggle from "@/components/PasswordInputWithToggel";



export default function SignUp() {

    const [formErrors, formAction, isLoading] = useActionState<SignUpErrors, FormData>(signUp, {})

    const [errors, setErrors] = useState(formErrors);

    return (
        <Card className="w-full max-w-md top-1/2 left-1/2 -translate-1/2 absolute">
            <CardHeader>
                <CardTitle>NexusSphere</CardTitle>
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
                <form action={formAction}>
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
    )
}


export interface SignUpErrors {
    error?: string,
    email?: string,
    password?: string,
}