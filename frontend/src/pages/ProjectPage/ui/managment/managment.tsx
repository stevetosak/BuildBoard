import iconUrl from "@assets/Icon.jpg";
import { ChevronDown, X } from "lucide-react";
import { useOutletContext } from "react-router-dom";
import type { Project } from "@shared/api-utils.ts";
import TrashIcon from "@pages/ProjectPage/ui/managment/trashIcon.tsx";
import {
	DropdownMenu, DropdownMenuArrow,
	DropdownMenuContent,
	DropdownMenuItem,
	DropdownMenuLabel,
	DropdownMenuSeparator,
	DropdownMenuTrigger,
} from "@radix-ui/react-dropdown-menu";
import { Button } from "@components/ui/button.tsx";
import SearchBar from "@pages/shared/ThreadsComponent/SearchBar.tsx";

const hoverUserRowStyle = "border-[#46a77b] hover:border-accent";
const dropDownProps = 'px-2 w-[10em]'

const Managment = () => {
	const project = useOutletContext<Project>();
	const largestUsernameInChars = 10;
	const grid = `grid grid-cols-[1em_5em_${largestUsernameInChars}em_auto] px-3 gap-2 items-center`;

	return (
		<section className="grid grid-rows-[minmax(15rem,20vh)_auto] p-5">
			<div className="relative w-1/4 justify-self-center">
				<div className="absolute w-full h-full">
					<img
						className="w-full h-full"
						src={iconUrl}
					/>
				</div>
			</div>
			<section
				className={
					"h-full rounded-xl border-[#285842] border-1 grid grid-rows-[3em_auto]"
				}
			>
				<div
					className={`bg-[#061833] rounded-t-xl  border-b-1  border-[#285842] items-center ${grid}`}
				>
					<span></span>
					<span className={"h-fit"}>Logo</span>
					<span className={"h-fit justify-self-center"}>Username</span>
					<div className={'justify-self-end'}>
					<SearchBar />
					</div>
				</div>
				<div className={"h-full overflow-scroll flex flex-col"}>
					<div>
						<div
							className={`user-info-row ${grid} p-0 px-2 items-center border-b-1 border-[#031c3e]`}
						>
							<span>&#8226;</span>
							<div className={"w-[5em] h-[5em] rounded-xl"}>
								<img
									src={iconUrl}
									className={"w-full h-full"}
								/>
							</div>
							<span
								className={`justify-self-center w-[${largestUsernameInChars}em]`}
							>
								viki123v
							</span>
							<div
								className={
									"justify-self-end flex justify-between items-center h-full w-[13em]"
								}
							>
								<div className={"grid grid-cols-2 gap-1 flex items-center"}>
									<span>Roles</span>
									<DropdownMenu defaultOpen>
										<DropdownMenuTrigger asChild>
											<Button
												variant={"ghost"}
												className={"hover:bg-transparent"}
											>
												<ChevronDown
													className={"stroke-accent cursor-pointer"}
												/>
											</Button>
										</DropdownMenuTrigger>
										<DropdownMenuContent
											className={"bg-sidebar-bg rounded-lg py-[1.2em] border-[#285842] border-1"}
											align={"start"}
										>
											<DropdownMenuLabel className={'text-lg'}>Viki's Roles</DropdownMenuLabel>
											<DropdownMenuSeparator className={'h-5'}/>
											<DropdownMenuItem className={`flex w-[calc(1.2em+min-content)] justify-between text-md ${dropDownProps} hover:border-0 hover:outline-0`}>
												<span>Manager</span>
												<X className={'stroke-red-500 cursor-pointer'} />
											</DropdownMenuItem>
											<DropdownMenuArrow/>
										</DropdownMenuContent>
									</DropdownMenu>
								</div>
								<div
									className={`rounded-lg p-2 border-1 hover:text-accent cursor-pointer ${hoverUserRowStyle}`}
								>
									<TrashIcon />
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
		</section>
	);
};

export default Managment;
