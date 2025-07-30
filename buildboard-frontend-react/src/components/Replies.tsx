import type {ThreadData} from "@/types.ts";
import {DiscussionThread} from "@/components/DiscussionThread.tsx";

export const Replies = ({threads,className}: {threads?: ThreadData[],className?:string}) => {
    return (
        <div className={`w-4/5 ${threads ? `mr-${threads[0].depth}` : ''}`}>
            {threads?.map((thr,idx) => <DiscussionThread className={"gap-1 mt-5"} data={thr}/>)}
        </div>
    )
}