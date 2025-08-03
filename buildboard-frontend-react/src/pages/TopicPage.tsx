import type {ThreadData} from "@/types.ts";
import "../fonts.css"
import {useLoaderData} from "react-router-dom";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import {useEffect, useState} from "react";



export const TopicPage = () => {
    const {topic,replies} = useLoaderData<{topic:ThreadData, replies: ThreadData[]}>();
    const [repliesData, setRepliesData] = useState<ThreadData[]>(replies ?? []);


    return (
        <main className={"flex flex-col justify-center items-center kanit-light"}>
            <div className={"mt-2 min-w-1/2"}>
                <DiscussionThreadView data={topic} replies={repliesData} setRepliesData={setRepliesData} isRoot={true}/>
            </div>
        </main>
    )
}