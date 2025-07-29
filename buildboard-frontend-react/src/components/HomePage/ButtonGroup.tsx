import {Button} from "@/components/ui/button.tsx";
import {Link} from "react-router-dom";
import {useState} from "react";

const ButtonGroup = () => {
    const [isRegisterActive, setIsRegisterActive] = useState<boolean>(true);

    return (
        <>
            <Button
                onMouseEnter={_ => setIsRegisterActive(true)}
                variant="outline"
                className={`text-white ${isRegisterActive ? 'text-accent' : 'text-white'} hover:text-white`}>
                <Link to="/user/register">Register</Link>
            </Button>
            <Button
                onMouseEnter={_ => setIsRegisterActive(false)}
                variant="outline"
                className={`text-white ${!isRegisterActive ? 'text-accent' : 'text-white'} hover:text-white`}>
                <Link to="/user/login">Login</Link>
            </Button>
        </>
    )
}

export default ButtonGroup