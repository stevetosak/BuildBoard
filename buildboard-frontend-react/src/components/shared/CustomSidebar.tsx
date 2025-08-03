import type { JSX } from "react";
import {
	Sidebar,
	SidebarContent,
	SidebarHeader,
	SidebarProvider,
	SidebarTrigger,
} from "../ui/sidebar";

type CustomSidebarProps = {
	side: "left" | "right";
	headerContent?: JSX.Element[] | JSX.Element;
	bodyContent: JSX.Element[] | JSX.Element;
};

const CustomSidebar = ({
	side,
	headerContent,
	bodyContent,
}: CustomSidebarProps) => {
	return (
		<SidebarProvider className={`${side == 'right' ? 'flex-row-reverse' : 'flex-row'}`}>
			<Sidebar
				className=" bg-sidebar-bg"
				side={side}
			>
				{headerContent && (
					<SidebarHeader>
						<div className="w-full flex justify-center">{headerContent}</div>
					</SidebarHeader>
				)}
				<SidebarContent className="pb-3 px-2 group-data-[collapsible=offcanvas]:pb-0 group-data-[collapsible=offcanvas]:px-0">
					{bodyContent}
				</SidebarContent>
			</Sidebar>
			<SidebarTrigger
				className="text-white"
				style={{ transform: "translate(0,calc(50vh - var(--spacing) * 7))" }}
			/>
		</SidebarProvider>
	);
};

export default CustomSidebar;
