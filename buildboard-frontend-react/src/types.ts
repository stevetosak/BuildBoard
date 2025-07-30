export type ThreadData = {
    user: string,
    id?: number,
    parentId?: number
    children?: ThreadData[]
    content: string,
    date?: string
    depth: number
}