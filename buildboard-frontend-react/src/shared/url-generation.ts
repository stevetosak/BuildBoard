import type { UserProfile } from "./api-utils";

export type InterestedHeaders = keyof NonNullable<UserProfile>["interested"];

export const createUrlForTopic = (topicName:string) =>  
    `topics/${topicName}`


export const createUrlForProject = (projectName:string) => 
    `projects/${projectName}`

export const getUrlForThread = (name:string, threadType:InterestedHeaders) => { 
    if(threadType==="projects")
        return createUrlForProject(name);
    if(threadType==="topics")
        return createUrlForTopic(name);
    throw new Error("Unknown thread type");
}