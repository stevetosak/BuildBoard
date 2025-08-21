import LoginLogoutButtons from "@pages/shared/login-logout-buttons";
import RightSidebar from "@pages/shared/right-sidebar";
import useGetUserProfile from "@pages/shared/use-get-user-profile";
import UserInfo from "@pages/shared/UserInfo";
import type { Project, UserProfile } from "@shared/api-utils";
import { useQuery } from "@tanstack/react-query";
import { Link, Outlet, useParams } from "react-router-dom";
import fetchProject from "./data/fetchProject";
import UserShortRow from "@pages/shared/user-short-row";
import RightPopUp from "@pages/shared/RightPopup";
import LeftSidebar from "@pages/shared/left-sidebar";
import LogoLeftSidebar from "@pages/shared/LogoLeftSidebar";
import {
	SidebarMenu,
	SidebarMenuButton,
	SidebarMenuItem,
} from "@components/ui/sidebar";

type ProjectPathRouteParams = {
	projectName: string;
};

const links = ["topics", "channels", "cescription", "manamgnet"];

const ProjectPage = () => {
	const userProfile = useGetUserProfile();
	const { projectName } = useParams<ProjectPathRouteParams>();
	const { data: project } = useQuery({
		queryKey: [projectName],
		queryFn: ({ queryKey }) => fetchProject(queryKey[0] as string),
	});

	return (
		<main className="layout p-0">
			<LeftSidebar.Wrapper data={"placeholder"}>
				<LeftSidebar.HeaderInsideData>
					<LogoLeftSidebar />
				</LeftSidebar.HeaderInsideData>
				<LeftSidebar.BodyInsideData>
					<SidebarMenu>
						{links.map((linkName) => (
							<SidebarMenuItem key={linkName}>
								<SidebarMenuButton asChild>
									<Link to={`/projects/${linkName}/${projectName}`}>
										{linkName}
									</Link>
								</SidebarMenuButton>
							</SidebarMenuItem>
						))}
					</SidebarMenu>
				</LeftSidebar.BodyInsideData>
			</LeftSidebar.Wrapper>
			<Outlet />
			<RightSidebar.Wrapper data={userProfile}>
				<RightSidebar.Header componentIfDataNullable={<LoginLogoutButtons />}>
					{(user: UserProfile) => <UserInfo username={user.username} />}
				</RightSidebar.Header>
				<RightSidebar.ContextOverrider value={project}>
					<RightSidebar.Body>
						{(project: Project) => (
							<RightPopUp title="Members">
								{project.members.map((member) => (
									<UserShortRow
										key={member.username}
										username={member.username}
										logo={member.logo}
									/>
								))}
							</RightPopUp>
						)}
					</RightSidebar.Body>
				</RightSidebar.ContextOverrider>
			</RightSidebar.Wrapper>
		</main>
	);
};

export default ProjectPage;
