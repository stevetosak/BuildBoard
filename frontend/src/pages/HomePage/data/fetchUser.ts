import API_ENDPOINTS from "@constants/api-endpoints.ts";
import {type ErrorMsg, type UserProfile} from "@shared/api-utils";
import {getAuthHeader} from "@shared/security-utils.ts";
import type { UseQueryResultWrapper } from "@shared/useQuery-result-type";

type FetchUserParams={
    queryKey: [string|undefined]
}

const handleError = async (response:Response) => {
    const body = await response.json() as ErrorMsg
    if(body.message==='Not found')
        return new Error("User not found")
    throw new Error("Unknown expection")
}

export const fetchUsers = async ({queryKey}: FetchUserParams):Promise<UseQueryResultWrapper<UserProfile>> => {
    const username = queryKey[0]
    if(username===undefined) throw new Error("Username is undefined in fetchUsers")

    const response = await fetch(API_ENDPOINTS.user(username),getAuthHeader())		
    if(!response.ok)
        await handleError(response)
    return await response.json()
};
