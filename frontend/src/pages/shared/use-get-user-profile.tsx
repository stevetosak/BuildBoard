import SecurityContext from "@context/security-context";
import { fetchUsers } from "@pages/HomePage/data/fetchUser";
import { useQuery } from "@tanstack/react-query";
import { useContext } from "react";

const useGetUserProfile = () => { 
	const user = useContext(SecurityContext);
	const { data: userProfile } = useQuery({
		queryKey: [user?.username],
		queryFn: fetchUsers,
	});

    return userProfile
}

export default useGetUserProfile