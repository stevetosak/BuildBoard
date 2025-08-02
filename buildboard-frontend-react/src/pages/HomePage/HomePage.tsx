import {useLoaderData} from "react-router-dom";
import { User as UserIcon} from "lucide-react";
import {
    HoverCard,
    HoverCardContent,
    HoverCardTrigger,
} from "@components/ui/hover-card";
import ButtonGroup from "@components/HomePage/ButtonGroup.tsx";
import type {User} from '@pages/HomePage/utils'


export type SingleColorCtx = {
    registered : [boolean],
    setFalse : () => void,
    setTrue : () => void
}

const HomePage = () => {
    const user = useLoaderData() as User;


    return (
        <main className="w-full grid bg-bg-1 px-0 h-[100vh] ps-3 py-3 pe-3 " style={{gridTemplateColumns : '1fr 3fr 1fr'}} >
            <section className="border-r-2 border-r-non-accent flex flex-col"></section>
            <section></section>
            <section className="flex border-l-2 border-l-white gap-2.5 justify-end pe-3">
                {user == null ? (
                    <ButtonGroup />
                ) : (
                    <>
                        {/* TODO: najdi tailwindcss kako handlvit font i font-families */}
                        {/* TODO: sredi go firefox debugerrot aman */}
                        {/* TODO: add a component with childs that can register which one is active in the viewport and a default option */}
                        {/*TODO: make svg bigger*/}
                        {/*TODO: define typography*/}
                        {/*TODO: register popup */}
                        {/*TODO: single icon visible*/}
                        <span className={`text-white hover:text-accent`}>{user.username}</span>
                            <HoverCard>
                                <HoverCardTrigger>
                                    <UserIcon className={`text-white hover:text-accent`}/>
                                </HoverCardTrigger>
                                <HoverCardContent className="h-fit">See profile</HoverCardContent>
                            </HoverCard>
                    </>
                )
                }
            </section>
        </main>
    );
};

export default HomePage;