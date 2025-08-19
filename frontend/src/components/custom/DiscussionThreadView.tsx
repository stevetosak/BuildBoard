import {
	Card,
	CardContent,
	CardFooter,
	CardHeader,
} from "@/components/ui/card.tsx";
import { Check, CircleEllipsis, Reply, X } from "lucide-react";
import { Button } from "@/components/ui/button.tsx";
import type { ThreadResponse, ThreadElement } from "@/types.ts";
import { useContext, useState } from "react";
import { AnimatePresence, motion } from "framer-motion";
import * as React from "react";
import { MessageInputBox } from "@/components/custom/MessageInputBox.tsx";
import { api } from "@/services/apiconfig.ts";
import { type ThreadNode, ThreadTree } from "@/lib/utils.ts";
import { useJwt } from "react-jwt";
import { getToken } from "@shared/security-utils.ts";
import SecurityContext from "@context/security-context.ts";

export const DiscussionThreadView = ({
	className,
	node,
	isRoot = false,
	handleAddReply,
	tree,
	updateTree,
}: {
	className?: string;
	node: ThreadNode;
	isRoot?: boolean;
	tree: ThreadTree;
	handleAddReply: (targetNodeIdx: number, child: ThreadElement) => void;
	updateTree: (threadResponse: ThreadResponse) => void;
}) => {
	const [replying, setReplying] = useState(false);
	const { username } = useContext(SecurityContext);
	const [displayReplies, setDisplayReplies] = useState<boolean>(true);
	const replies = node.children;
	console.log("Replies");
	console.log(replies);

	// todo refactor security context

	const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
		const content = e.currentTarget.value;
		if (e.key === "Enter" && !e.shiftKey && content.trim() !== "") {
			const newThread: ThreadElement = {
				parentId: node.element.parentId,
				level: node.element.level + 1,
				content: content,
				user: { id: 55, username: "Tosman223", avatarUrl: "nema" },
				numLikes: 0,
				numReplies: 0,
				type: "discussion",
				createdAt: Date.now(),
			};

			e.preventDefault();
			handleAddReply(node.element.id!, newThread);
			setReplying(false);
		}
	};

	const handleDisplayReplies = async () => {
		if (!replies || replies.length === 0) {
			await loadReplies();
		}
		setDisplayReplies((prevState) => !prevState);
	};

	const loadReplies = async () => {
		const response = await api.get<ThreadResponse>(
			`/replies?threadId=${node.element.id}`,
		);
		console.log("LOADING REPLIES FOR CURRENT NODE >>>");
		console.log(node);
		console.log("RECIEVED RESPONSE");
		console.log(response.data);
		updateTree(response.data);
	};

	return (
		<div
			className={`${
				displayReplies ? "border-l-2 border-gray-800 pl-3" : ""
			} m-5 flex flex-col max-w-2xl gap-2`}
		>
			<AnimatePresence>
				<Card
					className={`border-0 border-l-4 border-accent-2 rounded-xl bg-background-card text-[#eaeaea] ${className}`}
				>
					<CardHeader className="flex justify-between items-start gap-2">
						<div className="flex gap-3 items-center">
							<img
								className="w-10 h-10 rounded-full border-2 border-gray-700 p-1"
								src="/vite.svg"
								alt="User Avatar"
							/>
							<span className="text-sm font-medium text-white">
								{node.element.user.username}
							</span>
						</div>
						<span className="text-sm text-muted-foreground mt-1">
							{new Date(node.element.createdAt).toDateString()}
						</span>
					</CardHeader>

					<CardContent className="text-left">
						{isRoot ? (
							<h2 className="text-lg font-semibold leading-snug">
								{node.element.content}
							</h2>
						) : (
							<p className="text-sm text-gray-300">{node.element.content}</p>
						)}
					</CardContent>

					<CardFooter className="flex justify-between items-center pt-2">
						<div className="flex gap-6">
							<Button className="flex items-center gap-1 bg-background-card hover:bg-gray-900">
								<Check size={16} />
								<span className={"text-sm text-muted-foreground"}>23</span>
							</Button>
							<Button className="flex items-center gap-1 bg-background-card hover:bg-gray-900 hover:border-s-muted hover:border-1">
								<X size={16} />
								<span className={"text-sm text-muted-foreground"}>23</span>
							</Button>
						</div>

						<Button
							size="icon"
							className="hover:-translate-y-0.5 hover:bg-gray-900 transition bg-background-card"
							onClick={() => setReplying((prev) => !prev)}
						>
							<Reply />
						</Button>
					</CardFooter>

					{replying && (
						<motion.div
							initial={{ x: "100%", opacity: 0 }}
							animate={{ x: 0, opacity: 1 }}
							exit={{ x: "100%", opacity: 0 }}
							transition={{ type: "spring", stiffness: 300, damping: 30 }}
							className="px-4 mt-2"
						>
							<MessageInputBox handleKeyDown={handleKeyDown} />
						</motion.div>
					)}
				</Card>
			</AnimatePresence>

			{node.element.numReplies > 0 && (
				<div className="flex items-start">
					<Button
						size="sm"
						className="hover:bg-gray-900 hover:-translate-y-0.5 hover:translate-x-0.5 transition bg-background-main"
						onClick={() => handleDisplayReplies()}
					>
						<CircleEllipsis
							className="mr-2"
							size={16}
						/>
						{node.element.numReplies - replies.length > 0
							? `View ${node.element.numReplies} more replies`
							: "Hide"}
					</Button>
				</div>
			)}

			{displayReplies && (
				<div className="w-full">
					{replies?.map((thr, idx) => (
						<div
							key={idx}
							className={`mt-5`}
							style={{ paddingLeft: `${thr.element.level}rem` }}
						>
							<DiscussionThreadView
								className="gap-1"
								node={thr}
								tree={tree}
								updateTree={updateTree}
								handleAddReply={handleAddReply}
							/>
						</div>
					))}
				</div>
			)}
		</div>
	);
};
