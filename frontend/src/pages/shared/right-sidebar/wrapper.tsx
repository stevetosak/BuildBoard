import { Children, type ReactNode } from "react"
import DataContextProvider from "../placeholders/data-context-provider"

export type RightSidebarProps<T> = { 
    children : ReactNode,
    data?: T,
}


const Sidebar = <T,>({children, data}:RightSidebarProps<T>) => {
    if(Children.count(children) != 2) throw new Error("The component works with 2 components")    
     
    const [header, body] = Children.toArray(children)

    return ( 
        <DataContextProvider value={data}>
            <section className="pt-4 flex flex-col gap-2">
				<div className="w-full gap-2 flex justify-center items-center">
                    {header}
				</div>
                <div className="flex-grow-1 grid justify-end pt-[20%]">
                    {body}
                </div>
			</section>
        </DataContextProvider>
    )
}

export default Sidebar