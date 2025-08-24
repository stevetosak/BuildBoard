import type { ReactNode } from "react";
import LeftSidebarBuilder from "./left-sidebar-helpers";
import LogoLeftSidebar from "../LogoLeftSidebar";
import { SidebarMenu } from "@components/ui/sidebar";

type LeftSidebarProps = {
	children: ReactNode;
};

const LeftSidebar = ({ children }: LeftSidebarProps) => {
	return (
		<LeftSidebarBuilder.Wrapper data={"placeholder"}>
			<LeftSidebarBuilder.HeaderInsideData>
				<LogoLeftSidebar />
			</LeftSidebarBuilder.HeaderInsideData>
			<LeftSidebarBuilder.BodyInsideData>
				<SidebarMenu>{children}</SidebarMenu>
			</LeftSidebarBuilder.BodyInsideData>
		</LeftSidebarBuilder.Wrapper>
	);
};

export default LeftSidebar;
