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


type ShortUserProfileWithRoles = ShortUserProfile & {roles: string[]}

export const getNextPage = <T,>(lastPage:Page<T>) => 
			lastPage.pageable.pageNumber + 1 < lastPage.totalPages
				? lastPage.pageable.pageNumber + 1
				: undefined 

export type Project = { 
	name:string 
	members : ShortUserProfileWithRoles[]
	logo: string,
	description: string  
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
