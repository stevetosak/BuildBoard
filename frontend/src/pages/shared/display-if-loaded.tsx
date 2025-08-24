import type { ReactNode } from "react";

type DisplayIfLoadedProps<T> = {
	children : (data:T) => ReactNode;
	componentIfNotLoaded?: ReactNode;
	data: T|undefined
}

const DisplayIfLoaded = <T,>({componentIfNotLoaded,children,data}:DisplayIfLoadedProps<T>) => {
	if(!data) return componentIfNotLoaded || <div></div>;
	return <>{children(data)}</>
}

export default DisplayIfLoaded;