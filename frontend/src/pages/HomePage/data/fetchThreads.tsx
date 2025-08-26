import API_ENDPOINTS from "@constants/api-endpoints.ts";
import {type Page, type NamedThread } from "@shared/api-utils";
import { getAuthHeader } from "@shared/security-utils";
import type { SearchOptions } from "../../shared/ThreadsComponent";
import { createPageURL } from "@shared/api-utils";

export const fetchThreads = async (pageNumber:number, searchOptions:SearchOptions) : Promise<Page<NamedThread[]>> => {
	if (pageNumber < 0) throw new Error("The requested page must be > 0");

	const response = await fetch(createPageURL(pageNumber,searchOptions,API_ENDPOINTS.threads()), getAuthHeader());
	if(!response.ok)
		throw new Error("Can't fetch threads")
	
	const pageResponse = await response.json() as Page<NamedThread[]>
	return pageResponse;
};