import TopicSection from "@/components/HomePage/TopicSection.tsx";
import SideBar from "@/components/HomePage/SideBar";
import NotificationPanel from "@/components/HomePage/NotificationPanel";
import {useLoaderData} from "react-router-dom";
import {User} from "lucide-react";
import {
    HoverCard,
    HoverCardContent,
    HoverCardTrigger,
} from "@/components/ui/hover-card";
import ButtonGroup from "@/components/HomePage/ButtonGroup.tsx";

type User = {
    username: string; // Sega zasega
} | null;

export type SingleColorCtx = {
    registered : [boolean],
    setFalse : () => void,
    setTrue : () => void
}

export const loader = async () => {
    return null;
};


const HomePage = () => {
    const user = useLoaderData() as User;

    return (
        <main className="w-full grid bg-bg-1 grid-cols-3 h-[100vh] ps-3 pt-3 pe-3 ">
            <SideBar/>
            <TopicSection/>
            <div className="flex gap-2.5 justify-end pe-3">
                {user == null ? (
                    <ButtonGroup />
                ) : (
                    <>
                        {/*TODO: make svg bigger*/}
                        {/*TODO: define typography*/}
                        {/*TODO: register popup */}
                        {/*TODO: single icon visible*/}
                        <span className={`text-white hover:text-accent`}>{user.username}</span>
                            <HoverCard>
                                <HoverCardTrigger>
                                    <User className={`text-white hover:text-accent`}/>
                                </HoverCardTrigger>
                                <HoverCardContent className="h-fit">See profile</HoverCardContent>
                            </HoverCard>
                    </>
                )
                }
                <NotificationPanel/>
            </div>
        </main>
    );
};

export default HomePage;
