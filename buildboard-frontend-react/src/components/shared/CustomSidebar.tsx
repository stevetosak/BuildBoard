import type { JSX } from "react";
import { Sidebar, SidebarContent, SidebarHeader, SidebarProvider, SidebarTrigger } from "../ui/sidebar";

type CustomSidebarProps={
    side : "left" | "right", 
    headerContent : JSX.Element, 
    bodyContent : JSX.Element
}

const CustomSidebar = ({side,headerContent,bodyContent} : CustomSidebarProps) => {
	return (
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
				<SidebarContent className="pb-3 px-2 group-data-[collapsible=offcanvas]:pb-0 group-data-[collapsible=offcanvas]:px-0">
					{(Object.keys(user.following) as (keyof typeof user.following)[])
						.filter((key) => key !== "friends")
						.map((sidebarTitle) => (
							<Collapsible
								key={sidebarTitle}
								className="group/collapsible"
							>
								<SidebarGroup className="border-b-1 border-b-sidebar-lines hover:text-white hover:border-b-white">
									<SidebarGroupLabel className="flex-grow-1 text-none">
										<CollapsibleTrigger className="w-full flex gap-2 items-center">
											{iconsForHeading[sidebarTitle]}
											<span>{makeFirstLetterUppercase(sidebarTitle)}</span>
											<ChevronDown className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-180" />
										</CollapsibleTrigger>
									</SidebarGroupLabel>
									<CollapsibleContent className="flex flex-col gap-1 overflow-scroll">
										{user.following[sidebarTitle].map((item) => (
											<Button
												className="self-start"
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
