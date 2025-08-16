import type { UseQueryResult } from "@tanstack/react-query";

export type UseQueryResultWrapper<T> = UseQueryResult<T,unknown>['data']  