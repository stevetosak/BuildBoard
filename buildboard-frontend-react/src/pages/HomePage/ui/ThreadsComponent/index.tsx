import { useQuery } from "@tanstack/react-query";
import LoadingBlock from "./LoadingBlock";
import { fetchThreads } from "@pages/HomePage/data/initialFetch";
import {
	Card,
	CardContent,
	CardFooter,
	CardHeader,
} from "@/components/ui/card";
import SearchBar from "./SearchBar";
import type { NamedThread, Page } from "@shared/api-types";
import { useEffect, useState } from "react";



const isDataFetched = (
	data: undefined | Page<NamedThread[]>,
	isLoading: boolean,
): data is Page<NamedThread[]> => !isLoading;

const ThreadsComponent = () => {
	const [page,setPage] = useState<number>(0)
	const { data: dataPromise, isLoading } = useQuery({
		queryKey: [page],
		queryFn: ({queryKey}) => fetchThreads(queryKey[0]),
	});
	const [data,setData] = useState<Page<NamedThread[]>|undefined>(dataPromise); 

	useEffect(() => {
		fetchThreads(page)
	},[page])

	return (
		<section
			className="grid grid-rows-[8em_auto] h-screen overflow-scroll"
			style={{ scrollbarWidth: "none" }}
		>
			<SearchBar className="w-1/2 justify-self-center self-center" />
			<div className="flex flex-col gap-5 p-5 row-start-2 items-center">
				{isDataFetched(data, isLoading)
					? data.content.map((thread) => (
							<Card
								className={`border-0 border-l-1 border-accent-2 rounded-none rounded-r-xl cursor-pointer  bg-bg-2 text-[#eaeaea] w-3/4 min-w-[10em]`}
							>
								<CardHeader className="w-full">
									<div className="w-full grid-cols-[repeat(2,max-content)_auto] gap-2 items-center grid">
										<img
											className="w-10 h-10 border-2 border-gray-700 p-1 rounded-full"
											src="/vite.svg"
											alt="User Avatar"
										/>
										<span className="text-sm font-medium text-white">
											{thread.creator.username}
										</span>
										<span className="text-sm font-medium text-gray-400 hover:text-white justify-self-end">
											{thread.createdAt}
										</span>
									</div>
									<span className="text-sm text-muted-foreground mt-1">{}</span>
								</CardHeader>
								<CardContent className="text-left ">
									<p className="text-sm text-gray-300 truncate">
										{thread.content.content}
									</p>
								</CardContent>
								<CardFooter className="flex wrap gap-5">
									{thread.content.tags.map((tag) => (
										<div key={tag} className="bg-[#2b6c4f] hover:bg-[#3a976d] rounded-xl px-2 p-1">
											<span>#{tag}</span>
										</div>
									))}
								</CardFooter>
							</Card>
						))
					: Array.from(Array(3)).map((i) => (
							<LoadingBlock
								key={i}
								w={"80%"}
								h={"40%"}
							/>
						))}
			</div>
		</section>
	);
};

export default ThreadsComponent;
