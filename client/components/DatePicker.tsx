"use client";
import { ChevronDownIcon } from "lucide-react";
import { FieldLabel } from "./ui/field";
import { Input } from "./ui/input";
import { Popover, PopoverContent, PopoverTrigger } from "./ui/popover";
import { Button } from "./ui/button";
import { RefObject, useState } from "react";
import { Calendar } from "./ui/calendar";

export function DatePicker({
  label,
  name,
  ref,
}: {
  label?: string;
  name?: string;
  ref?: RefObject<HTMLInputElement | null>;
}) {
  const [open, setOpen] = useState(false);
  const [date, setDate] = useState<undefined | Date>(undefined);
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
            onSelect={(date) => {
              setDate(date);
              setOpen(false);
            }}
          />
        </PopoverContent>
      </Popover>
    </>
  );
}
