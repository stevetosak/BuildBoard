import {
    createBrowserRouter,
} from "react-router-dom";
import {TopicPage} from "@/pages/TopicPage.tsx";
import {ChannelPage} from "@/pages/ChannelPage.tsx";
import {api} from "@/services/apiconfig.ts";
import type {ThreadData, ThreadElement, ThreadResponse} from "@/types.ts";

export const router = createBrowserRouter([
        {
            path: "/topics/:topicName",
            Component: TopicPage,
            loader: async ({params}) => {
                const {topicName} = params;
                console.log("TOPIC NAME: " + topicName)
                const response = await api.get<ThreadResponse>(`/topics/${topicName}`)
                console.log("RESP DATA")
                console.log(response.data)
                return response.data;
            }
        },
        {
            path: "/projects/:projectName",
            children: [
                {path: "channels/:channelName", Component: ChannelPage}
            ]
        }
    ]
    // createRoutesFromElements(
    //     <Route path={"/"}>{/*    tuka vo element ke napreme eden layout element za consistent ui*/}
    //         <Route path={"topics/:topicName"} element={<TopicPage/>}></Route>
    //         <Route path={"projects/:id/"}>
    //             <Route path={"channels/:channelName"} element={<ChannelPage/>}></Route>
    //             <Route path={"requests"}></Route>
    //         </Route>
    //         <Route path={":username/profile"}></Route>
    //         <Route path={"/about"}></Route>
    //     </Route>
    // )
)