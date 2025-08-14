import API_ENDPOINTS from "@constants/api-endpoints.ts";
import type {Page, NamedThread} from "@shared/api-types.ts";

const createPageURL = (page: number) => {
	const url = new URL(API_ENDPOINTS.threads());
	url.searchParams.set("page", page.toString());
	return url;
};

export const fetchThreads = async (page: number) : Promise<Page<NamedThread[]>> => {
	if (page < 0) throw new Error("The requested page must be > 0");

	const response = await fetch(createPageURL(page));
	if(!response.ok)
		throw new Error("Can't fetch threads")
	
	const content = await response.json() as Page<NamedThread[]>
	return content
};

export const handleInitFetch = async () => fetchThreads(0);