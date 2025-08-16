import API_ENDPOINTS from "@constants/api-endpoints.ts";
import {type Page, type NamedThread } from "@shared/api-utils";
import { getAuthHeader } from "@shared/security-utils";

const createPageURL = (page: number) => {
	const url = new URL(API_ENDPOINTS.threads());
	url.searchParams.set("page", page.toString());
	return url;
};

export const fetchThreads = async (pageNumber:number) : Promise<Page<NamedThread[]>> => {
	if (pageNumber < 0) throw new Error("The requested page must be > 0");

	const response = await fetch(createPageURL(pageNumber), getAuthHeader());
	if(!response.ok)
		throw new Error("Can't fetch threads")
	
	const pageResponse = await response.json() as Page<NamedThread[]>
	return pageResponse;
};
