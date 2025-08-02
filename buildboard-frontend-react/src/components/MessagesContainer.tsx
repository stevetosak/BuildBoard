import {Message} from "@/components/Message.tsx";

export const MessagesContainer = ({messages}: {
    messages: { user: string, content: string, date: string, userImage: string }[]
}) => {
    return (
        <div className={"flex flex-col gap-2 justify-start items-start"}>
            {messages.map((m, idx) => (
                <Message key={idx} messageData={m}/>
            ))}
        </div>
    )
}