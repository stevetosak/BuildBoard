import {DiscussionThread} from "@/components/DiscussionThread.tsx";
import type {ThreadData} from "@/types.ts";
import {Replies} from "@/components/Replies.tsx";

const mockReplies: ThreadData[] = [
    {
        user: 'tosak',
        content: "lorem ipsum dolor sit amet camet sergej dunda pur",
        depth: 1
    },
    {
        user: 'viktssk223',
        content: "sit amet camet sergej dunda pur",
        depth: 1
    },
    {
        user: 'koska55123',
        content: "sit amet camet sergej dunda pur lambda poppy sa diddy kloppy",
        depth: 1
    }
]

const rootThread: ThreadData = {
    user: "stevetosak",
    content: 'How much wood would a woodchuck chuck if a woodchuck could chuck wood?',
    depth: 0
}

export const TopicPage = () => {
    return (
        <main className={"flex flex-col justify-center items-center"}>
            <div className={"w-1/2 mt-2"}>
                <DiscussionThread data={rootThread}/>
                <Replies threads={mockReplies}/>
            </div>
        </main>
    )
}