import type {LoaderFunction} from "react-router-dom";
import {apiGetAuthenticated} from "@lib/utils/api.ts";
import type {ChannelMessageDisplay} from "@/types.ts";

export const channelLoader : LoaderFunction =  async ({params}) => {
    const {projectName,channelName} = params;
    try{
        const response = await apiGetAuthenticated<ChannelMessageDisplay[]>(`projects/${projectName}/channels/${channelName}`)
        return response.data
    } catch (err){
        console.error(err)
    }
}