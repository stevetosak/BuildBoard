import type { UserAuth } from "@/shared/types";
import { createContext } from "react";

const SecurityContext = createContext<UserAuth>(null)

export default SecurityContext; 