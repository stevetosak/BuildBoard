import API_ENDPOINTS from "@/constants";

type Channel = {
	name: string;
	url: URL;
};

type Projects = {
	name: string;
	url: URL;
};

type Tag = {
	name: string;
};

type Friend = {
	username : string, 
	logo : URL
}

type NonNullUser = {
	username: string;
	following: {
		channels: Channel[];
		projects: Projects[];
		tags: Tag[];
		friends: Friend[]
	};
};
export type User = NonNullUser | null;
export type ErrorMsg = {
	message : string, 
	path : URL 
}

const handleError = async (response:Response) => { 
	const body = await response.json() as ErrorMsg
	if(body.message==='Not found')
		return null 
	throw new Error("Unknown expection")
}

export const loader = async () => {
	const response = await fetch(API_ENDPOINTS.user("buildboard"))		
	if(!response.ok)
		return await handleError(response)
	return await response.json()
};
