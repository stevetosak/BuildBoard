import TopicSection from "@/components/HomePage/TopicSection.tsx";
import SideBar from "@/components/HomePage/SideBar";
import NotificationPanel from "@/components/HomePage/NotificationPanel";
import SingleColor from "@/components/HomePage/SingleColor";
import {useLoaderData} from "react-router-dom";
import {User} from "lucide-react";
import {
    HoverCard,
    HoverCardContent,
    HoverCardTrigger,
} from "@/components/ui/hover-card";
import {Button} from "@/components/ui/button.tsx";
import {Link} from "react-router-dom";

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

const singleColorContextGen = () => ({
    registered: [false],
    setFalse: () => {},
    setTrue: () => {}
} satisfies SingleColorCtx)

const ctx1 = singleColorContextGen()
const ctx2 = singleColorContextGen()

const HomePage = () => {
    const user = useLoaderData() as User;

    return (
        <main className="w-full grid bg-bg-1 grid-cols-3 h-[100vh] ps-3 pt-3 pe-3 ">
            <SideBar/>
            <TopicSection/>
            <div className="flex gap-2.5 justify-end pe-3">
                {user == null ? (
                    <SingleColor ctx={ctx1}>
                        <Button
                            onMouseEnter={_ => ctx1.setTrue()}
                            variant="outline"
                            className={`text-white ${ctx1.registered[0] ? 'text-accent' : 'text-white'} hover:text-white`}>
                            <Link to="/user/register">Register</Link>
                        </Button>
                        <Button
                            onMouseEnter={_ => ctx1.setFalse()}
                            variant="outline"
                            className={`text-white ${!ctx1.registered[0] ? 'text-accent' : 'text-white'} hover:text-white`}>
                            <Link to="/user/login">Login</Link>
                        </Button>
                    </SingleColor>
                ) : (
                    <>
                        {/*TODO: make svg bigger*/}
                        {/*TODO: define typography*/}
                        {/*TODO: register popup */}
                        {/*TODO: single icon visible*/}
                        <SingleColor ctx={ctx2}>
                            <span className={`text-white ${ctx2.registered[0] ? 'text-accent' : 'text-white'} h-fit`}>{user.username}</span>
                            <HoverCard>
                                <HoverCardTrigger>
                                    <User className={`${!ctx2.registered[0] ? 'text-accent' : 'text-white'}`}/>
                                </HoverCardTrigger>
                                <HoverCardContent className="h-fit">See profile</HoverCardContent>
                            </HoverCard>
                        </SingleColor>
                    </>
                )
                }
                <NotificationPanel/>
            </div>
        </main>
    );
};

export default HomePage;
