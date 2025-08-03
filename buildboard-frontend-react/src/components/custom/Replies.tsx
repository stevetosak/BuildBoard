import type {ThreadData} from "@/types.ts";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import  {type SetStateAction} from "react";
import * as React from "react";

export const Replies = ({ threads,setRepliesData }: { threads?: ThreadData[], className?: string,setRepliesData:React.Dispatch<SetStateAction<ThreadData[]>>}) => {
    return (
        <div className="w-full">
            {threads?.map((thr, idx) => (
                <div
                    key={idx}
                    className={`mt-5`}
                    style={{ paddingLeft: `${thr.level * 1.5}rem` }}
                >
                    <DiscussionThreadView className="gap-1" data={thr} setRepliesData={setRepliesData} />
                </div>
            ))}
        </div>
    );
};
