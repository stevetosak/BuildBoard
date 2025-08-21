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
            return <div></div>
        return componentIfDataNullable
    }
    return <>{children}</>
}

export default PlaceHolderNoValue