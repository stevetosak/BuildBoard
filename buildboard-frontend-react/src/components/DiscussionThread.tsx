import {Card, CardContent, CardFooter, CardHeader} from "@/components/ui/card.tsx";
import {Check, CircleEllipsis, Reply, TextQuote, X} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import type {ThreadData} from "@/types.ts";
import {Replies} from "@/components/Replies.tsx";
import {useState} from "react";
import {Input} from "@/components/ui/input.tsx";

// todo decoupled loading da e pomegju threads: ne site naednas tuku sekoj DiscussionThread, pri kilk na show replies duri togas da gi fetchnit decata negovi


export const DiscussionThread = ({className, data}: { className?: string, data: ThreadData }) => {

    const mockReplies: ThreadData[] = [
        {
            user: "globov2",
            depth: data.depth + 1,
            content: "I am replying baby"
        },
        {
            user: 'kaliuser123',
            content: 'I use kali linux i am bed boy',
            depth: data.depth + 1
        }
    ]
    const [replying, setReplying] = useState<boolean>(false)
    const [replies, setReplies] = useState<ThreadData[]>([])
    const [displayReplies,setDisplayReplies] = useState(false)


    return (
        <div className={`ml-${data.depth}`}>
            <Card className={`border rounded-3xl ${className}`}>
                <CardHeader className={"flex justify-between"}>
                    <div className={"flex gap-2"}>
                        <div>
                            usr img
                        </div>
                        <div>
                            {data.user}
                        </div>
                    </div>
                    <div>
                        25.08.2025
                    </div>
                </CardHeader>
                <hr/>
                <CardContent>
                    {data.content}
                </CardContent>
                <CardFooter className={"flex justify-between"}>
                    <div className={"flex gap-5"}>
                        <div className={"flex justify-between "}>
                            <Check/>
                            <span>23</span>

                        </div>
                        <div className={"flex justify-between"}>
                            <X/>
                            <span>13</span>
                        </div>
                    </div>
                    <div>
                        {replying && (
                            <Input>Add your reply...</Input>
                        )}

                    </div>
                    <div>
                        <Button className={"hover:-translate-y-0.5 hover:bg-gray-300"}
                                onClick={() => console.log("click")}>
                            <Reply/>
                        </Button>
                    </div>
                </CardFooter>

            </Card>
            <div className={"flex items-baseline"}>
                <CircleEllipsis/>
                <Button onClick={() => {
                    setDisplayReplies(prevState => !prevState)
                }}>{!displayReplies ? `View ${mockReplies.length} more replies` : "Hide"}</Button>
            </div>

            {displayReplies && <Replies threads={mockReplies}/>}
        </div>
    )
}