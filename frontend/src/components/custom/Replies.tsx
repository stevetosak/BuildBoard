import type {ThreadData, ThreadResponse, ThreadElement} from "@/types.ts";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import type {ThreadNode, ThreadTree} from "@lib/thread-tree/thread-tree.ts";

export const Replies = ({ replies,tree,updateTree,handleReply,handleDelete }:
                        {
                            replies?: ThreadNode[],
                            tree: ThreadTree,
                            updateTree: (threadResponse: ThreadResponse) => void,
                            className?: string,
                            handleReply: (targetNodeIdx: number, child: ThreadElement) => void
                            handleDelete: (id: number) => Promise<void>
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
                    <DiscussionThreadView className="gap-1" node={thr} tree={tree} updateTree={updateTree} handleDelete={handleDelete} handleReply={handleReply} />
                </div>
            ))}
        </div>
    );
};
