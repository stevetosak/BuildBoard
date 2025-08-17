import type { UseQueryResultWrapper } from "@shared/useQuery-result-type";
import type React from "react";

type DisplayIfUserExistsProps<T> = {
	children: (data: T) => React.ReactElement | React.ReactNode;  
	dataUndefinedComponent?: React.ReactNode ;
	data: UseQueryResultWrapper<T>;
};

const DisplayDataIfLoaded = <T,>({
	children,
	dataUndefinedComponent,
	data,
}: DisplayIfUserExistsProps<T>) => {
	if(data)
		return <>{children(data)}</>
	return dataUndefinedComponent;
};

export default DisplayDataIfLoaded;
