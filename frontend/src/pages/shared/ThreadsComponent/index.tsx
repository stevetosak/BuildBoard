import { useInfiniteQuery} from "@tanstack/react-query";
import LoadingBlock from "./LoadingBlock";
import { Fragment, useState } from "react";
import {
	Card,
	CardContent,
	CardFooter,
	CardHeader,
} from "@/components/ui/card";
import SearchBar from "./SearchBar";
import ConditionalDisplay from "../../HomePage/ui/ConditionalDsipaly.tsx";
import { useNavigate } from "react-router-dom";
import { getUrlForThread } from "@shared/url-generation";
import { useRef, type RefObject } from "react";
import { debounceGenerator, type FetchNamedTopics, type NamedThread } from "@shared/api-utils";


export type SearchOptions = { 
	query: string;
	tags: string[];
	threadType: string;
	filters:"title"|"content"|"all"
}

export type ThreadsComponentProps = { 
	fetchTopics: FetchNamedTopics,
}

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
): IntersectionObserver => {
	const observer = new IntersectionObserver(
		(entry) => {
			const lastCard = entry[0];
			if (lastCard.isIntersecting) {
				observer.disconnect();
				callbackWhenIntersting();
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

const createKeyForNamedThread = (namedThread: NamedThread) => namedThread.content.title  + '-' +  namedThread.threadType

//TODO: napraj nekoj datata da mu ja davat 
const ThreadsComponent = ({fetchTopics}: ThreadsComponentProps) => {
	const [searchOptions, setSearchOptions] = useState<SearchOptions>({query:"", tags: [], threadType: "", filters:'all'});
	const {
		data: namedThreads,
		hasNextPage,
		fetchNextPage,
		isFetchingNextPage,
	} = useInfiniteQuery({
		queryKey: ["namedThreads", searchOptions],
		queryFn: ({ pageParam, queryKey }) => fetchTopics(pageParam,queryKey[1] as SearchOptions), 
		initialPageParam: 0,
		getNextPageParam: (lastPage) =>
			lastPage.pageable.pageNumber + 1 < lastPage.totalPages
				? lastPage.pageable.pageNumber + 1
				: undefined,
	});

	const navigate = useNavigate();
	const observer = useRef<IntersectionObserver>(
		createIntersectionObserver(debounceGenerator(fetchNextPage, 50)),
	);

	return (
		<section
			className="grid grid-rows-[8em_auto] h-screen overflow-scroll"
			style={{ scrollbarWidth: "none" }}
		>
			<SearchBar triggerFetch={setSearchOptions} className=""/>
			<div className="flex flex-col gap-5 p-5 row-start-2 items-center">
				<ConditionalDisplay
					data={namedThreads}
					dataLoadingComponent={<LoadingBlocks />}
				>
					{(namedThreads) =>
						namedThreads.pages.map((page, i) => {
							const isLastPage = i === namedThreads.pages.length - 1;
							

							if (!hasNextPage) observer.current.disconnect();

							return (
								<Fragment key={page.content.length > 0 ? createKeyForNamedThread(page.content[0]) : 0}>
									{page.content.map((namedThread, idx) => (
										<Card
										 	key={createKeyForNamedThread(namedThread)}
											ref={
												hasNextPage &&
												isLastPage &&
												idx == page.content.length - 1
													? (el) => handleRegistrationOfObserver(observer, el)
													: undefined
											}
											className={`border-0 border-l-1 border-accent rounded-none rounded-r-xl cursor-pointer bg-bg-2 text-[#eaeaea] w-3/4 min-w-[10em] hover:border-2 transition-border duration-50`}
											onClick={() =>
												navigate( "/" +
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
												<h3 className="mt-1">{namedThread.content.title}</h3>
											</CardHeader>
											<CardContent className="text-left ">
												<p className="text-sm text-gray-300 truncate">
													{namedThread.content.content + '...'}
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
								</Fragment>
							);
						})
					}
				</ConditionalDisplay>
			</div>
		</section>
	);
};

export default ThreadsComponent;
