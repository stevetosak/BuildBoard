import SecurityContextProvider from "@/components/auth/security-context-provider";
import { Outlet } from "react-router-dom";

const SecurityOutlet = ( )=> {    
    const token = localStorage.getItem("token")

    return ( 
        <SecurityContextProvider token={token}>
            <Outlet/>
        </SecurityContextProvider>
    );
}
 
export default SecurityOutlet;