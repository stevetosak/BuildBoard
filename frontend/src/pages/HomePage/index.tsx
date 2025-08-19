import { Button } from "@/components/ui/button.tsx";
import iconUrl from "@assets/Icon.jpg";
import { fetchUsers } from "@pages/HomePage/data/fetchUser";
import { FolderDot, Rss, ChevronDown } from "lucide-react";
import { SidebarGroup, SidebarGroupLabel } from "@components/ui/sidebar";
import { Collapsible, CollapsibleTrigger } from "@components/ui/collapsible";
import { useContext, type JSX } from "react";
import { CollapsibleContent } from "@radix-ui/react-collapsible";
import CustomSidebar from "@components/shared/CustomSidebar";
import UserInfo from "@pages/shared/UserInfo";
import ThreadsComponent from "@pages/HomePage/ui/ThreadsComponent";
import SecurityContext from "@/context/security-context";
import { useQuery } from "@tanstack/react-query";
import type { UserProfile } from "@shared/api-utils";
import {
	getUrlForThread,
	type InterestedHeaders,
} from "@shared/url-generation";
import DisplayDataIfLoaded from "./ui/DisplayDataIfLoaded";
import { Link } from "react-router-dom";
import LogoLeftSidebar from "@pages/shared/LogoLeftSidebar";
import RightPopUp from "@pages/shared/RightPopup";
import UserLogo from "@components/shared/UserLogo";
import OneActiveAtTime from "@components/shared/OneActiveAtTime";

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

const LogoComponentIfUserUndefined = () => (
	<section>
		<div className="w-[10em] h-[10em]">
			<img
				src={iconUrl}
				alt="Buildboard logo"
				className="w-full h-full"
			/>
		</div>
	</section>
);

const LoginLogoutButtons = () => (
	<OneActiveAtTime
		activeCls="text-accent"
		nonActiveCls="text-white"
	>
		<Button variant={"outline"}>
			<Link to="/register">Register</Link>
		</Button>
		<Button variant={"outline"}>
			<Link to="/login">Login</Link>
		</Button>
	</OneActiveAtTime>
);

const HomePage = () => {
	const user = useContext(SecurityContext);
	const { data: userProfile } = useQuery({
		queryKey: [user?.username],
		queryFn: fetchUsers,
	});

	return (
		<main className="layout">
			<DisplayDataIfLoaded
				data={userProfile}
				dataUndefinedComponent={<LogoComponentIfUserUndefined />}
			>
				{(user) => (
					<CustomSidebar
						side="left"
						headerContent={<LogoLeftSidebar />}
						bodyContent={(
							Object.keys(user.interested) as InterestedHeaders[]
						).map((threadType) => (
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
										{user.interested[threadType].map(
											({ name: threadName }, idx) => (
												<Button
													key={idx}
													className="self-start text-lg cursor-pointer"
													variant="link"
												>
													<Link
														to={"/" + getUrlForThread(threadName, threadType)}
													>
														{threadName}
													</Link>
												</Button>
											),
										)}
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
					<DisplayDataIfLoaded
						data={userProfile}
						dataUndefinedComponent={<LoginLogoutButtons/>}
					>
						{(user) => <UserInfo username={user.username}/>}
					</DisplayDataIfLoaded>
				</div>
				<DisplayDataIfLoaded
					data={userProfile}
					dataUndefinedComponent={<div></div>}
				>
					{(user) => (
						<div className="flex-grow-1 grid justify-end pt-[20%]">
							<RightPopUp title="Friends">
								{user.friends.map((friend) => (
									<Button
										variant="link"
										key={friend.username}
										className="p-0 gap-2"
									>
										<div className="border-2 rounded p-0.5">
											<UserLogo
												url={friend.logo}
												alt={`${friend.username} logo`}
											/>
										</div>
										<Link to={"/profile/" + friend.username}>
											{friend.username}
										</Link>
									</Button>
								))}
							</RightPopUp>
						</div>
					)}
				</DisplayDataIfLoaded>
			</section>
		</main>
	);
};

export default HomePage;
