import { useInfiniteQuery, useQuery } from "@tanstack/react-query";
import LoadingBlock from "./LoadingBlock";
import { fetchThreads } from "@pages/HomePage/data/initialFetch";
import {
	Card,
	CardContent,
	CardFooter,
	CardHeader,
} from "@/components/ui/card";
import SearchBar from "./SearchBar";
import DisplayDataIfLoaded from "../DisplayDataIfLoaded";
import { useNavigate } from "react-router-dom";
import { getUrlForThread } from "@shared/url-generation";
import { useRef, type RefObject } from "react";
import React from "react";

const LoadingBlocks = () => {
	return (
		<>
			{Array.from(Array(3)).map((_, i) => (
				<LoadingBlock
					key={i}
					w={"80%"}
					h={"40%"}
				/>
			))}
		</>
	);
};

const createIntersectionObserver = (
	callbackWhenIntersting: () => void,
	isFetching: boolean,
): IntersectionObserver => {
	const observer = new IntersectionObserver(
		(entry) => {
			const lastCard = entry[0];
			if (lastCard.isIntersecting && !isFetching) {
				callbackWhenIntersting();
				observer.disconnect();
			}
		},
		{ threshold: 0.8 },
	);
	return observer;
};

const handleRegistrationOfObserver = (
	observer: RefObject<IntersectionObserver>,
	el: HTMLElement | null,
) => {
	if (!el) return undefined;
	observer.current.disconnect();
	observer.current.observe(el);
};

const ThreadsComponent = () => {
	const {
		data: namedThreads,
		hasNextPage,
		fetchNextPage,
		isFetchingNextPage,
	} = useInfiniteQuery({
		queryKey: ["namedThreads"],
		queryFn: ({ pageParam }) => fetchThreads(pageParam),
		initialPageParam: 0,
		getNextPageParam: (lastPage) =>
		lastPage.pageable.pageNumber + 1 < lastPage.totalPages
			? lastPage.pageable.pageNumber + 1
			: undefined,
	});

	const navigate = useNavigate();
	const observer = useRef<IntersectionObserver>(
		createIntersectionObserver(fetchNextPage, isFetchingNextPage),
	);

	return (
		<section
			className="grid grid-rows-[8em_auto] h-screen overflow-scroll"
			style={{ scrollbarWidth: "none" }}
		>
			<SearchBar className="w-1/2 justify-self-center self-center" />
			<div className="flex flex-col gap-5 p-5 row-start-2 items-center">
				<DisplayDataIfLoaded
					data={namedThreads}
					dataUndefinedComponent={<LoadingBlocks />}
				>
					{(namedThreads) =>
						namedThreads.pages.map((page, i) => {
							const isLastPage = i === namedThreads.pages.length - 1;

							if (!hasNextPage) observer.current.disconnect();

							return (
								<React.Fragment key={i}>
									{page.content.map((namedThread, idx) => (
										<Card
											ref={
												hasNextPage &&
												isLastPage &&
												idx == page.content.length - 1
													? (el) => handleRegistrationOfObserver(observer, el)
													: undefined
											}
											className={`border-0 border-l-1 border-accent rounded-none rounded-r-xl cursor-pointer bg-bg-2 text-[#eaeaea] w-3/4 min-w-[10em] hover:border-2 transition-border duration-50`}
											onClick={() =>
												navigate(
													getUrlForThread(
														namedThread.content.title,
														namedThread.threadType,
													),
												)
											}
										>
											<CardHeader className="w-full">
												<div className="w-full grid-cols-[repeat(2,max-content)_auto] gap-2 items-center grid">
													<img
														className="w-10 h-10 border-2 border-gray-700 p-1 rounded-full"
														src="/vite.svg"
														alt="User Avatar"
													/>
													<span className="text-sm font-medium text-white">
														{namedThread.creator.username}
													</span>
													<span className="text-sm font-medium text-gray-400 hover:text-white justify-self-end">
														{namedThread.createdAt}
													</span>
												</div>
												<span className="text-sm text-muted-foreground mt-1">
													{}
												</span>
											</CardHeader>
											<CardContent className="text-left ">
												<p className="text-sm text-gray-300 truncate">
													{namedThread.content.content}
												</p>
											</CardContent>
											<CardFooter className="flex wrap gap-5">
												{namedThread.content.tags.map((tag) => (
													<div
														key={tag}
														className="bg-[#2b6c4f] hover:bg-[#3a976d] rounded-xl px-2 p-1"
													>
														<span>#{tag}</span>
													</div>
												))}
											</CardFooter>
										</Card>
									))}
									{isFetchingNextPage && (
										<span>
											Loading <span className="animate-pulse">...</span>
										</span>
									)}
								</React.Fragment>
							);
						})
					}
				</DisplayDataIfLoaded>
			</div>
		</section>
	);
};

export default ThreadsComponent;
