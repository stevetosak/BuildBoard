import type { ReactElement, ReactNode } from "react"
import useDataContext from "./use-data"

export type PlaceHolderProps = { 
    children : ReactNode,
    componentIfDataNullable?: ReactElement,
}

const PlaceHolderNoValue=  ({children, componentIfDataNullable } : PlaceHolderProps) => { 
    const data = useDataContext() as unknown|undefined|null
    if(!data) {
        if(!componentIfDataNullable)
            return <aside></aside>
        return componentIfDataNullable
    }
    return <>{children}</>
}

export default PlaceHolderNoValue