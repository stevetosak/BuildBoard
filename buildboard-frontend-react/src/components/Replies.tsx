import type {ThreadData} from "@/types.ts";
import {DiscussionThread} from "@/components/DiscussionThread.tsx";

export const Replies = ({ threads, className }: { threads?: ThreadData[], className?: string }) => {
    return (
        <div className="w-full">
            {threads?.map((thr, idx) => (
                <div
                    key={idx}
                    className={`mt-5`}
                    style={{ paddingLeft: `${thr.depth * 1.5}rem` }} // <- this does the indentation
                >
                    <DiscussionThread className="gap-1" data={thr} />
                </div>
            ))}
        </div>
    );
};
