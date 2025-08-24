import {MessagesContainer} from "@components/custom/MessagesContainer.tsx"
import {Card, CardContent, CardFooter, CardHeader} from "@components/ui/card.tsx"
import "../../fonts.css"
import * as React from "react";
import {useCallback, useContext, useEffect, useMemo, useRef, useState} from "react";
import {MessageInputBox} from "@components/custom/MessageInputBox.tsx";
import {useWebSocketService} from "@lib/ws/web-socket-impl.ts";
import {useLoaderData, useParams} from "react-router-dom";
import SecurityContext from "@context/security-context.ts";
import type {ChannelMessageDisplay, ChannelMessageDto, ChannelMessageEvent} from "@/types.ts";
import type {UserAuth} from "@shared/security-utils.ts";
import {MessageContext} from "@pages/ChannelPage/data/MessageContext.ts";
import {useMessageService} from "@pages/ChannelPage/data/useMessageService.ts";
import lodash from "lodash"

const webSocketUrl = "http://localhost:8080/channel-websocket"

export const ChannelPage = () => {
    const {projectName, channelName} = useParams()
    const messageData = useLoaderData<ChannelMessageDisplay[]>()
    const {username}: UserAuth = useContext(SecurityContext)

    const {messages, messageEventHandler, currentlyTyping} = useMessageService(messageData)

    const topicPath = `/topic/${projectName}/${channelName}`
    const {connect, subscribe, send, unsubscribe, disconnect} = useWebSocketService(
        webSocketUrl,
        () => {
            console.log('Connected!')
            console.log("Subscribing to topic: " + topicPath)
            subscribe(topicPath, messageEventHandler)
        },
        (error) => console.log('WebSocket Error:', error)
    );


    useEffect(() => {
        connect();
        return () => {
            unsubscribe(topicPath);
            disconnect();
        };
    }, [])



    const handleTyping = useMemo(
        () =>
            lodash.debounce(() => {
                const dto: ChannelMessageDto = {
                    senderUsername: username,
                    sentAt: "",
                    channelName: channelName!,
                    projectName: projectName!,
                    content: ""
                };
                send("/app/chat/type", dto);
            }, 150),
        []
    );



    const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
        const content = e.currentTarget.value;
        if (e.key === "Enter" && !e.shiftKey && content.trim() !== '') {
            e.preventDefault();
            handleSendMessage(content)
            e.currentTarget.value = ''
        }
    }

    const handleSendMessage = (content: string) => {
        const messageDto: ChannelMessageDto = {
            senderUsername: username,
            sentAt: new Date().toISOString(),
            content: content,
            projectName: projectName!,
            channelName: channelName!
        }
        send("/app/chat/send", messageDto)
    }


    const cardContainerRef = useRef<HTMLDivElement>(null)

    useEffect(() => {
        if (cardContainerRef.current) {
            cardContainerRef.current.scrollTop = cardContainerRef.current.scrollHeight;
        }
    }, [messages])

    return (
        <main className={"flex justify-center items-center kanit-light border-none w-full"}>
            <Card className={"bg-background-main border-none p-2 max-w-7xl min-w-2xl w-5/7"}>
                <CardHeader className={"border-b-1 border-gray-800 mx-2 flex items-center"}>
                    <h1 className={"p-2"}>General</h1>
                </CardHeader>
                <div ref={cardContainerRef} className={"max-h-[50vh] overflow-y-auto"}>
                    <CardContent>
                        <MessageContext value={{
                            dispatchMessage: send
                        }}>
                            <MessagesContainer messages={messages}></MessagesContainer>
                        </MessageContext>
                    </CardContent>
                </div>
                <div className={"text-end"}>
                    {currentlyTyping !== "" && currentlyTyping !== username &&
                        <div>{currentlyTyping} is typing...</div>}
                </div>
                <CardFooter className="p-4 border-t border-gray-800 bg-background-main">
                    <div className="flex w-full items-center space-x-2">
                        <MessageInputBox handleKeyDown={handleKeyDown} handleTyping={handleTyping}/>
                    </div>
                </CardFooter>
            </Card>
        </main>
    )
}