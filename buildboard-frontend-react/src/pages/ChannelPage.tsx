import {MessagesContainer} from "@/components/custom/MessagesContainer.tsx"
import {Card, CardContent, CardFooter, CardHeader} from "@/components/ui/card.tsx"
import "../fonts.css"
import {Textarea} from "@/components/ui/textarea.tsx";
import * as React from "react";
import {useEffect, useRef, useState} from "react";
import {MessageInputBox} from "@/components/custom/MessageInputBox.tsx";

const messageData = [
    {
        user: "stevetosak",
        content: "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi ar",
        date: "2025/07/01",
        userImage: "../public/vite.svg"
    },
    {user: "ivan", content: "message content herer as sa", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "peco", content: "message content herer as sa", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "trajan", content: "message content herer as sa", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "stevetosak", content: "messageasdasddasd ", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "stevetosak", content: "messageasdasddasd ", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "stevetosak", content: "messageasdasddasd ", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "stevetosak", content: "messageasdasddasd ", date: "2025/07/01", userImage: "../public/vite.svg"},
    {user: "stevetosak", content: "messageasdasddasd ", date: "2025/07/01", userImage: "../public/vite.svg"},
]

export const ChannelPage = () => {

    const [messages, setMessages] = useState<{
        user: string,
        content: string,
        date: string,
        userImage: string
    }[]>(messageData)

    const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
        const content = e.currentTarget.value;
        if (e.key === "Enter" && !e.shiftKey && content.trim() !== '') {
            e.preventDefault();
            handleSendMessage(content)
            e.currentTarget.value = ''
        }
    }

    const handleSendMessage = (messageContent: string) => {
        setMessages(prevMessages => [...prevMessages, {
            user: "newMessageSender",
            content: messageContent,
            date: new Date().toDateString(),
            userImage: ""
        }])
    }

    const cardContainerRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        if (cardContainerRef.current) {
            cardContainerRef.current.scrollTop = cardContainerRef.current.scrollHeight;
        }
    }, [messages])

    return (
        <main className={"flex kanit-light border-none"}>
            <div className={"w-[300px] h-[600px]"}>
                sidebar here
            </div>
            <Card className={"bg-background-main border-none p-2"}>
                <CardHeader className={"border-b-1 border-gray-800 mx-2 flex items-center"}>
                    <h1 className={"p-2"}>General</h1>
                </CardHeader>
                <div ref={cardContainerRef} className={"max-h-[50vh] overflow-y-auto"}>
                    <CardContent>
                        <MessagesContainer messages={messages}></MessagesContainer>
                    </CardContent>
                </div>
                <CardFooter className="p-4 border-t border-gray-800 bg-background-main">
                    <div className="flex w-full items-center space-x-2">
                        <MessageInputBox handleKeyDown={handleKeyDown}/>
                    </div>
                </CardFooter>


            </Card>
            <div className={"w-[300px] h-[600px]"}>
                sidebar here
            </div>
        </main>
    )
}