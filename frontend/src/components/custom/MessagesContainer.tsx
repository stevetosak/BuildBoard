import {Message} from "@/components/custom/Message.tsx";
import type {ChannelMessageDisplay} from "@/types.ts";export const MessagesContainer = ({messages}: {
    messages: ChannelMessageDisplay[],
}) => {


    return (
        <div className={"flex flex-col gap-2 justify-start items-start"}>
            {messages.map((m, idx) => (
                <Message key={m.sentAt} messageData={m}/>
            ))}
        </div>
    )
}