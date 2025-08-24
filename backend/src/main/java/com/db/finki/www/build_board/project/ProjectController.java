package com.db.finki.www.build_board.project;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import com.db.finki.www.build_board.project.associated_entities.custom_role.CustomRole;
import com.db.finki.www.build_board.project.associated_entities.permissions.Permissions;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectDTO.ProjectDTOBuilder projectDTOBuilder = ProjectDTO.builder();
    private final ProjectDTO.Member.MemberBuilder memberBuilder =
            ProjectDTO.Member.builder();
    private final ProjectDTO.CustomRoles.CustomRolesBuilder customRolesBuilder =
            ProjectDTO.CustomRoles.builder();

    public ProjectController(ProjectService projectService) {this.projectService = projectService;}

    @GetMapping("{projectName}/threads")
    public Page<NamedThread> getThreadsForProject(
            @PathVariable String projectName,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) List<String> filters,
            @RequestParam(required = false, name = "threadType") String type,
            @RequestParam(required = false, defaultValue = "0") int page
                                                 ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("{projectTitle}")
    public ProjectDTO getProject(@PathVariable String projectTitle) throws UnsupportedEncodingException {
        projectTitle= URLDecoder.decode(projectTitle,"UTF-8");
        Project project = projectService.getByTitle(projectTitle);

        return this.projectDTOBuilder
                .name(project.getTitle())
                .repoURL(project.getRepoUrl())
                .description(project.getDescription())
                .logo(project.getLogoURL())
                .members(
                        project
                                .getDevelopers()
                                .stream()
                                .map(dev -> memberBuilder
                                        .username(dev.getUsername())
                                        .logo(dev.getAvatarUrl())
                                        .typeOfUser(dev.getAuthority())
                                        .roles(dev.getProjectRoles().stream().map(CustomRole::getName).collect(Collectors.toList()))
                                        .build()
                                    )
                                .toList()
                        )
                .roles(
                        project
                                .getCustomRoles()
                                .stream()
                                .map(role -> this.customRolesBuilder
                                        .name(role.getName())
                                        .permissions(role
                                                        .getPermissions()
                                                        .stream()
                                                        .map(Permissions::getName)
                                                        .toList()
                                                    )
                                        .description(role.getDescription())
                                        .build()
                                    )
                                .toList()
                      )
                .build();
    }
}
