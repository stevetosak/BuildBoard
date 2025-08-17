import {clsx, type ClassValue} from "clsx";
import {twMerge} from "tailwind-merge";
import type {ThreadResponse, ThreadElement} from "@/types.ts";

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

export type ThreadNode = {
    parent: ThreadNode | null,
    element: ThreadElement,
    children: ThreadNode[],
}

export class ThreadTree {
    root: ThreadNode;


    constructor(root: ThreadNode) {
        this.root = root
    }

    static fromResponse = (threadResponse: ThreadResponse) => {
        const root = this.constructThreadTree(threadResponse)
        return new ThreadTree(root)
    }

    find = (targetNodeId: number,currentNode: ThreadNode = this.root) : ThreadNode | undefined => {
        if(targetNodeId === currentNode.element.id) return currentNode
        for(const child of currentNode.children){
            const found = this.find(targetNodeId,child)
            if(found) return found
        }
        return undefined;
    }

    addChildren = (tree: ThreadTree) => {
        const node = this.find(tree.root.element.id)
        if(node){
            node.children.push(...tree.root.children)
        }else console.error("Cant find node: ",tree.root)
    }
    addChild = (targetNodeId: number,child: ThreadElement) => {
        const target = this.find(targetNodeId);
        if(target){
            target.children.push({element:child,parent:target,children:[]})
        } else console.log("Cant add reply")
    }

    display = () => {
        console.log("Displaying Tree...")
        this.logChildren(this.root)
    }
    private logChildren = (node: ThreadNode) => {
        console.log("--".repeat(node.element.level + 1), node.element.id)
        if (node.children.length === 0) return
        node.children.forEach((c) => this.logChildren(c))
    }

    private static constructThreadTree = (threadResponse: ThreadResponse,parent: ThreadNode | null = null) => {
        const levelMap: Map<number, ThreadElement[]> = getLevelMap(threadResponse.children)
        console.log("LEVEL MAP")
        console.log(levelMap)
        const root: ThreadNode = {parent: parent, element: threadResponse.root, children: []}
        this.populate(levelMap, root.element.level + 1, root);
        return root;
    }

    private static populate = (levelMap: Map<number, ThreadElement[]>, level: number, node: ThreadNode) => {
        console.log("Populating:", node, level)
        console.log("LEVEL MAP GET")
        console.log(levelMap.get(level))
        const children: ThreadNode[] = levelMap.get(level)?.filter(c => c.parentId === node.element.id).map(c => ({
            parent: node,
            element: c,
            children: []
        })) ?? []
        console.log("Children:", children)
        if (children.length === 0) return

        node.children.push(...children)
        node.children.forEach(child => this.populate(levelMap, level + 1, child))
    }


}

export const getLevelMap = (threads: ThreadElement[]) => {
    const map = new Map<number, ThreadElement[]>
    threads.forEach(r => {
        if (map.get(r.level) == undefined) map.set(r.level, [])
        map.get(r.level)?.push(r)
    })
    return map
}


