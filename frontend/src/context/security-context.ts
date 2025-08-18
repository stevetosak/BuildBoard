import type { UserAuth } from "@shared/security-utils";
import { createContext } from "react";

export const defaultSecurityContext = {id:"",username:"",authorities:[],isAuth: false}
const SecurityContext = createContext<UserAuth>(defaultSecurityContext)

export default SecurityContext; 