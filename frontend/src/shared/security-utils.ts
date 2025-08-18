export type UserRoles = Uppercase<"ROLE_PROJECT_OWNER" | "ROLE_Developer" | "ROLE_User" | "ROLE_MODERATOR">


export type UserAuth = {
	id: string,
    username : string, 
    authorities: UserRoles[] ,
	isAuth: boolean
} | null 


export type JWTResponse = { 
    token : string 
}

export const getToken = () => {
	return localStorage.getItem("token") || "";
}

export const getAuthHeader = (): RequestInit => {
	let token = localStorage.getItem("token") || "";
    
    if(token)
        token = "Bearer " + token;

	return {
		headers: {
			Authorization: token
		},
	};
};
