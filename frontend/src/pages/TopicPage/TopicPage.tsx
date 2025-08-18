import type { ThreadElement, ThreadResponse} from "@/types.ts";
import "../../fonts.css"
import {useLoaderData} from "react-router-dom";
import {DiscussionThreadView} from "@components/custom/DiscussionThreadView.tsx";
import {useState} from "react";
import { ThreadTree} from "@lib/utils.ts";
import {api} from "@/services/apiconfig.ts";
import {getAuthHeader, getToken} from "@shared/security-utils.ts";



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
    const handleAddReply =  async (targetNodeIdx:number,newThread: ThreadElement) => {
        const response = await api.post<ThreadElement>(`/replies/add`,newThread,{
            headers:{
                Authorization: `Bearer ${getToken()}`
            }
        })
        const newTree = new ThreadTree(tree.root)
        newTree.addChild(targetNodeIdx,response.data)
        setTree(newTree)

    }

    //todo refactor jwt, morat da ojt po claim userid za da

    return (
        <main className={"flex flex-col justify-center items-center kanit-light"}>
            <div className={"mt-2 min-w-1/2"}>
                <DiscussionThreadView tree={tree} updateTree={handleLoadReplies} handleAddReply={handleAddReply} node={tree.root} isRoot={true}/>
            </div>
        </main>
    )
}