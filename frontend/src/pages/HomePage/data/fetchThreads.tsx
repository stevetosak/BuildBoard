import API_ENDPOINTS from "@constants/api-endpoints.ts";
import {type Page, type NamedThread } from "@shared/api-utils";
import { getAuthHeader } from "@shared/security-utils";
import type { SearchOptions } from "../../shared/ThreadsComponent";


const createPageURL = (page: number, searchOptions:SearchOptions) => {
	const url = new URL(API_ENDPOINTS.threads());
	url.searchParams.set("page", page.toString());
	
	for(const option in searchOptions){
		if(option=='tags') continue
		url.searchParams.set(option, searchOptions[option as keyof Omit<SearchOptions,'tags'>]);
	}

	searchOptions.tags.forEach((tag) => {
		url.searchParams.append("tags", tag);
	})

	return url;
};

export const fetchThreads = async (pageNumber:number, searchOptions:SearchOptions) : Promise<Page<NamedThread[]>> => {
	console.log(searchOptions.query)
	if (pageNumber < 0) throw new Error("The requested page must be > 0");

	const response = await fetch(createPageURL(pageNumber,searchOptions), getAuthHeader());
	if(!response.ok)
		throw new Error("Can't fetch threads")
	
	const pageResponse = await response.json() as Page<NamedThread[]>
	return pageResponse;
};