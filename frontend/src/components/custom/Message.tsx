import {Card} from "@/components/ui/card.tsx";
import type {ChannelMessageDisplay, DeleteMessageDTO} from "@/types.ts";
import {handlePressEnter, messageDateFormat} from "@lib/utils/utils.ts";
import {useContext, useState} from "react";
import SecurityContext from "@context/security-context.ts";
import {Button} from "@components/ui/button.tsx";
import {Edit, SquarePen, SquarePenIcon, Trash2Icon} from "lucide-react";
import {useParams} from "react-router-dom";
import {useMessageContext} from "@pages/ChannelPage/data/MessageContext.ts";
import {
    deleteChatMessageEndpoint,
    editChatMessageEndpoint,
    sendChatMessageEndpoint
} from "@constants/message-endpoints.ts";
import {Input} from "@components/ui/input.tsx";
import * as React from "react";

export const Message = ({messageData}: {
    messageData: ChannelMessageDisplay
}) => {
    const [isEditing, setIsEditing] = useState(false)
    const {username} = useContext(SecurityContext)
    const {dispatchMessage} = useMessageContext()
    const [isHovered, setIsHovered] = useState(false)
    const {projectName, channelName} = useParams();
    const [content,setContent] = useState(messageData.content)
    const handleDelete = () => {
        const dto = {
            senderUsername: messageData.senderUsername,
            sentAt: messageData.sentAt,
            channelName: channelName!,
            projectName: projectName!,
            content: messageData.content
        }
        dispatchMessage(deleteChatMessageEndpoint, dto)
    }
    const handleEditSave = () => {
        const dto = {
            senderUsername: messageData.senderUsername,
            sentAt: messageData.sentAt,
            channelName: channelName!,
            projectName: projectName!,
            content: content
        }
        dispatchMessage(editChatMessageEndpoint, dto)
        setIsEditing(false)
    }


    const highlightStyle = ["border-l-1", "border-accent-2", "bg-gray-900"]

    return (
        <Card
            className={`bg-background-main-s1 w-full border-0 rounded-l text-white flex-row items-start 
            ${isEditing ? highlightStyle.join(" ") : ""}
            ${highlightStyle.map(cls => `hover:${cls}`).join(" ")}`

        }
            onMouseEnter={() => setIsHovered(true)}
            onMouseLeave={() => setIsHovered(false)}
        >
            <div>
                <img
                    src={"/vite.svg"}
                    className="user-avatar ml-2"
                    alt="user profile pic"
                />
            </div>

            <div className="w-full">
                <div className="w-full space-x-2 text-sm flex justify-between items-center p-3">
                    <div className="space-x-2">
                        <span>{messageData.senderUsername}</span>
                        <span className="mr-2">{messageDateFormat(messageData.sentAt)}</span>
                    </div>

                    {isHovered && username === messageData.senderUsername && (
                        <div className="mr-2 flex space-x-1">
                            <Button variant="ghost" className="hover:bg-gray-800" onClick={() => setIsEditing(prevState => !prevState)}>
                                <SquarePen width={10} height={10}/>
                            </Button>
                            <Button variant="ghost" className="hover:bg-gray-800" onClick={handleDelete}>
                                <Trash2Icon width={10} height={10}/>
                            </Button>
                        </div>
                    )}
                </div>
                {isEditing ? (
                    <div className="mt-2">
                        <Input
                            type="text"
                            onChange={(e) => setContent(e.target.value)}
                            value={content}
                            className="border-b border-gray-500/40 bg-transparent px-2 py-1 text-sm focus:border-gray-400 focus:ring-0"
                            onKeyDown={(e) => {
                                handlePressEnter<HTMLInputElement>(e,handleEditSave)
                            }}
                        />
                    </div>
                ) : (
                    <div className="text-start mt-2">{messageData.content}</div>
                )}


            </div>
        </Card>
    );
}