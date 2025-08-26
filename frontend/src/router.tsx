import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
} from "react-router-dom";
import {TopicPage} from "@pages/TopicPage/TopicPage.tsx";
import HomePage from "@pages/HomePage";
import LandingPage from "@pages/LandingPage";
import LoginPage from "@pages/Login";
import validateUser  from "@pages/Login/data/validateUser.tsx";
import Register from "@pages/Register";
import registerUser from "@pages/Register/data/registerUser.tsx";
import SecurityOutlet from "./components/auth/security-context-outlet";
import {topicLoader} from "@pages/TopicPage/data/loader.ts";
import {ChannelPage} from "@pages/ChannelPage/ChannelPage.tsx";
import {channelLoader} from "@pages/ChannelPage/data/loader.ts";
import {default as ProjectTopic} from "@pages/ProjectPage/ui/topics";
import {default as ProjectDescription} from "@pages/ProjectPage/ui/description";
import {default as ProjectManagment} from "@pages/ProjectPage/ui/managment";
import ProjectPage from "@pages/ProjectPage";

export const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path={"/"} element={<SecurityOutlet/>}>
            <Route
                index
                element={<LandingPage/>}
            />
            <Route
                path="/homepage"
                element={<HomePage/>}
            />
            <Route loader={topicLoader}
                path={"topics/:topicName"} element={<TopicPage/>}></Route>
            <Route path={"projects/:projectName"} element={<ProjectPage/>}>
                    <Route path="topics" element={<ProjectTopic/>}/>
                    <Route path="description" element={<ProjectDescription/>}/>
                    <Route path={"channels/:channelName"} loader={channelLoader} element={<ChannelPage/>}></Route>
                    <Route path="manamgnet/:projectName" element={<ProjectManagment />}/>
                    <Route  path={"requests"}></Route>
            </Route>
            <Route path={":username/profile"}></Route>
            <Route path={"login"} element={<LoginPage/>} action={validateUser} />
            <Route path={'register'} element={<Register/>} action={registerUser} />
        </Route>,
    ),
);
