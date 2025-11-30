"use client";
import PasswordInputWithToggle from "@/components/PasswordInputWithToggel";
import { FieldGroup } from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { useState } from "react";

export default function SignUp() {
  const [userCredential, setUserCredentials] = useState<{
    email?: string;
    password?: string;
  }>({});

  return (
    <form className="w-full max-w-md">
      <FieldGroup>
        <Input
          type="email"
          onChange={(eve) =>
            setUserCredentials((pre) => ({ ...pre, email: eve.target.value }))
          }
          placeholder="Email"
        />
        <PasswordInputWithToggle
          placeholder="Password"
          onChange={(eve) =>
            setUserCredentials((pre) => ({
              ...pre,
              password: eve.target.value,
            }))
          }
        />
      </FieldGroup>
    </form>
  );
}
