import { Button } from "@/components/ui/button.tsx";
import { Link, useLoaderData } from "react-router-dom";
import iconUrl from "@assets/Icon.jpg";
import type { User } from "./utils";
import { Tag, FolderDot, Rss, ChevronDown } from "lucide-react";
import { SidebarGroup, SidebarGroupLabel } from "@/components/ui/sidebar";
import { Collapsible, CollapsibleTrigger } from "@components/ui/collapsible";
import type { JSX } from "react";
import { CollapsibleContent } from "@radix-ui/react-collapsible";
import CustomSidebar from "@/components/shared/CustomSidebar";
import UserInfo from "@/components/HomePage/UserInfo";
import FriendsPopUp from "@/components/HomePage/FriendsPopUp";
import ThreadsComponent from "@/components/HomePage/ThreadsComponent/ThreadsComponent";

export type SingleColorCtx = {
	registered: [boolean];
	setFalse: () => void;
	setTrue: () => void;
};

const makeFirstLetterUppercase = (msg: string) => {
	if (msg.length == 0) return;
	return msg.charAt(0).toUpperCase() + msg.slice(1);
};

const iconSize = "1em";
const iconsForHeading = {
	tags: <Tag size={iconSize} />,
	projects: <FolderDot size={"1em"} />,
	channels: <Rss size={"1em"} />,
} as Record<keyof NonNullable<User>["following"], JSX.Element>;

//TODO: eslint + prettier config
//TODO: vidi sho se at rules vo css @layers @themes @apply...
//TODO: razmisli za code examples
//TODO: fix intelisense for tailwind css
//TODO: ovie sho ke se project, topics,tags... napraj gi da se dropdown
//TODO: klaj na sidebierot ko ke imash nekoj na hover togash i borderot i ova drugovo da ti se vo bela boja
//TDOO: add paging
//TODO: add links to user profiles
//TODO: Collapsable element into it's own component
//TODO: ostaj aj customSidebar izvaj ja friends vo posebna komponenta
//TODO: vidi kako popametno da gi koristish min i max height/width i calc
//TODO: sekade napraj da se koristit URL so URLSearchParams
//TODO: dogovorejtesa za edna fetch funkcija so generic za da ne se dupite so castiranje vo ts
//TODO: add infinite scroll na loader componentite. Ideja napraj nekoja komponenta drugachija sho ke se zamarat so to i posle samo resikoristuvaj
//TODO: reshigo tekstot, mnogu e mal msl prosechen
//TODO: vidi sho e ona so layout vo firefox debuggerot
//TODO: vidi gi poubo em i rem
const HomePage = () => {
	const user = useLoaderData() as NonNullable<User>;

	return (
		<main className="w-full grid bg-bg-1 px-0 h-[100vh] pb-3 grid-cols-[1fr_3fr_1fr]">
			<CustomSidebar
				side="left"
				headerContent={
					<div className="w-1/2 h-full">
						<img
							src={iconUrl}
							alt="Buildboard logo"
							className="w-full h-full"
						/>
					</div>
				}
				bodyContent={(
					Object.keys(user.following) as (keyof typeof user.following)[]
				)
					.filter((key) => key !== "friends")
					.map((sidebarTitle) => (
						<Collapsible
							key={sidebarTitle}
							className="group/collapsible text-[1.5rem]"
						>
							<SidebarGroup className="border-b-1 border-b-sidebar-lines hover:text-white hover:border-b-white">
								<SidebarGroupLabel className="flex-grow-1 text-none text-md">
									<CollapsibleTrigger className="w-full flex gap-2 items-center">
										{iconsForHeading[sidebarTitle]}
										<span>{makeFirstLetterUppercase(sidebarTitle)}</span>
										<ChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
									</CollapsibleTrigger>
								</SidebarGroupLabel>
								<CollapsibleContent className="flex pt-1 flex-col gap-1 overflow-scroll">
									{user.following[sidebarTitle].map((item) => (
										<Button
											className="self-start text-lg cursor-pointer"
											variant={"link"}
										>
											{"url" in item ? (
												<Link to={item.url}>{item.name}</Link>
											) : (
												<span>{item.name}</span>
											)}
										</Button>
									))}
								</CollapsibleContent>
							</SidebarGroup>
						</Collapsible>
					))}
			/>
			<ThreadsComponent />
			<section className="pt-4 flex flex-col gap-2">
				<div className="w-full gap-2 flex justify-center items-center">
					<UserInfo user={user} />
				</div>
				<div className="flex-grow-1 grid justify-end pt-[20%]">
					<FriendsPopUp user={user} />
				</div>
			</section>
		</main>
	);
};

export default HomePage;
