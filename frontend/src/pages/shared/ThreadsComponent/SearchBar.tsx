import { cn } from "@lib/utils/utils.ts";
import { SearchIcon } from "lucide-react";
import React, {
	useMemo,
	useState,
	type Dispatch,
	type SetStateAction,
} from "react";
import type { SearchOptions } from ".";
import { debounceGenerator } from "@shared/api-utils";
import DisplayIfLoaded from "@pages/shared/display-if-loaded.tsx";

type SearchBarProp = {
	className: string;
	triggerFetch: React.Dispatch<React.SetStateAction<SearchOptions>>;
	helperText?: string;
};

const parseInput = (
	userInput: string,
	setOptions: Dispatch<SetStateAction<SearchOptions>>,
) => {
	if (
		userInput.match(
			/(type:\w+).*?(type:\w+)|type:(?!project|topic)\w+|(filters:\w+).*?(filters:\w+)/,
		)
	)
		return;

	const searchOptions = userInput.split(/\s+/).reduce(
		(acc, word) => {
			if (word.startsWith("type:")) {
				acc.threadType = word.split(":")[1];
			} else if (word.startsWith("tag:")) {
				acc.tag.push(word.split(":")[1]);
			} else if (word.startsWith("title:")) {
				acc.title = word.split(":")[1];
			} else if (word.startsWith("content:")) {
				acc.content = word.split(":")[1];
			}

			return acc;
		},
		{
			tag: [] as string[],
			threadType: "",
			title: "",
			content: "",
		} as SearchOptions,
	);

	setOptions((options: SearchOptions) => ({
		...searchOptions,
		projectId: options.projectId,
	}));
};

const SearchBar = ({ className, triggerFetch, helperText }: SearchBarProp) => {
	const [query, setQuery] = useState<string>("");
	const triggerDebounce = useMemo(
		() =>
			debounceGenerator((userInput: string) => {
				parseInput(userInput, triggerFetch);
			}, 400),
		[triggerFetch],
	);

	return (
		<div>
			<div className="h-full grid grid-rows-auto justify-center">
				<div
					className={cn(
						className,
						" group flex items-center rounded-md border border-input pl-2 text-lg text-white  border-[#285842] ring-offset-background bg-bg-2 rounded-3xl",
					)}
				>
					<div className={'pe-2 border-r-1  border-[#285842]'}>
						<SearchIcon className="h-[16px] w-[16px] group-focus-within:text-accent stroke-accent" />
					</div>
					<input
						type="search"
						value={query}
						className="w-full peer pl-1 placeholder:text-muted-foreground focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50"
						placeholder="Search"
						onChange={(ev) => {
							setQuery(ev.target.value);
							triggerDebounce(ev.target.value);
						}}
					/>
				</div>
				<DisplayIfLoaded data={helperText}>
					{(helperText: string) => (
						<div className="text-[#327b5a] hover:text-accent">
							<p>{helperText}</p>
						</div>
					)}
				</DisplayIfLoaded>
			</div>
		</div>
	);
};

export default SearchBar;
