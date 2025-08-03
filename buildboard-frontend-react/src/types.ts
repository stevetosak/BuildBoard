
export type ThreadUserDto = {
    id: number,
    username: string,
    avatarUrl: string
}
export type ThreadData = {
    id:number,
    title?:string,
    content: string,
    date: string
    level: number,
    user: ThreadUserDto
}
