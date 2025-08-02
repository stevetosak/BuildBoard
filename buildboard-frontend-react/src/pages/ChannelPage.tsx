import { MessagesContainer } from "@/components/MessagesContainer.tsx"
import {Card, CardContent, CardFooter, CardHeader} from "@/components/ui/card.tsx"
import "../fonts.css"
import {Textarea} from "@/components/ui/textarea.tsx";
import {Button} from "@/components/ui/button.tsx";

const messageData = [
    {user: "stevetosak", content: "\"Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi ar", date: "2025/07/01",userImage:"../public/vite.svg"},
    {user: "ivan", content: "message content herer as sa", date: "2025/07/01",userImage:"../public/vite.svg"},
    {user: "peco", content: "message content herer as sa", date: "2025/07/01",userImage:"../public/vite.svg"},
    {user: "trajan", content: "message content herer as sa", date: "2025/07/01",userImage:"../public/vite.svg"},
    {user: "stevetosak", content: "messageasdasddasd ", date: "2025/07/01",userImage:"../public/vite.svg"},
]

export const ChannelPage = () => {
    return (
        <main className={"flex kanit-light border-none"}>
            <div className={"w-[300px] h-[600px]"}>
                sidebar here
            </div>
            <Card className={"bg-background-main border-none p-2"}>
                <CardHeader className={"border-b-1 border-gray-800 mx-2 flex items-center"}>
                    <h1 className={"p-2"}>General</h1>
                </CardHeader>
                <CardContent>
                    <MessagesContainer messages={messageData}></MessagesContainer>
                </CardContent>
                <CardFooter className="p-4 border-t border-gray-800 bg-background-main">
                    <div className="flex w-full items-center space-x-2">
                        <Textarea
                            placeholder="Type your message..."
                            className="flex-1 resize-none rounded-xl border border-background-gray bg-background-card
                            text-white px-4 py-2 text-sm shadow-sm placeholder:text-foreground-gray
                            focus:outline-none focus-visible:ring-0 focus:border-0 focus:border-r-3 focus:border-accent-2"
                        />
                    </div>
                </CardFooter>


            </Card>
            <div className={"w-[300px] h-[600px]"}>
                sidebar here
            </div>
        </main>
    )
}