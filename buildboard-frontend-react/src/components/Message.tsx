import {Card} from "@/components/ui/card.tsx";

export const Message = ({messageData}: {
    messageData: { user: string, content: string, date: string, userImage: string }
}) => {
    return (
        <Card className={"bg-background-main-s1 w-full border-0 hover:border-l-1 hover:border-accent-2 rounded-l text-white flex-row items-start hover:bg-gray-900"}>
            <div>
                <img src={"/vite.svg"} className={"user-avatar ml-2"} alt={"user profile pic"}/>
            </div>

            <div className={"w-full"}>
                <div className={"w-full space-x-2 flex items-a text-sm"}>
                    <span>{messageData.user}</span>
                    <span className={"mr-2"}>{messageData.date}</span>
                </div>
                <div className={"text-start mt-2"}>
                    {messageData.content}
                </div>
            </div>

        </Card>
    )
}