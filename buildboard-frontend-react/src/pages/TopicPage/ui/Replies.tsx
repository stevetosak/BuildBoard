import type {ThreadData} from "@/pages/TopicPage/data/fetchReplies";
import DiscussionThread from "@/pages/TopicPage/ui/DiscussionThread";

export const Replies = ({ threads }: { threads?: ThreadData[], className?: string }) => {
    return (
        <div className="w-full">
            {threads?.map((thr, idx) => (
                <div
                    key={idx}
                    className={`mt-5`}
                    style={{ paddingLeft: `${thr.depth * 1.5}rem` }}
                >
                    <DiscussionThread className="gap-1" data={thr} />
                </div>
            ))}
        </div>
    );
};
