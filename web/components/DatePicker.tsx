"use client";
import { ChevronDownIcon } from "lucide-react";
import { RefObject, useState } from "react";
import { Button } from "./ui/button";
import { Calendar } from "./ui/calendar";
import { FieldLabel } from "./ui/field";
import { Input } from "./ui/input";
import { Popover, PopoverContent, PopoverTrigger } from "./ui/popover";

export function DatePicker({
  defaultValue,
  label,
  name,
  ref,
  onFocusAction,
  before = new Date(1947, 1, 1),
  after = new Date(),
}: {
  defaultValue?: string;
  label?: string;
  name?: string;
  ref?: RefObject<HTMLInputElement | null>;
  onFocusAction?: (eve: React.FocusEvent<HTMLButtonElement>) => void;
  before?: Date;
  after?: Date;
}) {
  const initialDate: Date | undefined = defaultValue
    ? new Date(defaultValue)
    : undefined;

  const [open, setOpen] = useState(false);
  const [date, setDate] = useState<undefined | Date>(initialDate);

  return (
    <>
      <Input
        ref={ref}
        name={name}
        type="date"
        defaultValue={date && date.toISOString().slice(0, 10)}
        className="hidden"
      ></Input>
      {label && (
        <FieldLabel htmlFor="date" className="px-1">
          {label}
        </FieldLabel>
      )}
      <Popover open={open} onOpenChange={setOpen}>
        <PopoverTrigger asChild>
          <Button
            variant="outline"
            id="date"
            className="w-48 justify-between font-normal"
            onFocus={(eve) => onFocusAction && onFocusAction(eve)}
          >
            {date ? date.toLocaleDateString() : "Select date"}
            <ChevronDownIcon />
          </Button>
        </PopoverTrigger>
        <PopoverContent className="w-auto overflow-hidden p-0" align="start">
          <Calendar
            mode="single"
            selected={date}
            captionLayout="dropdown"
            onSelect={(newDate: undefined | Date) => {
              setDate(newDate);
              setOpen(false);
            }}
            disabled={{ before: before, after: after }}
          />
        </PopoverContent>
      </Popover>
    </>
  );
}
