import useGetUserProfile from "@pages/shared/use-get-user-profile";
import { useQuery } from "@tanstack/react-query";
import { Link, Outlet, useParams } from "react-router-dom";
import fetchProject from "./data/fetchProject";
import { SidebarMenuButton, SidebarMenuItem } from "@components/ui/sidebar";
import { uppercaseFirstLetter } from "@shared/string-utils";
import LeftSidebar from "@pages/shared/left-sidebar/sidebar-without-data";
import RightSidebar from "@pages/shared/right-sidebar/sidebar-with-data";
import type { Project } from "@shared/api-utils";
import UserShortRow from "@pages/shared/user-short-row";

type ProjectPathRouteParams = {
	projectName: string;
};

const links = ["topics", "channels", "description", "manamgnet"];

const ProjectPage = () => {
	const userProfile = useGetUserProfile();
	const { projectName } = useParams<ProjectPathRouteParams>();
	const { data: project } = useQuery({
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
								to={`/projects/${linkName}/${projectName}`}
							>
								{uppercaseFirstLetter(linkName)}
							</Link>
						</SidebarMenuButton>
					</SidebarMenuItem>
				))}
			</LeftSidebar>
			<Outlet context={project} />
			<RightSidebar
				userProfile={userProfile}
				data={project}
				title="Members"
			>
				{(project: Project) =>
					project.members.map((member) => (
						<div>
							<UserShortRow
								key={member.username}
								username={member.username}
								logo={member.logo}
							/>
							<div className="w-full overflow-x-scroll">
								{member.roles.map((role) => (
									<p className="p-1 rounded-xl bg-accent text-[0.7rem]">
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
