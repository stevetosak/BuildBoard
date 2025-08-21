import OneActiveAtTime from "@components/shared/OneActiveAtTime";
import { Button } from "@components/ui/button";
import { Link } from "react-router-dom";

const LoginLogoutButtons = () => (
	<OneActiveAtTime
		activeCls="text-accent"
		nonActiveCls="text-white"
	>
		<Button variant={"outline"}>
			<Link to="/register">Register</Link>
		</Button>
		<Button variant={"outline"}>
			<Link to="/login">Login</Link>
		</Button>
	</OneActiveAtTime>
);

export default LoginLogoutButtons