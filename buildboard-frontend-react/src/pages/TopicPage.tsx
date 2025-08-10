import type {ThreadData} from "@/types.ts";
import "../fonts.css"
import {useLoaderData} from "react-router-dom";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import {useEffect, useState} from "react";

const populate = (replies: ThreadData[]) => {
    const map = new Map<number,ThreadData[]>
    replies.forEach(r => {
        if(map.get(r.level) == undefined) map.set(r.level,[])
        map.get(r.level)?.push(r)
    })
    return map
}

export const TopicPage = () => {
    const {topic,replies} = useLoaderData<{topic:ThreadData, replies: ThreadData[]}>();
    const [threadLevelMap,setThreadLevelMap] = useState<Map<number,ThreadData[]>>(populate(replies))

    return (
        <main className={"flex flex-col justify-center items-center kanit-light"}>
            <div className={"mt-2 min-w-1/2"}>
                <DiscussionThreadView data={topic} threadLevelMap={threadLevelMap} setThreadLevelMap={setThreadLevelMap} isRoot={true}/>
            </div>
        </main>
    )
}