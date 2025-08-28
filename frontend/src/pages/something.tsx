import { Button } from "@/components/ui/button"
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "@/components/ui/dialog"
import {
	DropdownMenu,
	DropdownMenuContent,
	DropdownMenuItem,
	DropdownMenuTrigger,
} from "@radix-ui/react-dropdown-menu";

export default function Something() {
	return (
		<Dialog>
			<DropdownMenu>
				<DropdownMenuTrigger>Right click</DropdownMenuTrigger>
				<DropdownMenuContent>
					<DropdownMenuItem>Open</DropdownMenuItem>
					<DropdownMenuItem>Download</DropdownMenuItem>
					<DialogTrigger asChild>
							<span>Delete</span>
					</DialogTrigger>
				</DropdownMenuContent>
			</DropdownMenu>
			<DialogContent>
				<DialogHeader>
					<DialogTitle>Are you absolutely sure?</DialogTitle>
					<DialogDescription>
						This action cannot be undone. Are you sure you want to permanently
						delete this file from our servers?
					</DialogDescription>
				</DialogHeader>
				<DialogFooter>
					<Button type="submit">Confirm</Button>
				</DialogFooter>
			</DialogContent>
		</Dialog>
	)
}
