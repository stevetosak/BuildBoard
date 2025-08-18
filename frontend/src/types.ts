export type ThreadUserDto = {
    id: number,
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

export interface ThreadElement {
    id?: number,
    content: string,
    level: number,
    parentId: number,
    numReplies: number,
    numLikes: number,
    type: string,
    createdAt: number
    user: ThreadUserDto
}

export interface TopicView extends ThreadElement {
    title: string
}

export type ThreadResponse = {
    root: ThreadElement | TopicView,
    children: ThreadElement[],
    type: string
}

