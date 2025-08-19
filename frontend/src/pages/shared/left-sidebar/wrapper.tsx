import { Children, type ReactNode } from "react";
import {
	Sidebar,
	SidebarContent,
	SidebarHeader,
	SidebarProvider,
	SidebarTrigger,
} from "@components/ui/sidebar";
import { DataContext } from "../placeholders/use-data";

type WrapperProps<T> = {
	side: "left" | "right";
	children: ReactNode;
	data: T|null|undefined;
};
const Wrapper = <T,>({ side, children, data }: WrapperProps<T>) => {
	if (Children.count(children) !== 2)
		throw new Error("Accepts only 2 children");

	const [header, body] = Children.toArray(children);

	return (
		<DataContext.Provider value={data}>
			<SidebarProvider
				className={`${side == "right" ? "flex-row-reverse" : "flex-row"} `}
			>
				<Sidebar
					className=" bg-sidebar-bg"
					side={side}
				>
					<SidebarHeader>
						<div className="w-full flex justify-center">{header}</div>
					</SidebarHeader>
					<SidebarContent className="pb-3 px-2 group-data-[collapsible=offcanvas]:pb-0 group-data-[collapsible=offcanvas]:px-0">
						{body}
					</SidebarContent>
				</Sidebar>
				<SidebarTrigger
					className="text-white"
					style={{ transform: "translate(0,calc(50vh - var(--spacing) * 7))" }}
				/>
			</SidebarProvider>
		</DataContext.Provider>
	);
};

export default Wrapper;
