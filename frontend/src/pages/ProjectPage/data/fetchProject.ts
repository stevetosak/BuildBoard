import type { ApiError, Project } from "@shared/api-utils";
import {default as API_ENDPOINTS} from '@constants/api-endpoints'
import { getAuthHeader } from "@shared/security-utils.ts";

const fetchProject = async (projectName: string): Promise<Project> => {
	const response = await fetch(API_ENDPOINTS.projectDescription(projectName),getAuthHeader())
	const data = await response.json();

	if(!response.ok) {
		throw data as ApiError;
	}

	return data as Project;
}

export default fetchProject;
