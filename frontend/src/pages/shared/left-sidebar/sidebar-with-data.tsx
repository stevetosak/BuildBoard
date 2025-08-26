import LeftSidebarBuilder from "./left-sidebar-helpers";
import LogoLeftSidebar from "../LogoLeftSidebar";
import type { ReactNode } from "react";

type LeftSidebarProps<T> = {
	data: T | null | undefined;
	children: (data:T) => ReactNode;
};

const LeftSidebar = <T,>({ data, children }: LeftSidebarProps<T>) => {
	return (
		<LeftSidebarBuilder.Wrapper data={data}>
			<LeftSidebarBuilder.HeaderInsideData>
				<LogoLeftSidebar />
			</LeftSidebarBuilder.HeaderInsideData>
			<LeftSidebarBuilder.BodyOutsideData>
				{children}
			</LeftSidebarBuilder.BodyOutsideData>
		</LeftSidebarBuilder.Wrapper>
	);
};

export default LeftSidebar;
