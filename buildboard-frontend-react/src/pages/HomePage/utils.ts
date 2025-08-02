import API_ENDPOINTS from "@/apiUtils";

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

type NonNullUser = {
	username: string;
	following: {
		channels: Channel[];
		projects: Projects[];
		tags: Tag[];
		friends: NonNullUser["username"][];
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

//TODO: klaj mu posle da ne e logiran i ne se zamaraj
export const loader = async () => {
	//TODO: make url builder 
	const response = await fetch(API_ENDPOINTS.host + API_ENDPOINTS.endpoints.user  + "?username=")		
	if(!response.ok)
		return await handleError(response)
	return await response.json()
};
