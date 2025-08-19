import type { ReactElement, ReactNode } from "react";
import useDataContext from "./use-data";

export type PlaceHolderProps<T> = { 
    children : (data:T) => ReactNode,
    componentIfDataNullable?: ReactElement,
}

const PlaceHolderWithValue=  <T,>({children, componentIfDataNullable } : PlaceHolderProps<T>) => { 
    const data = useDataContext() as T|undefined|null
    if(data===null || data===undefined) {
        if(!componentIfDataNullable)
            return <div></div>
        return componentIfDataNullable
    }
    return <>{children(data)}</>
}

export default PlaceHolderWithValue