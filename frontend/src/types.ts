export type ThreadUserDto = {
    id: string,
    username: string,
    avatarUrl: string
}
export type ThreadData = {
    id?: number,
    title?: string,
    content: string,
    date: string
    level: number,
    user: ThreadUserDto
}

export type ThreadType = "topic" | "discussion" | "project";
export type ThreadStatus = "active" | "deleted"

export interface ThreadElement {
    id?: number,
    content: string,
    level: number,
    parentId: number,
    numReplies: number,
    numLikes: number,
    type: string,
    createdAt: number
    user: ThreadUserDto,
    status: ThreadStatus
}

export type ChannelMessage = {
    channelName: string,
    content: string,
    senderUsername:string,
    sentAt: string,
    projectName: string,
    avatarUrl: string
}
export type SendChannelMessageDto = {
    channelName: string,
    content: string,
    senderUsername:string,
    projectName: string,
}

export interface TopicView extends ThreadElement {
    title: string
}

export type ThreadResponse = {
    root: ThreadElement | TopicView,
    children: ThreadElement[],
    type: string
}

