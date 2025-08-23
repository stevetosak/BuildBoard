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

export type ChannelMessageDisplay = {
    content: string,
    senderUsername: string,
    sentAt: string,
    avatarUrl: string
}

export interface ChannelMessageKey {
    channelName: string,
    sentAt: string, //
    senderUsername: string,
    projectName: string,
}

export interface ChannelMessageDto extends ChannelMessageKey{
    content: string,
}

export interface TopicView extends ThreadElement {
    title: string
}

export type ThreadResponse = {
    root: ThreadElement | TopicView,
    children: ThreadElement[],
    type: string
}

export type ChannelMessageEventType = "SEND" | "EDIT" | "DELETE" | "TYPE_START" | "TYPE_END"

export type ChannelMessageEvent = {
    type: ChannelMessageEventType,
    payload: ChannelMessageDto
}


