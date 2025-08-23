import type { FetchNamedTopics, NamedThread, Page } from "@shared/api-utils";
import { getAuthHeader } from "@shared/security-utils";
import API_ENDPOINTS from "@constants/api-endpoints.ts";
import { createPageURL } from "@shared/api-utils";

export const fetchTopicsForProject = (projectName: string): FetchNamedTopics => {
	return async (pageNumber, searchOptions) => {
		if (pageNumber < 0) throw new Error("The requested page must be > 0");

		const response = await fetch(
			createPageURL(pageNumber, searchOptions,API_ENDPOINTS.projectThread(projectName)),
			getAuthHeader(),
		);
		if (!response.ok) throw new Error("Can't fetch threads");

		const pageResponse = (await response.json()) as Page<NamedThread[]>;
		return pageResponse;
	};
};


