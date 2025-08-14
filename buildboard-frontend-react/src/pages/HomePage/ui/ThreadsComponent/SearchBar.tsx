import { cn } from "@/lib/utils";
import {  SearchIcon } from "lucide-react";
import { useState } from "react";

type SearchBarProp = { 
    className : string
}

//TODO: add debounce support
//TODO: add # tags searching
const SearchBar = ({className}:SearchBarProp) => {
	const [query, setQuery] = useState<string>("");
	return (
      <div
        className={cn(" group flex h-10 items-center rounded-md border border-input pl-3 text-lg text-white focus-within:border-accent  ring-offset-background bg-bg-2 ",className)}
      >
        <SearchIcon className="h-[16px] w-[16px] group-focus-within:text-accent" />
        <input
          type="search"
          value={query}
          className="w-full p-2 placeholder:text-muted-foreground focus-visible:outline-none disabled:cursor-not-allowed disabled:opacity-50"
          placeholder="Search"
          onChange={ev => setQuery(ev.target.value)}
        />
      </div>
    );
};

export default SearchBar;
