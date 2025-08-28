import iconUrl from "@assets/Icon.jpg";
import { ChevronDown, X } from "lucide-react";
import { useOutletContext } from "react-router-dom";
import type { Project } from "@shared/api-utils.ts";
import TrashIcon from "@pages/ProjectPage/ui/managment/trashIcon.tsx";
import {
	DropdownMenu,
	DropdownMenuContent,
	DropdownMenuItem,
	DropdownMenuLabel,
	DropdownMenuSeparator,
	DropdownMenuTrigger,
} from "@components/ui/dropdown-menu.tsx";
import { Button } from "@components/ui/button.tsx";
import SearchBar from "@pages/shared/ThreadsComponent/SearchBar.tsx";
import {
	Dialog,
	DialogClose,
	DialogContent,
	DialogTitle,
	DialogTrigger,
} from "@components/ui/dialog.tsx";
import { DialogFooter, DialogHeader } from "@components/ui/dialog.tsx";
import { Select, SelectContent, SelectItem } from "@components/ui/select";

const hoverUserRowStyle = "border-[#46a77b] hover:border-accent";
const dropDownProps = "px-2 w-[10em]";
const grid = `grid px-3 gap-2 items-center`;

const Managment = () => {
	const project = useOutletContext<Project>();
	const largestUsernameInChars = 10;
	const gridStyle = {
		gridTemplateColumns: `1em 5em ${largestUsernameInChars}em auto`,
	};

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
					style={gridStyle}
				>
					<span></span>
					<span className={"h-fit"}>Logo</span>
					<span className={"h-fit justify-self-center"}>Username</span>
					<div className={"justify-self-end"}>
						<SearchBar />
					</div>
				</div>
				<div className={"h-full overflow-scroll flex flex-col"}>
					<div>
						<div
							className={`user-info-row ${grid} p-0 px-2 items-center border-b-1 border-[#031c3e]`}
							style={gridStyle}
						>
							<span>&#8226;</span>
							<div className={"w-[5em] h-[5em] rounded-xl"}>
								<img
									src={iconUrl}
									className={"w-full h-full"}
								/>
							</div>
							<span
								className={`justify-self-center`}
								style={{
									...gridStyle,
									width: `${largestUsernameInChars}em`,
								}}
							>
								viki123v
							</span>
							<div
								className={
									"justify-self-end flex justify-between items-center h-full w-[13em]"
								}
							>
								<div className={"grid grid-cols-2 gap-1 items-center"}>
									<span>Roles</span>
									<Dialog modal={false}>
										<DropdownMenu>
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
												className={
													"bg-sidebar-bg rounded-lg py-[1.2em] border-[#285842] border-1"
												}
												align={"start"}
											>
												<DropdownMenuLabel className={"text-lg flex items-center"}>
													<span className={'me-2'}>Viki's Roles</span>
													<DialogTrigger>
														<Button variant="ghost">+</Button>
													</DialogTrigger>
												</DropdownMenuLabel>
												<DropdownMenuSeparator className={'bg-[#285842]'}/>
												<DropdownMenuItem
													className={`flex w-[calc(1.2em+min-content)] justify-between text-md ${dropDownProps} hover:border-0 hover:outline-0`}
												>
													<span>Manager</span>
													<X className={"stroke-red-500 cursor-pointer"} />
												</DropdownMenuItem>
											</DropdownMenuContent>
										</DropdownMenu>
											<DialogContent className="sm:max-w-[425px] bg-sidebar-bg">
												<DialogHeader>
													<DialogTitle className={'text-2xl'}>Select roles</DialogTitle>
												</DialogHeader>
												<ul className={'flex flex-col overflow-scroll h-[max(10em,fit-content)]'}>
													<p className={'hover:bg-[#0a2a57] rounded-lg p-2'}>- Manager</p>
												</ul >
												<DialogFooter>
													<DialogClose asChild>
														<Button variant="outline" className={'bg-sidebar-bg'}>Cancel</Button>
													</DialogClose>
												</DialogFooter>
											</DialogContent>
									</Dialog>
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
