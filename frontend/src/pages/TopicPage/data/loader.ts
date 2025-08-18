import type { ThreadResponse } from "@/types";
import {api} from "@lib/utils/api.ts";
import type {LoaderFunction} from "react-router-dom";

export const topicLoader : LoaderFunction = async ({params}) => {
    const {topicName} = params;
    console.log("TOPIC NAME: " + topicName)
    const response = await api.get<ThreadResponse>(`/topics/${topicName}`)
    console.log("RESP DATA")
    console.log(response.data)
    return response.data;
}