import ThreadsComponent from "@pages/shared/ThreadsComponent";
import iconUrl from "@assets/Icon.jpg";
import { useOutletContext } from "react-router-dom";
import type { Project } from "@shared/api-utils.ts";
import { fetchTopicsForProject } from "../data/fetch-topics-project.ts";

const Topics = () => {
    const project = useOutletContext<Project>()

	return (
		<section className="grid grid-rows-[minmax(15rem,20vh)_auto]">
            <div className="relative w-1/4 justify-self-center">
				<div className="absolute w-full h-full">
					<img
						className="w-full h-full"
						src={iconUrl}
					/>
				</div>
			</div>
			<ThreadsComponent fetchTopics={fetchTopicsForProject(project.name)} projectId={project.id}/>
		</section>
	);
};

export default Topics;
