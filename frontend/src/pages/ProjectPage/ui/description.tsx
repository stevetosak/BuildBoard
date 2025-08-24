import iconUrl from "@assets/Icon.jpg";
import { Button } from "@components/ui/button";
import { Link, useOutletContext } from "react-router-dom";
import Markdown from "react-markdown";
import type { Project } from "@shared/api-utils.ts";
import ConditionalDisplay from "@pages/HomePage/ui/ConditionalDsipaly.tsx";


const Description = () => {
	const project = useOutletContext<Project|undefined>();
	return (
		<ConditionalDisplay data={project}>
			{({description}) => (
		<section
			className="grid px-[10%]"
			style={{
				gridTemplateRows: "minmax(15rem,20vh) min-content auto"
			}}
		>
			<div className="relative w-1/4 justify-self-center">
				<div className="absolute w-full h-full">
					<img
						className="w-full h-full"
						src={iconUrl}
					/>
				</div>
			</div>
			<div className="justify-self-end ">
				<Button variant={"outline"}>
					<Link to="/login">Send request</Link>
				</Button>
			</div>
			<div className="h-full overflow-scroll relative">
				<div className="w-full mt-5 text-lg absolute text-left justify-self-center">
					<div className="p-[2em] pt-[1em] bg-sidebar-bg rounded-xl w-full h-full  project-desc">
						<Markdown>{description}</Markdown>
					</div>
				</div>
			</div>
		</section>)}
		</ConditionalDisplay>
	);
};

export default Description;
