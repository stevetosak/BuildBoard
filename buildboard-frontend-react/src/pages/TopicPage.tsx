import type {ThreadData, ThreadElement, ThreadResponse} from "@/types.ts";
import "../fonts.css"
import {useLoaderData} from "react-router-dom";
import {DiscussionThreadView} from "@/components/custom/DiscussionThreadView.tsx";
import {useState} from "react";
import {getLevelMap, ThreadTree} from "@/lib/utils.ts";
import {api} from "@/services/apiconfig.ts";



export const TopicPage = () => {
    const topicResponse= useLoaderData<ThreadResponse>();
    const [tree,setTree] = useState<ThreadTree>(ThreadTree.fromResponse(topicResponse))
    // const tree = new ThreadTree(topicResponse)
    tree.display()

    const handleLoadReplies = (threadResponse:ThreadResponse) => {
        const branch = ThreadTree.fromResponse(threadResponse)
        const newTree = new ThreadTree(tree.root)
        newTree.addChildren(branch)
        setTree(newTree)
    }
    const handleAddReply = (targetNodeIdx:number,newThread: ThreadElement) => {
        const newTree = new ThreadTree(tree.root)
        newTree.addChild(targetNodeIdx,newThread)
        setTree(newTree)
        api.post(`/threads/reply?id=${targetNodeIdx}`,newThread)
    }

    return (
        <main className={"flex flex-col justify-center items-center kanit-light"}>
            <div className={"mt-2 min-w-1/2"}>
                <DiscussionThreadView tree={tree} updateTree={handleLoadReplies} handleAddReply={handleAddReply} node={tree.root} isRoot={true}/>
            </div>
        </main>
    )
}