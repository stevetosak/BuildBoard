import API_ENDPOINTS from "@constants/api-endpoints.ts";
import type {ErrorMsg, UserProfile} from "@shared/api-types.ts";

const handleError = async (response:Response) => {
    const body = await response.json() as ErrorMsg
    if(body.message==='Not found')
        return null 
    throw new Error("Unknown expection")
}

export const loader = async ():Promise<UserProfile> => {
    const response = await fetch(API_ENDPOINTS.user("buildboard"))		
    if(!response.ok)
        return await handleError(response)
    return await response.json()
};
