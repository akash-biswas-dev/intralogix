"use state";
import { Eye, EyeClosed } from "lucide-react";
import { Input } from "./ui/input";
import { ChangeEvent, useState } from "react";
export default function PasswordInputWithToggle({
  onChange,
  placeholder,
}: {
  placeholder?: string;
  onChange: (eve: ChangeEvent<HTMLInputElement>) => void;
}) {
  const [inputType, setInputType] = useState<"password" | "text">("password");

  const updateInputType = () => {
    if (inputType === "password") {
      setInputType("text");
    } else {
      setInputType("password");
    }
  };
  return (
    <div className="w-full relative">
      <Input type={inputType} onChange={onChange} placeholder={placeholder} />
      <button
        onClick={updateInputType}
        type="button"
        className="absolute top-1/2 right-2 cursor-pointer -translate-y-1/2"
      >
        {inputType === "password" ? (
          <Eye onClick={updateInputType} />
        ) : (
          <EyeClosed onClick={updateInputType} />
        )}
      </button>
    </div>
  );
}
