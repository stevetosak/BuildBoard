import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
} from "react-router-dom";
import {TopicPage} from "@/pages/TopicPage";

export const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path={"/"}>{/*    tuka vo element ke napreme eden layout element za consistent ui*/}
            <Route path={"topics/:topicName"} element={<TopicPage/>}></Route>
            <Route path={"projects/:id/"}>
                <Route path={"channels/:channelName"}></Route>
                <Route path={"requests"}></Route>
            </Route>
            <Route path={":username/profile"}></Route>
            <Route path={"/about"}></Route>
        </Route>
    )
)