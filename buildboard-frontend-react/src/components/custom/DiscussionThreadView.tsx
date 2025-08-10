import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
} from "@/components/ui/card.tsx";
import {Check, CircleEllipsis, Reply, X} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import type {ThreadData} from "@/types.ts";
import {Replies} from "@/components/custom/Replies.tsx";
import {type SetStateAction, useEffect, useState} from "react";
import {AnimatePresence, motion} from "framer-motion";
import * as React from "react";
import {MessageInputBox} from "@/components/custom/MessageInputBox.tsx";
import {api} from "@/services/apiconfig.ts";

export const DiscussionThreadView = ({
                                         className,
                                         data,
                                         threadLevelMap,
                                         setThreadLevelMap,
                                         isRoot = false,
                                     }: {
    className?: string;
    data: ThreadData;
    isRoot?: boolean;
    threadLevelMap: Map<number, ThreadData[]>;
    setThreadLevelMap: React.Dispatch<SetStateAction<Map<number, ThreadData[]>>>
}) => {


    const [replying, setReplying] = useState(false);
    const [replies, setReplies] = useState<ThreadData[]>([])
    const [displayReplies, setDisplayReplies] = useState<boolean>(isRoot);

    useEffect(() => {
        setReplies(() => threadLevelMap?.get(data.level + 1) ?? [])
    }, [threadLevelMap]);


    const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
        const content = e.currentTarget.value;
        if (e.key === "Enter" && !e.shiftKey && content.trim() !== '') {
            e.preventDefault();
            handleReply(content);
            setReplying(false)
        }
    }


    const handleDisplayReplies = async () => {
        if (!replies || replies.length === 0) {
            await loadReplies()
        }
        setDisplayReplies(prevState => !prevState)
    }

    const loadReplies = async () => {
        const response = await api.get<ThreadData[]>(`/replies?threadId=${data.id}`)
        if (response.data.length > 0) {
            setThreadLevelMap((map) => map.set(data.level + 1, response.data))
        }

    }

    const handleReply = (content: string) => {
        setThreadLevelMap(prevMap => {
            const newMap = new Map(prevMap);

            if (!newMap.has(data.level + 1)) {
                newMap.set(data.level + 1, []);
            }

            newMap.get(data.level + 1)!.push({
                user: data.user,
                content,
                level: data.level + 1,
                date: data.date,
            });

            return newMap;
        });
    };


    return (
        <div
            className={`${
                displayReplies ? "border-l-2 border-gray-800 pl-3" : ""
            } m-5 flex flex-col max-w-2xl gap-2`}
        >
            <AnimatePresence>
                <Card
                    className={`border-0 border-l-4 border-accent-2 rounded-xl bg-background-card text-[#eaeaea] ${className}`}
                >
                    <CardHeader className="flex justify-between items-start gap-2">
                        <div className="flex gap-3 items-center">
                            <img
                                className="w-10 h-10 rounded-full border-2 border-gray-700 p-1"
                                src="/vite.svg"
                                alt="User Avatar"
                            />
                            <span className="text-sm font-medium text-white">
              {data.user.username}
            </span>
                        </div>
                        <span className="text-sm text-muted-foreground mt-1">25.08.2025</span>
                    </CardHeader>

                    <CardContent className="text-left">
                        {isRoot ? (
                            <h2 className="text-lg font-semibold leading-snug">
                                {data.content}
                            </h2>
                        ) : (
                            <p className="text-sm text-gray-300">{data.content}</p>
                        )}
                    </CardContent>

                    <CardFooter className="flex justify-between items-center pt-2">
                        <div className="flex gap-6">
                            <Button className="flex items-center gap-1 bg-background-card hover:bg-gray-900">
                                <Check size={16}/>
                                <span className={"text-sm text-muted-foreground"}>23</span>
                            </Button>
                            <Button
                                className="flex items-center gap-1 bg-background-card hover:bg-gray-900 hover:border-s-muted hover:border-1">
                                <X size={16}/>
                                <span className={"text-sm text-muted-foreground"}>23</span>
                            </Button>
                        </div>

                        <Button
                            size="icon"
                            className="hover:-translate-y-0.5 hover:bg-gray-900 transition bg-background-card"
                            onClick={() => setReplying((prev) => !prev)}
                        >
                            <Reply/>
                        </Button>
                    </CardFooter>


                    {replying && (
                        <motion.div
                            initial={{x: "100%", opacity: 0}}
                            animate={{x: 0, opacity: 1}}
                            exit={{x: "100%", opacity: 0}}
                            transition={{type: "spring", stiffness: 300, damping: 30}}
                            className="px-4 mt-2"
                        >
                            <MessageInputBox handleKeyDown={handleKeyDown}/>
                        </motion.div>
                    )}

                </Card>
            </AnimatePresence>

            <div className="flex items-start">
                <Button
                    size="sm"
                    className="hover:bg-gray-900 hover:-translate-y-0.5 hover:translate-x-0.5 transition bg-background-main"
                    onClick={() => handleDisplayReplies()}
                >
                    <CircleEllipsis className="mr-2" size={16}/>
                    {!displayReplies
                        ? `View more replies`
                        : "Hide"}
                </Button>
            </div>

            {displayReplies &&
                <Replies replies={replies} threadLevelMap={threadLevelMap} setThreadLevelMap={setThreadLevelMap}/>}
        </div>
    );
};
