import type { SearchOptions } from "@pages/shared/ThreadsComponent";
import type { InterestedHeaders } from "./url-generation";

export type ChannelEssentials = {
	name: string;
};

export type ProjectEssentials = {
	name: string;
};

export type Topic = {
	name: string;
};

export type ShortUserProfile = {
	username: string;
	logo: URL;
};

export type UserProfile = {
	username: string;
	interested: {
		projects: ProjectEssentials[];
		topics: Topic[];
	};
	friends: ShortUserProfile[];
};


export type ErrorMsg = {
	message: string;
	path: URL;
};

export type Page<T> = {
	content: T;
	pageable: {
		pageNumber: number;
	};
	totalPages: number;
};

export type NamedThread = {
	createdAt: string;
	creator: ShortUserProfile;
	content: {
		title: string;
		content: string;
		tags: string[];
	};
	threadType : InterestedHeaders
};
export type FetchNamedTopics = (pageNumber:number, queryKey:SearchOptions) => Promise<Page<NamedThread[]>>


//Sekogash userot sho go pret requestot e prv vo listata 
type ShortUserProfileWithRoles = ShortUserProfile & {roles: string[], permissions:string[]}

export const getNextPage = <T,>(lastPage:Page<T>) => 
			lastPage.pageable.pageNumber + 1 < lastPage.totalPages
				? lastPage.pageable.pageNumber + 1
				: undefined 

export type Project = { 
	id:string, 
	name:string 
	members : ShortUserProfileWithRoles[]
	logo: string,
	description: string,
	repoURL:string   
}

export type ApiError = {
	title: string ,
	status:number,
	detail:string
}

export const debounceGenerator = <T,>(f:(...args:T[]) => unknown, delay:number) => {
	let timeoutId: number | undefined;
	return (...args:T[]) => {
		window.clearTimeout(timeoutId);
		timeoutId = window.setTimeout(() => {
			f(...args);
		}, delay);
	}
};

export const createPageURL = (page: number, searchOptions:SearchOptions, apiUrl:string) => {
    const url = new URL(apiUrl);
    url.searchParams.set("page", page.toString());
    
    for(const option in searchOptions){
        if(option=='tags' || option=='projectId') continue
        url.searchParams.set(option, searchOptions[option as keyof Omit<SearchOptions,'tag'|"projectId">]);
    }

	if(searchOptions['projectId'])
		url.searchParams.set("projectId",searchOptions['projectId'])

    searchOptions.tag.forEach((tag) => {
        url.searchParams.append("tag", tag);
    })

    return url;
};