import {Textarea} from "@/components/ui/textarea.tsx";
import * as React from "react";

export const MessageInputBox = ({handleKeyDown, className}: {
    handleKeyDown: (e: React.KeyboardEvent<HTMLTextAreaElement>) => void,
    className?: string
}) => {
    return (
        <Textarea
            onKeyDown={handleKeyDown}
            placeholder="Type your message..."
            className={`flex-1 resize-none rounded-xl border border-background-gray bg-background-card
                     text-white px-4 py-2 text-sm shadow-sm placeholder:text-foreground-gray
                      focus:outline-none focus-visible:ring-0 focus:border-0 focus:border-r-3 focus:border-accent-2
                      ${className}`}
        />
    )
}