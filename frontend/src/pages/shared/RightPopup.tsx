import { Button } from "@components/ui/button";
import { PanelLeftIcon } from "lucide-react";
import { useState } from "react";

type RightPopUpProps = {
	title: string;
	children: React.ReactNode;
};

const RightPopUp = ({ title, children }: RightPopUpProps) => {
	const [expanded, setExpanded] = useState<boolean>(false);

	return (
		<div
			data-expanded={expanded}
			className="group grid-rows-[3em_3em_auto] data-[expanded=true]:translate-x-full relative transition-transform ease-in-out duration-100 bg-sidebar-bg grid p-2 rounded-l-lg text-sidebar-foreground hover:text-white hover:border-white border-l-1 border-t-1 border-b-1 w-[12em] max-h-1/2 "
		>
			<Button
				size="icon"
				onClick={() => setExpanded((e) => !e)}
				className="justify-self-end hover:bg-accent hover:text-white group-data-[expanded=true]:-translate-x-[13em] duration-300"
			>
				<PanelLeftIcon className="hover:translate-0"/>
				<span className="sr-only">Toggle Sidebar</span>
			</Button>

			<h3 className="justify-self-center">{title}</h3>

			<div className="overflow-scroll flex flex-col gap-1 items-start p-2">
				{children}
			</div>
		</div>
	);
};

export default RightPopUp;
