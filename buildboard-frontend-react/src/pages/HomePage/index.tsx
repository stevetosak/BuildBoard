import { Button } from "@/components/ui/button.tsx";
import iconUrl from "@assets/Icon.jpg";
import { fetchUsers } from "@pages/HomePage/data/fetchUser";
import { FolderDot, Rss, ChevronDown } from "lucide-react";
import { SidebarGroup, SidebarGroupLabel } from "@components/ui/sidebar";
import { Collapsible, CollapsibleTrigger } from "@components/ui/collapsible";
import { useContext, type JSX } from "react";
import { CollapsibleContent } from "@radix-ui/react-collapsible";
import CustomSidebar from "@components/shared/CustomSidebar";
import UserInfo from "@pages/HomePage/ui/UserInfo";
import FriendsPopUp from "@pages/HomePage/ui/FriendsPopUp";
import ThreadsComponent from "@pages/HomePage/ui/ThreadsComponent";
import SecurityContext from "@/context/security-context";
import { useQuery } from "@tanstack/react-query";
import type { UserProfile } from "@shared/api-utils";
import { getUrlForThread, type InterestedHeaders } from "@shared/url-generation";
import DisplayDataIfLoaded from "./ui/DisplayDataIfLoaded";

export type SingleColorCtx = {
	registered: [boolean];
	setFalse: () => void;
	setTrue: () => void;
};

const makeFirstLetterUppercase = (msg: string) => {
	if (msg.length == 0) return;
	return msg.charAt(0).toUpperCase() + msg.slice(1);
};

const iconsForHeading = {
	projects: <FolderDot size={"1em"} />,
	topics: <Rss size={"1em"} />,
} as Record<keyof UserProfile["interested"], JSX.Element>;

const HomePage = () => {
	const user = useContext(SecurityContext);
	const { data: userProfile } = useQuery({
		queryKey: [user?.username],
		queryFn: fetchUsers,
	});

	return (
		<main className="w-full grid bg-bg-1 px-0 h-[100vh] pb-3 grid-cols-[1fr_3fr_1fr]">
			<DisplayDataIfLoaded
				data={userProfile}
				dataUndefinedComponent={<div></div>}
			>
				{(user) => (
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
						bodyContent={(Object.keys(user.interested) as InterestedHeaders[]).map((threadType) => (
							<Collapsible
								key={threadType}
								className="group/collapsible text-[1.5rem]"
							>
								<SidebarGroup className="border-b-1 border-b-sidebar-lines hover:text-white hover:border-b-white">
									<SidebarGroupLabel className="flex-grow-1 text-none text-md">
										<CollapsibleTrigger className="w-full flex gap-2 items-center">
											{iconsForHeading[threadType]}
											<span>{makeFirstLetterUppercase(threadType)}</span>
											<ChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
										</CollapsibleTrigger>
									</SidebarGroupLabel>
									<CollapsibleContent className="flex pt-1 flex-col gap-1 overflow-scroll">
										{user.interested[threadType].map(({name:threadName}, idx) => (
											<Button
												key={idx}
												className="self-start text-lg cursor-pointer"
												variant="link"
											>
												{getUrlForThread(threadName, threadType)}	
											</Button>
										))}
									</CollapsibleContent>
								</SidebarGroup>
							</Collapsible>
						))}
					/>
				)}
			</DisplayDataIfLoaded>
			<ThreadsComponent />
			<section className="pt-4 flex flex-col gap-2">
				<div className="w-full gap-2 flex justify-center items-center">
					<UserInfo user={userProfile} />
				</div>
				<DisplayDataIfLoaded
					data={userProfile}
					dataUndefinedComponent={<div></div>}
				>
					{(user) => (
						<div className="flex-grow-1 grid justify-end pt-[20%]">
							<FriendsPopUp user={user} />
						</div>
					)}
				</DisplayDataIfLoaded>
			</section>
		</main>
	);
};

export default HomePage;
