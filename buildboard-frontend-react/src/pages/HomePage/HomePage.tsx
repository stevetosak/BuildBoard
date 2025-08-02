import OneActiveAtTime from "@/components/shared/OneActiveAtTime";
import { Button } from "@/components/ui/button.tsx";
import { Link, useLoaderData } from "react-router-dom";
import iconUrl from "@assets/Icon.jpg";
import type { User } from "./utils";
import {
	HoverCard,
	HoverCardContent,
	HoverCardTrigger,
} from "@radix-ui/react-hover-card";
import { UserIcon } from "lucide-react";
import {
	SidebarHeader,
	SidebarProvider,
	SidebarTrigger,
	Sidebar,
} from "@/components/ui/sidebar";

export type SingleColorCtx = {
	registered: [boolean];
	setFalse: () => void;
	setTrue: () => void;
};

//TODO: eslint + prettier config
//TODO: vidi sho e fintatna so group-data[state]:neshto da napresh
//TODO: vidi sho se at rules vo css @layers @themes @apply...
//TODO: razmisli za code examples
//TODO: fix intelisense for tailwind css
const HomePage = () => {
	const user = useLoaderData() as NonNullable<User>;

	return (
		<main className="w-full grid bg-bg-1 px-0 h-[100vh]  pb-3 pe-3 grid-cols-[1fr_3fr_1fr]">
			<SidebarProvider>
				<Sidebar className=" bg-sidebar-bg">
					<SidebarHeader>
						<div className="w-full flex justify-center">
							<div className="w-1/2 h-full">
								<img
									src={iconUrl}
									alt="Buildboard logo"
									className="w-full h-full"
								/>
							</div>
						</div>
					</SidebarHeader>
				</Sidebar>
				<SidebarTrigger className="text-white"/>
			</SidebarProvider>
			<section></section>
			<section className="flex border-l-2 border-l-white gap-2.5 justify-end pe-3 my-3">
				{user ? (
					// TODO: hovertot da izlegvit ovaj viewerov.
					<OneActiveAtTime initActive={1}>
						<span>{user.username}</span>
						<HoverCard>
							<HoverCardTrigger>
								<UserIcon />
							</HoverCardTrigger>
							<HoverCardContent>See profile</HoverCardContent>
						</HoverCard>
					</OneActiveAtTime>
				) : (
					<OneActiveAtTime>
						<Button variant={"outline"}>
							<Link to="/user/register">Register</Link>
						</Button>
						<Button variant={"outline"}>
							<Link to="/user/login">Login</Link>
						</Button>
					</OneActiveAtTime>
				)}
				{/* TODO: vidi kako se prat podobro za searchojte mozhit nekoj pagination da klajme */}
			</section>
		</main>
	);
};

export default HomePage;
