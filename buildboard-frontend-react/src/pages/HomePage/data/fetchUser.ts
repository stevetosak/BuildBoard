import API_ENDPOINTS from "@constants/api-endpoints.ts";
import {getAuthHeader, type ErrorMsg, type UserProfile} from "@shared/api-types";

const handleError = async (response:Response) => {
    const body = await response.json() as ErrorMsg
    if(body.message==='Not found')
        return null 
    throw new Error("Unknown expection")
}

export const fetchUsers = async ({queryKey}: {queryKey: [string]}):Promise<UserProfile> => {
    const username = queryKey[0]
    if(username===undefined) return null 
    const response = await fetch(API_ENDPOINTS.user(username),getAuthHeader())		
    if(!response.ok)
        return await handleError(response)
    return await response.json()
};
