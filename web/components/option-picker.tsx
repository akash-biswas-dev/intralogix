import { useState } from "react";
import { Button } from "./ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "./ui/dropdown-menu";
import { Input } from "./ui/input";
import { FieldLabel } from "./ui/field";

export default function OptionPicker({
  label,
  options,
  placeHolder,
  selected,
  fieldName,
}: {
  options?: OptionType[];
  placeHolder?: string;
  selected?: OptionType;
  fieldName?: string;
  label?: string;
}) {
  const [item, setItem] = useState<OptionType | undefined>(selected);

  const inputId = `option-${fieldName}`;

  return (
    <>
      <FieldLabel htmlFor={inputId}>{label}</FieldLabel>
      <DropdownMenu>
        <Input
          id={inputId}
          defaultValue={item?.key}
          className="hidden"
          name={fieldName}
        ></Input>
        <DropdownMenuTrigger asChild>
          <Button variant="outline">
            {item?.name || placeHolder || "Select"}
          </Button>
        </DropdownMenuTrigger>

        <DropdownMenuContent>
          <DropdownMenuGroup>
            {options?.map(({ key, name }) => {
              return (
                <DropdownMenuItem
                  key={key}
                  onClick={() => setItem({ key, name })}
                >
                  {name}
                </DropdownMenuItem>
              );
            })}
          </DropdownMenuGroup>
        </DropdownMenuContent>
      </DropdownMenu>
    </>
  );
}

export type OptionType = {
  key: string;
  name: string;
};
