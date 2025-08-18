import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
} from "@/components/ui/card.tsx";
import {
    Check,
    ChevronDown,
    ChevronDownCircle,
    ChevronUp,
    ChevronUpCircle,
    CircleEllipsis,
    Reply,
    X,
    XCircle
} from "lucide-react";
import {Button} from "@/components/ui/button.tsx";
import type {ThreadResponse, ThreadElement} from "@/types.ts";
import {useContext, useState} from "react";
import {AnimatePresence, motion} from "framer-motion";
import * as React from "react";
import {MessageInputBox} from "@/components/custom/MessageInputBox.tsx";
import {api, apiPostAuthenticated} from "@lib/utils/api.ts";
import {type ThreadNode, ThreadTree} from "@/lib/utils.ts";
import {useJwt} from "react-jwt";
import {getToken} from "@shared/security-utils.ts";
import SecurityContext from "@context/security-context.ts";

export type DiscussionThreadViewProps = {
    className?: string;
    node: ThreadNode;
    isRoot?: boolean;
    tree: ThreadTree,
    handleReply: (targetNodeIdx: number, child: ThreadElement) => void
    updateTree: (threadResponse: ThreadResponse) => void
    handleDelete: (id: number) => Promise<void>
}

export const DiscussionThreadView = ({
                                         className,
                                         node,
                                         handleReply,
                                         updateTree,
                                         tree,
                                         isRoot,
                                         handleDelete
                                     }: DiscussionThreadViewProps) => {


    const [replying, setReplying] = useState(false);
    const userAuthContext = useContext(SecurityContext)
    const [displayReplies, setDisplayReplies] = useState<boolean>(true);
    const [collapseChildren, setCollapseChildren] = useState(false)
    const replies = node.children
    const remainingReplies = node.element.numReplies - replies.length
    console.log("Replies")
    console.log(replies)

    // todo refactor security context


    const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
        const content = e.currentTarget.value;
        if (e.key === "Enter" && !e.shiftKey && content.trim() !== '') {
            console.log("CONTEXT")
            console.log(userAuthContext)

            const newThread: ThreadElement = {
                parentId: node.element.id!,
                level: node.element.level + 1,
                content: content,
                user: {id: userAuthContext!.id, username: userAuthContext!.username, avatarUrl: "nema"},
                numLikes: 0,
                numReplies: 0, type: "discussion", createdAt: Date.now(),
                status: "active",
            }

            e.preventDefault();
            handleReply(node.element.id!, newThread)
            setReplying(false)
        }
    }


    const handleDisplayReplies = async () => {
        if(remainingReplies > 0){
            await handleLoadReplies()
        }else {
            setDisplayReplies(prevState => !prevState)
        }

    }


    const handleLoadReplies = async () => {
        const response = await api.get<ThreadResponse>(`/replies?threadId=${node.element.id}`)
        console.log("LOADING REPLIES FOR CURRENT NODE >>>")
        console.log(node)
        console.log("RECIEVED RESPONSE")
        console.log(response.data)
        updateTree(response.data)
    }

    const styles = {
        deleted: {
            card: "border-red-600 opacity-70"
        },
        active: {
            card: "border-accent-2"
        }
    }

    console.log("STYLES active: ", node.element.status)


    return (
        <div
            className={`${
                displayReplies ? "border-l-2 border-gray-800 pl-3" : ""
            } m-5 flex flex-col max-w-2xl gap-2 w-full`}
        >
            <AnimatePresence>
                <Card
                    className={`border-0 border-l-4 rounded-xl bg-background-card text-[#eaeaea] ${className} ${styles[`${node.element.status}`].card}`}
                >
                    <CardHeader className="flex justify-between items-start gap-2">
                        <div className="flex gap-3 items-center">
                            <img
                                className="w-10 h-10 rounded-full border-2 border-gray-700 p-1"
                                src="/vite.svg"
                                alt="User Avatar"
                            />
                            <span className="text-sm font-medium text-white">
              {node.element.user.username}
            </span>
                        </div>
                        <span
                            className="text-sm text-muted-foreground mt-1">{new Date(node.element.createdAt).toDateString()}</span>
                    </CardHeader>

                    <CardContent className="text-left">
                        {isRoot ? (
                            <h2 className="text-lg font-semibold leading-snug">
                                {node.element.content}
                            </h2>
                        ) : (
                            <p className="text-sm text-gray-300">{node.element.content}</p>
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

                        {node.element.status === "active" && <div>
                            <Button
                                size="icon"
                                className="hover:-translate-y-0.5 hover:bg-gray-900 transition bg-background-card"
                                onClick={() => setReplying((prev) => !prev)}
                            >
                                <Reply/>
                            </Button>
                            {node.element.user.id == userAuthContext?.id && (
                                <Button
                                    size="icon"
                                    className="hover:-translate-y-0.5 hover:bg-gray-900 transition bg-background-card"
                                    onClick={() => handleDelete(node.element.id!)}
                                >
                                    <XCircle/>
                                </Button>
                            )}
                        </div>}

                    </CardFooter>


                    {replying && ( <div className={"px-4 mt-2"}>
                        <MessageInputBox handleKeyDown={handleKeyDown}/>
                        </div>
                    )}

                </Card>
            </AnimatePresence>

            <div className={"flex flex-row items-center"}>
                {node.element.numReplies > 0 &&
                    <div className="flex items-start">
                        <Button
                            size="sm"
                            className="hover:bg-gray-900 hover:-translate-y-0.5 hover:translate-x-0.5 transition bg-background-main rounded-3xl"
                            onClick={() => handleDisplayReplies()}
                        >
                            {displayReplies && remainingReplies <= 0 ? <ChevronUpCircle /> : <ChevronDownCircle/>}
                        </Button>
                    </div>
                }
                <div>
                    <span className={"text-primary-foreground"}>{remainingReplies > 0 && displayReplies &&  `${remainingReplies} more replies`}</span>
                </div>
            </div>


            {displayReplies &&
                <div className="w-full">
                    {replies?.map((thr, idx) => (
                        <div
                            key={idx}
                            className={`mt-5`}
                            style={{paddingLeft: `${thr.element.level}rem`}}
                        >
                            <DiscussionThreadView className="gap-1" node={thr} tree={tree} updateTree={updateTree}
                                                  handleReply={handleReply} handleDelete={handleDelete}/>
                        </div>
                    ))}
                </div>
            }
        </div>
    );
};
