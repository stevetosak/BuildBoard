export type ThreadData = {
    user: string,
    id?: number,
    parentId?: number
    children?: ThreadData[]
    content: string,
    date?: string
    depth: number
}

const fetchMockReplies = (data:ThreadData):ThreadData[] => ([
        {
            user: "globov2",
            depth: data.depth + 1,
            content: "I am replying baby",
        },
        {
            user: "kaliuser123",
            content: "I use kali linux i am bed boy",
            depth: data.depth + 1,
        },
])

export default fetchMockReplies