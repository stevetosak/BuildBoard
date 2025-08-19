/**
 * Po redot na neshtata i ovaj ke se vodit /project/:contentPart  
 * {@link contentPart} ni e topic|channel|description|managment 
 * sidebar ke se koristit od {@link ../Homepage/index.tsx} i {@link ../Homepage/search} 
 */

import SecurityContext from "@context/security-context";
import { useContext } from "react";

// import FriendsPopUp from "@pages/shared/FriendsPopUp";
// import UserInfo from "@pages/shared/UserInfo";

const ProjectPage = () => {
    const user = useContext(SecurityContext)    

    return (
        <main className="layout">

        </main>
    );
}
 
export default ProjectPage;