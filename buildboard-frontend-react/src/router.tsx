import {
    createBrowserRouter,
    createRoutesFromElements,
    Route,
} from "react-router-dom";
import {TopicPage} from "@/pages/TopicPage.tsx";
import {ChannelPage} from "@/pages/ChannelPage.tsx";
import {api} from "@/services/apiconfig.ts";
import type {ThreadData, ThreadElement, ThreadResponse} from "@/types.ts";
import HomePage from "@pages/HomePage";
import LandingPage from "@pages/LandingPage";
import LoginPage from "@pages/Login";
import validateUser  from "@pages/Login/data/validateUser.tsx";
import Register from "@pages/Register";
import registerUser from "@pages/Register/data/registerUser.tsx";
import SecurityOutlet from "./components/auth/security-context-outlet";

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
            <Route
                path={"topics/:topicName"}></Route>
            <Route path={"projects/:projectName/"}>
                <Route path={"channels/:channelName"}></Route>
                <Route  path={"requests"}></Route>
            </Route>
            <Route path={":username/profile"}></Route>

            <Route path={"login"} element={<LoginPage/>} action={validateUser} />
            <Route path={'register'} element={<Register/>} action={registerUser} />
        </Route>,
    ),
);
