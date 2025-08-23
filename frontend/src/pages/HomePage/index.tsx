import { Button } from "@/components/ui/button.tsx";
import { FolderDot, Rss, ChevronDown } from "lucide-react";
import { SidebarGroup, SidebarGroupLabel } from "@components/ui/sidebar";
import { Collapsible, CollapsibleTrigger } from "@components/ui/collapsible";
import { type JSX } from "react";
import { CollapsibleContent } from "@radix-ui/react-collapsible";
import UserInfo from "@pages/shared/UserInfo";
import ThreadsComponent from "@pages/shared/ThreadsComponent";
import type { UserProfile } from "@shared/api-utils";
import {
	getUrlForThread,
	type InterestedHeaders,
} from "@shared/url-generation";
import { Link } from "react-router-dom";
import LogoLeftSidebar from "@pages/shared/LogoLeftSidebar";
import RightPopUp from "@pages/shared/RightPopup";
import useGetUserProfile from "@pages/shared/use-get-user-profile";
import LoginLogoutButtons from "@pages/shared/login-logout-buttons";
import UserShortRow from "@pages/shared/user-short-row";
import LeftSidebar from "@pages/shared/left-sidebar";
import RightSidebar from "@pages/shared/right-sidebar";

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
	const userProfile = useGetUserProfile();

	return (
		<main className="layout overflow-x-hidden">
			<LeftSidebar.Wrapper
				data={userProfile}
			>
				<LeftSidebar.HeaderInsideData componentIfDataNullable={<LogoLeftSidebar />}>
					<LogoLeftSidebar />
				</LeftSidebar.HeaderInsideData>
				<LeftSidebar.BodyOutsideData>
					{(user: UserProfile) => (
						<>
							{(Object.keys(user.interested) as InterestedHeaders[]).map(
								(threadType) => (
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
																to={
																	"/" + getUrlForThread(threadName, threadType)
																}
															>
																{threadName}
															</Link>
														</Button>
													),
												)}
											</CollapsibleContent>
										</SidebarGroup>
									</Collapsible>
								),
							)}
						</>
					)}
				</LeftSidebar.BodyOutsideData>
			</LeftSidebar.Wrapper>
			<ThreadsComponent />
			<RightSidebar.Wrapper data={userProfile}>
				<RightSidebar.Header componentIfDataNullable={<LoginLogoutButtons />}>
					{(user: UserProfile) => <UserInfo username={user.username} />}
				</RightSidebar.Header>
				<RightSidebar.Body>
					{(user: UserProfile) => (
						<RightPopUp title="Friends">
							{user.friends.map((friend) => (
								<UserShortRow
									key={friend.username}
									username={friend.username}
									logo={friend.logo}
								/>
							))}
						</RightPopUp>
					)}
				</RightSidebar.Body>
			</RightSidebar.Wrapper>
		</main>
	);
};

export default HomePage;
