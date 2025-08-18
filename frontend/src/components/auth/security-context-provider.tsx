import SecurityContext from "@/context/security-context";;
import {  type ReactElement } from "react";
import {parseJwt} from "@lib/utils/jwtUtils.ts";

type SecurityContextProviderProps = { 
    children : ReactElement[] | ReactElement, 
    token:string|null
}  

const SecurityContextProvider = ({children, token}: SecurityContextProviderProps) => {
    const userAuth = parseJwt(token)

    return (
        <SecurityContext.Provider value={userAuth && {
            ...userAuth
        }}>
            {children}
        </SecurityContext.Provider>
    )
}

export default SecurityContextProvider