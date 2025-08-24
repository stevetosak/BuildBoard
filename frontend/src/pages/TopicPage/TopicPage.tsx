import type { ThreadElement, ThreadResponse} from "@/types.ts";
import "../../fonts.css"
import {useLoaderData} from "react-router-dom";
import {DiscussionThreadView} from "@components/custom/DiscussionThreadView.tsx";

import {api, apiPostAuthenticated} from "@lib/utils/api.ts";
import {useState} from "react";
import { ThreadTree } from "@lib/thread-tree/thread-tree";



export const TopicPage = () => {
    const topicResponse= useLoaderData<ThreadResponse>();
    const [tree,setTree] = useState<ThreadTree>(ThreadTree.fromResponse(topicResponse))// const tree = new ThreadTree(topicResponse)
    tree.display()

    const handleLoadReplies = (threadResponse:ThreadResponse) => {
        const branch = ThreadTree.fromResponse(threadResponse)
        const newTree = new ThreadTree(tree.root)
        newTree.addChildren(branch)
        setTree(newTree)
    }
    const handleReply =  async (targetNodeIdx:number,newThread: ThreadElement) => {
        const newTree = new ThreadTree(tree.root)
        newTree.addChild(targetNodeIdx,newThread)
        setTree(newTree)
        console.log("new thread",newThread)
        const response = await apiPostAuthenticated<ThreadElement>(`/replies/add`,newThread)
        console.log("ADDED REPLY");
        console.log(response.data)
    }
    const handleDelete =  async (id: number) => {
        const newTree = new ThreadTree(tree.root);
        newTree.delete(id);
        setTree(newTree)
        await apiPostAuthenticated(`/replies/delete?id=${id}`)
    }

    //todo refactor jwt, morat da ojt po claim userid za da

    return (
        <main className={"flex flex-col justify-center items-center kanit-light"}>
            <div className={"mt-2 mx-auto min-w-3/4"}>
                <DiscussionThreadView tree={tree} updateTree={handleLoadReplies} handleReply={handleReply} node={tree.root} handleDelete={handleDelete} isRoot={true}/>
            </div>
        </main>
    )
}