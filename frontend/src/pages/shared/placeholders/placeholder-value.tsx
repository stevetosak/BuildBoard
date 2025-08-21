import type { ReactElement, ReactNode } from "react";
import useDataContext from "./use-data";

export type PlaceHolderProps<T> = { 
    children : (data:T) => ReactNode,
    componentIfDataNullable?: ReactElement,
}

const PlaceHolderWithValue=  <T,>({children, componentIfDataNullable } : PlaceHolderProps<T>) => { 
    const contextData = useDataContext() as T|undefined|null

    if(contextData===null || contextData===undefined) {
        if(!componentIfDataNullable)
            return <div></div>
        return componentIfDataNullable
    }

    return <>{children(contextData)}</>
}

export default PlaceHolderWithValue