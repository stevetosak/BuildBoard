import { createContext, useContext } from "react";

export const DataContext = createContext<unknown>(null)

const useDataContext = () => { 
    const ctx = useContext(DataContext)
    return ctx  
}

export default useDataContext