import API_ENDPOINTS from "@/constants";

export type Page<T> = {
	content: T;
	page: number;
};

export type Thread = {
	meta: {
		username: string;
		userLogo: URL;
		uploadedAt: string;
	};
	content: {
		title: string;
		tags: string[]; //Trebat da se razmislit za ova kako ke se handelvit
		shortDescription: string;
	};
};

const createPageURL = (page: number) => {
	const url = new URL(API_ENDPOINTS.endpoints.threads, API_ENDPOINTS.host);
	url.searchParams.set("page", page.toString());

	return url;
};

export const fetchThreads = async (page: number) : Promise<Page<Thread[]>> => {
	if (page < 0) throw new Error("The requested page must be > 0");

	const response = await fetch(createPageURL(page));
	if(!response.ok)
		throw new Error("Can't fetch threads")
	
	return { 
		content : (await response.json() as Thread[]),
		page 	
	} 
};

export const handleInitFetch = async () => fetchThreads(0);

