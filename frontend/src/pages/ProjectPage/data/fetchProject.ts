import type { Project } from "@shared/api-utils";
import {default as API_ENDPOINTS} from '@constants/api-endpoints'
import { getAuthHeader } from "@shared/security-utils.ts";

const fetchProject = async (projectName: string): Promise<Project> => {
	const response = await fetch(API_ENDPOINTS.projectDescription(projectName),getAuthHeader())
	if(!response.ok) {
		throw new Error(response.statusText);
	}
	const data = await response.json();
	return data as Project;
}

export default fetchProject;
