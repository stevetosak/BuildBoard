import type { ReactNode } from "react"
import { DataContext } from "./use-data"

type DataContextProviderProps = { 
    children : ReactNode, 
    value:unknown
}
//TODO: ova so generics da e nekako ama neznam stvarno kako 
const DataContextProvider =  ({children,value}:DataContextProviderProps) => { 
    return <DataContext.Provider value={value}>
        {children}
    </DataContext.Provider>
}

export default DataContextProvider