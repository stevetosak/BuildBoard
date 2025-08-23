import UserLogo from "@components/shared/UserLogo";
import { Button } from "@components/ui/button";
import { Link } from "react-router-dom";

type UserShortRowProps = { 
    username:string,
    logo:URL,
}

const UserShortRow = ({username,logo}:UserShortRowProps) => {
	return (
		<Button
			variant="link"
			key={username}
			className="p-0 gap-2 text-lg"
		>
			<div className="border-2 rounded p-0.5">
				<UserLogo
					url={logo}
					alt={`${username} logo`}
				/>
			</div>
			<Link to={"/profile/" + username}>{username}</Link>
		</Button>
	);
};

export default UserShortRow
