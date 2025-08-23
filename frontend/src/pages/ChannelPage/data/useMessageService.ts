import type {ChannelMessageDisplay, ChannelMessageEvent} from "@/types.ts";
import {useState} from "react";

export const useMessageService = (initialState: ChannelMessageDisplay[]) => {
    const [messages, setMessages] = useState<ChannelMessageDisplay[]>(initialState)
    const [currentlyTyping,setCurrentlyTyping] = useState("")

    const messageEventHandler = (event: ChannelMessageEvent) => {
        switch (event.type) {
            case "EDIT": {
                setMessages((prevMessages) => {
                        const pruned = prevMessages.filter(
                            msg => !(msg.senderUsername === event.payload.senderUsername && msg.sentAt === event.payload.sentAt)
                        )
                        return [...pruned, {
                            senderUsername: event.payload.senderUsername,
                            content: event.payload.content,
                            avatarUrl: "",
                            sentAt: event.payload.sentAt
                        }]
                    }
                );
                break;
            }
            case "SEND": {
                setMessages((prevMessages) =>
                    [...prevMessages, {
                        senderUsername: event.payload.senderUsername,
                        content: event.payload.content,
                        avatarUrl: "",
                        sentAt: event.payload.sentAt
                    }]);
                break
            }
            case "DELETE": {
                setMessages((prevMessages) =>
                    prevMessages.filter(
                        msg =>
                            !(msg.senderUsername === event.payload.senderUsername && msg.sentAt === event.payload.sentAt)
                    )
                );
                break
            }
            case "TYPE_END": {
                setCurrentlyTyping("")
                break
            }
            case "TYPE_START": {
                setCurrentlyTyping(event.payload.senderUsername)
            }
        }
    }
    return {messages, messageEventHandler,currentlyTyping}
}