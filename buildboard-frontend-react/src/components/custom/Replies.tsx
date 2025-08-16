import type {ThreadData, ThreadResponse, ThreadElement} from "@/types.ts";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import  {type SetStateAction} from "react";
import * as React from "react";
import type {ThreadNode, ThreadTree} from "@/lib/utils.ts";

export const Replies = ({ replies,tree,updateTree }:
                        {
                            replies?: ThreadNode[],
                            // threadLevelMap: Map<number,ThreadView[]>
                            tree: ThreadTree,
                            updateTree: (threadResponse: ThreadResponse) => void,
                            className?: string,
                            // setThreadLevelMap:React.Dispatch<SetStateAction<Map<number,ThreadView[]>>>
                        }
) => {
    return (
        <div className="w-full">
            {replies?.map((thr, idx) => (
                <div
                    key={idx}
                    className={`mt-5`}
                    style={{ paddingLeft: `${thr.element.level * 1.5}rem` }}
                >
                    <DiscussionThreadView className="gap-1" node={thr} tree={tree} updateTree={updateTree} />
                </div>
            ))}
        </div>
    );
};
