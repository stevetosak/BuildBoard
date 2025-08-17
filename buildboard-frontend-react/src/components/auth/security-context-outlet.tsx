import SecurityContextProvider from "@/components/auth/security-context-provider";
import { Outlet, useLocation } from "react-router-dom";

const SecurityOutlet = ( )=> {    
    const token = localStorage.getItem("token")
    const {pathname} = useLocation(); 

    return ( 
        <SecurityContextProvider token={token}>
            <Outlet key={pathname}/>
        </SecurityContextProvider>
    );
}
 
export default SecurityOutlet;