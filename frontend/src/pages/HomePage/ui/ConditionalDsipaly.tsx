import type { UseQueryResult } from "@tanstack/react-query";
import type { ComponentType , ReactNode } from "react";

type DisplayIfUserExistsProps<T,V> = {
	dataLoadingComponent?: ReactNode ;
	ErrorComponent?:ComponentType<{error:V}>,
	query: UseQueryResult<T,V>,
	children: (data: T) => ReactNode;
};

const ConditionalDisplay = <T,V>({
	children,
	dataLoadingComponent,
	ErrorComponent,
	query
}: DisplayIfUserExistsProps<T,V>) => {
	const {
		error,
		isError,
		isLoading,
		data
	} = query;

	if(isLoading)
		return dataLoadingComponent || <div></div>

	if(isError)
			return ErrorComponent ? <ErrorComponent error={error}/> : <div></div>

	return <>{children(data as T)}</>
};

export default ConditionalDisplay;
