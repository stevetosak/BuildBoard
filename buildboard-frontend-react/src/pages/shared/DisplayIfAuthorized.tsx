import type { UserAuth } from "@shared/security-utils";
import type React from "react";

type DisplayIfAuthorizedProps={
    roles: NonNullable<UserAuth>["authorities"][],
    requiredRoles: NonNullable<UserAuth>["authorities"],
    children: React.ReactNode,
    AuthFailedComponent?: React.ComponentType
}

const DisplayIfAuthorized = ({roles=[], requiredRoles, children, AuthFailedComponent}: DisplayIfAuthorizedProps) => {
    if(roles.includes(requiredRoles)) {
        return AuthFailedComponent ? <AuthFailedComponent /> : <div></div>
    }

    return {children}
} 

export default DisplayIfAuthorized;