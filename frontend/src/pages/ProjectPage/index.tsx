import useGetUserProfile from "@pages/shared/use-get-user-profile";
import { useQuery } from "@tanstack/react-query";
import { Link, Outlet, useParams } from "react-router-dom";
import fetchProject from "./data/fetchProject";
import { SidebarMenuButton, SidebarMenuItem } from "@components/ui/sidebar";
import { uppercaseFirstLetter } from "@shared/string-utils";
import LeftSidebar from "@pages/shared/left-sidebar/sidebar-without-data";
import RightSidebar from "@pages/shared/right-sidebar/sidebar-with-data";
import type { ApiError, Project } from "@shared/api-utils";
import UserShortRow from "@pages/shared/user-short-row";
import DisplayApiError from "@pages/ProjectPage/ui/display-api-error.tsx";
import ConditionalDisplay from "@pages/HomePage/ui/ConditionalDsipaly.tsx";

type ProjectPathRouteParams = {
	projectName: string;
};

const links = ["topics", "channels", "description", "manamgnet"];

const ProjectPage = () => {
	const userProfile = useGetUserProfile();
	const { projectName } = useParams<ProjectPathRouteParams>();
	const query = useQuery<Project, ApiError>({
		queryKey: [projectName],
		queryFn: ({ queryKey }) => fetchProject(queryKey[0] as string),
	});

	return (
		<main
			className="layout p-0 min-h-[min-content]"
			style={{ padding: 0 }}
		>
			<LeftSidebar>
				{links.map((linkName) => (
					<SidebarMenuItem key={linkName}>
						<SidebarMenuButton asChild>
							<Link
								className="text-[2rem]"
								to={`/projects/${projectName}/${linkName}`}
							>
								{uppercaseFirstLetter(linkName)}
							</Link>
						</SidebarMenuButton>
					</SidebarMenuItem>
				))}
			</LeftSidebar>
			<ConditionalDisplay
				query={query}
				ErrorComponent={DisplayApiError}
			>
				{(project:Project) => <Outlet context={project} />}
			</ConditionalDisplay>
			<RightSidebar
				userProfile={userProfile}
				data={query.data}
				title="Members"
			>
				{(project: Project) =>
					project.members.map((member) => (
						<div className={"w-full"}>
							<UserShortRow
								key={member.username}
								username={member.username}
								logo={member.logo}
							/>
							<div className="w-full overflow-x-scroll flex flex-row gap-2 mt-2">
								{member.roles.map((role) => (
									<p className="p-[0.5em] rounded-xl bg-accent text-[0.7rem]">
										{role}
									</p>
								))}
							</div>
						</div>
					))
				}
			</RightSidebar>
		</main>
	);
};

export default ProjectPage;
