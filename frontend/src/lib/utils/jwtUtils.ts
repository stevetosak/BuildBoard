import {decodeJwt} from "jose"
import type {UserAuth, UserRoles} from "@shared/security-utils.ts";
import {defaultSecurityContext} from "@context/security-context.ts";
export const parseJwt = (jwtString?: string | null) : UserAuth => {
    if(jwtString == null || jwtString.length === 0) return defaultSecurityContext;
    const payload = decodeJwt(jwtString);
    return {
        id: payload.sub ?? "",
        username: payload["username"] as string,
        authorities: payload["authorities"] as UserRoles[],
        isAuth: true
    }
}