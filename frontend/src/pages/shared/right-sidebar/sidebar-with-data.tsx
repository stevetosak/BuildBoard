import type { UserProfile } from "@shared/api-utils";
import RightSidebarBuilder from "./right-sidebar-helpers";
import LoginLogoutButtons from "../login-logout-buttons";
import UserInfo from "../UserInfo";
import type { ReactNode } from "react";
import RightPopUp from "../RightPopup";

type RightSidebarProps<T> = {
	userProfile: UserProfile | undefined;
	data: T | undefined | null;
	children: (data: T) => ReactNode;
	title:string 
};

const RightSidebar = <T,>({
	userProfile,
	data,
	children,
	title
}: RightSidebarProps<T>) => {
	return (
		<RightSidebarBuilder.Wrapper data={userProfile}>
			<RightSidebarBuilder.Header
				componentIfDataNullable={<LoginLogoutButtons />}
			>
				{(user: UserProfile) => <UserInfo username={user.username} />}
			</RightSidebarBuilder.Header>
			<RightSidebarBuilder.ContextOverrider value={data}>
				<RightSidebarBuilder.BodyOutsideData>
					{(data: T) => <RightPopUp title={title}>{children(data)}</RightPopUp>}
				</RightSidebarBuilder.BodyOutsideData>
			</RightSidebarBuilder.ContextOverrider>
		</RightSidebarBuilder.Wrapper>
	);
};

export default RightSidebar;
