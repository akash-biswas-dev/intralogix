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

export default function OptionPicker({
    options,
    placeHolder,
    selected,
    fieldName,
}: {
    options?: { key: string; name: string }[];
    placeHolder?: string;
    selected?: OptionType;
    fieldName?: string;
}) {
    const [item, setItem] = useState<{ key: string; name: string } | undefined>(
        selected,
    );
    return (
        <DropdownMenu>
            <Input
                defaultValue={item?.key}
                className="hidden"
                name={fieldName}
            ></Input>
            <DropdownMenuTrigger asChild>
                <Button variant="outline">
                    {placeHolder || item?.name || "Select"}
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
    );
}

export type OptionType = {
    key: string;
    name: string;
};
