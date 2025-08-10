import type {ThreadData} from "@/types.ts";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import  {type SetStateAction} from "react";
import * as React from "react";

export const Replies = ({ replies,threadLevelMap,setThreadLevelMap }:
                        {
                            replies?: ThreadData[],
                            threadLevelMap: Map<number,ThreadData[]>
                            className?: string,
                            setThreadLevelMap:React.Dispatch<SetStateAction<Map<number,ThreadData[]>>>}) => {
    return (
        <div className="w-full">
            {replies?.map((thr, idx) => (
                <div
                    key={idx}
                    className={`mt-5`}
                    style={{ paddingLeft: `${thr.level * 1.5}rem` }}
                >
                    <DiscussionThreadView className="gap-1" data={thr} threadLevelMap={threadLevelMap} setThreadLevelMap={setThreadLevelMap} />
                </div>
            ))}
        </div>
    );
};
