
/** 
 *  @summary Stores endpoints definitions 
 *  @description Every member should be a @type {function} 
 *  @returns Valid API endpoint as @type {string}, which must start with '/' 
*/
const endpoints = {
	user: (username: string) =>
		 "/users" + `/${username}`,
	threads: (page: number = 0) =>
		 "/threads" + `?page=${page}`,
    auth: () => '/login',
    register: () => "/register",
	web_socket: () => "/channel-websocket"
};
const endpointsKeys = Object.keys(endpoints)

const HOST = import.meta.env.VITE_HOST
const handleEndpoints: ProxyHandler<typeof endpoints> = {
	get(_, prop) {
        if(typeof prop == 'symbol') throw new Error("No implementation for symbol")
		if (!endpointsKeys.includes(prop as string)) throw new Error("Prop is not defined");

		const orig = endpoints[prop as keyof typeof endpoints];
		if (typeof orig !== "function") throw new Error("Endpoint is not a function");
		
		return (...args: unknown[]) =>
			HOST + (orig as (...a: unknown[]) => string)(...args);
	},
};

export default new Proxy(endpoints,handleEndpoints) 
