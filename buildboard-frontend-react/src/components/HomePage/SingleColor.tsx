
import {type ReactElement, useState} from "react";
import {type SingleColorCtx} from "../../pages/HomePage"

type SingleColorProps = {
    children : ReactElement[]
    ctx: SingleColorCtx
}

const SingleColor = ({children, ctx} :  SingleColorProps) => {
    const [isRegisterActive, setIsRegisterActive] = useState<boolean>(true);

    ctx.registered = [isRegisterActive]
    ctx.setTrue = () => setIsRegisterActive(true)
    ctx.setFalse = () => setIsRegisterActive(false)

    return (
        <>
            {...children}
        </>
    )
}

export default SingleColor