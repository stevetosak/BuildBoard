import { cn } from "@/lib/utils";
import { SearchIcon } from "lucide-react";
import React, { useMemo, useState } from "react";
import type { SearchOptions } from ".";
import { debounceGenerator } from "@shared/api-utils";

type SearchBarProp = {
	className: string;
	triggerFetch: React.Dispatch<React.SetStateAction<SearchOptions>>;
};

const isFiltersInputCorrect = (filters:string| SearchOptions['filters']):filters is SearchOptions['filters'] => { 
	return filters == 'all' || filters == 'title' || filters == 'content' 
}

const parseInput = (
	userInput: string,
	setOptions: (options: SearchOptions) => void,
) => {
	if (userInput.match(/(type:\w+).*?(type:\w+)|type:(?!project|topic)\w+|(filters:\w+).*?(filters:\w+)/))
		return;

	const searchOptions = userInput.split(/\s+/).reduce(
		(acc, word) => {
			if (word.startsWith("type:")) {
				acc.threadType = word.split(":")[1];
			} else if (word.startsWith("tag:")) {
				acc.tags.push(word.split(":")[1]);
			}
			else if(word.startsWith("filters:")){
				const potentialFilter = word.split(":")[1]
				if(isFiltersInputCorrect(potentialFilter))
					acc.filters = potentialFilter 
			}
			else {
				if (acc.query.length == 0) acc.query = word;
				else acc.query += " " + word;
			}
			return acc;
		},
		{ query: "", tags: [] as string[], threadType: "", filters:'all' } as SearchOptions,
	);

	setOptions(searchOptions);
};

const SearchBar = ({ className, triggerFetch }: SearchBarProp) => {
	const [query, setQuery] = useState<string>("");
	const triggerDebounce = useMemo(
		() =>
			debounceGenerator((userInput: string) => {
				parseInput(userInput, triggerFetch);
			}, 400),
		[triggerFetch],
	);

	return (
		<div className="px-[2em] pt-[1em]">
			<div className="h-full grid grid-rows-auto justify-center">
				<div
					className={cn(
						className,
						" group flex items-center rounded-md border border-input pl-2 text-lg text-white focus-within:border-accent  ring-offset-background bg-bg-2 h-[3em]",
					)}
				>
					<SearchIcon className="h-[16px] w-[16px] group-focus-within:text-accent" />
					<input
						type="search"
						value={query}
						className="w-full pl-1 placeholder:text-muted-foreground focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50"
						placeholder="Search"
						onChange={(ev) => {
							setQuery(ev.target.value);
							triggerDebounce(ev.target.value);
						}}
					/>
				</div>
				<div className="text-[#327b5a] hover:text-accent">
					<p>Use tag:tag_name and type:topics|projects to filter threads.</p>
				</div>
			</div>
		</div>
	);
};

export default SearchBar;
