import * as React from "react";
import {Outlet} from "react-router-dom";

export const Layout = ({children}: {children:React.ReactNode}) => {
    return (
        <div>
            {children}
        </div>
    )
}