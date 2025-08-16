export type UserRoles = Uppercase<"ROLE_Project_Owner" | "ROLE_Developer" | "ROLE_User" | "ROLE_MODERATOR">


export type UserAuth = { 
    username : string, 
    authorities: UserRoles[] 
} | null 


export type JWTResponse = { 
    token : string 
}

export const getAuthHeader = (): RequestInit => {
	let user = localStorage.getItem("token") || "";
    
    if(user)
        user = "Bearer " + user;  

	return {
		headers: {
			Authorization: user
		},
	};
};
