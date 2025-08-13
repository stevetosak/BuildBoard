import SecurityContext from "@/context/security-context";
import type { UserAuth } from "@/shared/types";
import {  type ReactElement } from "react";
import {useJwt} from 'react-jwt'

type SecurityContextProviderProps = { 
    children : ReactElement[] | ReactElement, 
    token:string|null
}  

const SecurityContextProvider = ({children, token}: SecurityContextProviderProps) => {
    const {decodedToken} = useJwt<UserAuth>(token ?? "")

    return (
        <SecurityContext.Provider value={decodedToken && {
            username : decodedToken.username ,
            authorities : decodedToken.authorities
        }}>{children}</SecurityContext.Provider>
    )
}

export default SecurityContextProvider