import OneActiveAtTime from "@components/shared/OneActiveAtTime";
import {
	HoverCard,
	HoverCardContent,
	HoverCardTrigger,
} from "@radix-ui/react-hover-card";
import { UserIcon } from "lucide-react";

type UserInfoProp = {
	username: string ;
};

const UserInfo = ({ username }: UserInfoProp) => {
	return (
		<OneActiveAtTime
			initActive={1}
			activeCls="text-accent"
			nonActiveCls="text-white"
		>
			<span>{username}</span>
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
};

export default UserInfo;
