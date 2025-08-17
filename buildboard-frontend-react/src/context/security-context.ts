import type { UserAuth } from "@shared/security-utils";
import { createContext } from "react";

const SecurityContext = createContext<UserAuth>(null)

export default SecurityContext; 