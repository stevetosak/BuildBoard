import { Trash2 } from "lucide-react";
import type { ComponentProps } from "react";
import { twJoin } from "tailwind-merge";

type TrashIconProps = {
	props?: Omit<ComponentProps<typeof Trash2>, "className">;
	className?:string
}

const TrashIcon = ({props,className = ''}:TrashIconProps)=> (
	<Trash2 {...props} className={twJoin("text-accent stroke-[#46a77b] hover:stroke-accent",className)} />
)

export default TrashIcon