import type { ApiError } from "@shared/api-utils.ts";
import type { UseQueryResult } from "@tanstack/react-query";
import type { ComponentType , ReactNode } from "react";

type DisplayIfUserExistsProps<T> = {
	dataLoadingComponent?: ReactNode ;
	ErrorComponent?:ComponentType<{error:ApiError}>,
	query: UseQueryResult<T,ApiError>,
	children: (data: T) => ReactNode;
};

const ConditionalDisplay = <T,>({
	children,
	dataLoadingComponent,
	ErrorComponent,
	query
}: DisplayIfUserExistsProps<T>) => {
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
