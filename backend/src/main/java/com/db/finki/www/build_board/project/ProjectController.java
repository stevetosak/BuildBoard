package com.db.finki.www.build_board.project;

import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.namedThread.NamedThread;
import com.db.finki.www.build_board.namedThread.NamedThreadDTO;
import com.db.finki.www.build_board.namedThread.SearchService;
import com.db.finki.www.build_board.project.associated_entities.custom_role.CustomRole;
import com.db.finki.www.build_board.project.associated_entities.permissions.Permissions;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.utils.named_threads.NamedThreadsDTOMapper;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
    private final SearchService searchService;
    private final NamedThreadsDTOMapper namedThreadsDTOMapper;
    private final ChannelService channelService;

    public ProjectController(ProjectService projectService, SearchService searchService, NamedThreadsDTOMapper namedThreadsDTOMapper, ChannelService channelService) {
        this.projectService = projectService;
        this.searchService = searchService;
        this.namedThreadsDTOMapper = namedThreadsDTOMapper;
        this.channelService = channelService;
    }

    @GetMapping("{projectName}/threads")
    public ResponseEntity<Page<NamedThreadDTO>> getThreadsForProject(
            @RequestParam int projectId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false, name = "threadType") String type,
            @RequestParam(required = false) List<String> tag,
            @RequestParam(required = false, defaultValue = "0") int page
                                                                    ) {
        if (title != null)
            title = URLDecoder.decode(title,
                    StandardCharsets.UTF_8);
        if (content != null)
            content = URLDecoder.decode(content,
                    StandardCharsets.UTF_8);

        Page<NamedThread> namedThreadPage = searchService.search(title,
                content,
                type,
                tag,
                PageRequest.of(page,
                        10),
                projectId
                                                                );
        return ResponseEntity.ok(namedThreadsDTOMapper.map(namedThreadPage));
    }

    @GetMapping("{projectTitle}")
    public ProjectDTO getProject(@PathVariable String projectTitle) throws BadRequestException {
        projectTitle = URLDecoder.decode(projectTitle,
                StandardCharsets.UTF_8);
        Project project = projectService.getByTitle(projectTitle);

        if (project == null)
            throw new BadRequestException(String.format("Project with title:%s not found",
                    projectTitle));

        return this.projectDTOBuilder
                .name(project.getTitle())
                .id(project.getId())
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
                                        .roles(dev
                                                .getProjectRoles()
                                                .stream()
                                                .map(CustomRole::getName)
                                                .collect(Collectors.toList()))
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

    @GetMapping("{id}/channels/{channelName}")
    public void getChannel(@PathVariable Long id, @PathVariable String channelName) {
        Project project = projectService.getById(id);
        Channel channel = channelService.getByNameAndProject(channelName,
                project.getTitle());
    }
}
