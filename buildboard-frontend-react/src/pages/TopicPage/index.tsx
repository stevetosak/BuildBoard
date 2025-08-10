import DiscussionThread from "@/pages/TopicPage/ui/DiscussionThread";
import type {ThreadData} from "@/pages/TopicPage/data/fetchReplies";
import "@/fonts.css"

const rootThread: ThreadData = {
    user: "stevetosak",
    content: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
    depth: 0
}

export const TopicPage = () => {
    return (
        <main className={"flex flex-col justify-center items-center kanit-light"}>
            <div className={"mt-2"}>
                <DiscussionThread data={rootThread} isRoot={true}/>
            </div>
        </main>
    )
}