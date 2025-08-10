import type { User } from "@pages/HomePage/data/fetchUser";
import OneActiveAtTime from "@components/shared/OneActiveAtTime";
import {
	HoverCard,
	HoverCardContent,
	HoverCardTrigger,
} from "@radix-ui/react-hover-card";
import { UserIcon } from "lucide-react";
import { Button } from "@components/ui/button";
import { Link } from "react-router-dom";

type UserInfoProp = {
	user: User;
};

const UserInfo = ({ user }: UserInfoProp) => {
	if (user)
		return (
			<OneActiveAtTime
				initActive={1}
				activeCls="text-accent"
				nonActiveCls="text-white"
			>
				<span>{user.username}</span>
				<HoverCard>
					<HoverCardTrigger>
						<UserIcon />
					</HoverCardTrigger>
					<HoverCardContent className="text-[.7rem] p-1 bg-sidebar-bg rounded ">
						See profile
					</HoverCardContent>
				</HoverCard>
			</OneActiveAtTime>
		);

	return (
		<OneActiveAtTime
			activeCls="text-accent"
			nonActiveCls="text-white"
		>
			<Button variant={"outline"}>
				<Link to="/user/register">Register</Link>
			</Button>
			<Button variant={"outline"}>
				<Link to="/user/login">Login</Link>
			</Button>
		</OneActiveAtTime>
	);
};

export default UserInfo;
