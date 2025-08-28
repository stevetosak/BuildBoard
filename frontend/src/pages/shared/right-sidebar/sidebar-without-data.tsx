import type { UserProfile } from "@shared/api-utils";
import type { ReactNode } from "react";
import RightSidebarBuilder from "./right-sidebar-helpers";
import LoginLogoutButtons from "../login-logout-buttons";
import UserInfo from "../UserInfo";
import RightPopUp from "../RightPopup";

type RightSidebarProps = {
	userProfile: UserProfile | undefined;
	title: string;
	children: ReactNode;
};

const RightSidebar = ({ userProfile, children, title }: RightSidebarProps) => {
	return (
		<RightSidebarBuilder.Wrapper data={userProfile}>
			<RightSidebarBuilder.Header
				componentIfDataNullable={<LoginLogoutButtons />}
			>
				{(user: UserProfile) => <UserInfo username={user.username} />}
			</RightSidebarBuilder.Header>
			<RightSidebarBuilder.ContextOverrider value={"asdf"}>
				<RightSidebarBuilder.BodyInsideData>
					<RightPopUp title={title}>{children}</RightPopUp>
				</RightSidebarBuilder.BodyInsideData>
			</RightSidebarBuilder.ContextOverrider>
		</RightSidebarBuilder.Wrapper>
	);
};

export default RightSidebar;
