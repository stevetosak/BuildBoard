import type { SearchOptions } from "@pages/shared/ThreadsComponent";
import type { Project } from "@shared/api-utils";

const fetchProject = async (_projectName: string): Promise<Project> => ({
	members: [
		{
			username: "viki",
			logo: new URL("http://localhost:5432/matura.jpg"),
			roles: ["CREATOR"],
		},
		{
			username: "stefan",
			logo: new URL("http://localhost:5432/matura.jpg"),
			roles: ["CREATOR"],
		},
	],
});

export default fetchProject;
