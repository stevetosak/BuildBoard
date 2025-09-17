package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.dto.AddRoleDTO;
import com.db.finki.www.build_board.dto.DeveloperWithRolesForProjectDTO;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.access_managment.AddRoleDTOEntitiesMapper;
import com.db.finki.www.build_board.service.access_managment.ProjectAccessManagementService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.thread.impl.TagServiceImpl;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TagService tagService;
    private final String DUPLICATED_TITLE_MSG = "could not execute statement [ERROR: duplicate key value violates unique constraint";
    private final ProjectAccessManagementService projectAccessManagementService;
    private final AddRoleDTOEntitiesMapper mapper;
    private final BBUserDetailsService userDetailsService;

    public ProjectController(ProjectService projectService, TagServiceImpl topicService, ProjectAccessManagementService projectAccessManagementService, AddRoleDTOEntitiesMapper mapper, BBUserDetailsService userDetailsService) {
        this.projectService = projectService;
        this.tagService = topicService;
        this.projectAccessManagementService = projectAccessManagementService;
        this.mapper = mapper;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/{title}")
    public String getProjectPage(
            @PathVariable(name = "title") Project project, Model model, RedirectAttributes redirectAttributes,
            @RequestParam(required = false) String duplicateTitle
                                ) {
        model.addAttribute("project",
                project);
        model.addAttribute("tags",
                tagService.getAll());
        model.addAttribute("developers",
                projectService.getAllDevelopersForProject(project));
        String error = (String) redirectAttributes.getAttribute("error");

        if (error != null) {
            model.addAttribute("error",
                    error);
        }
        if (duplicateTitle != null) {
            model.addAttribute("errMsg",
                    "There already exists a project with the provided title");
        }

        Hibernate.initialize(project.getTags());

        return "project_pages/show-project";
    }

    @GetMapping("/create")
    public String getCreateProjectPage(Model model, @RequestParam(required = false) String duplicateTitle) {
        if (duplicateTitle != null) {
            model.addAttribute("errMsg",
                    "There already exists a project with the provided title");
        }
        model.addAttribute("project",
                new Project());
        model.addAttribute("isCreatingProject",
                tagService.getAll());
        return "project_pages/project-create";
    }


    @PostMapping("/{title}/roles/add")
    public String addProjectRole(@PathVariable(name = "title") String title, @RequestBody AddRoleDTO addRoleDTO) {
        projectAccessManagementService.addRole(mapper.map(addRoleDTO));
        return "redirect:/projects/" + title + "/roles";
    }

    @GetMapping("{title}/roles")
    public String getRolesPage(@PathVariable(name = "title") Project project, Model model) {
        model.addAttribute("project",
                project);
        model.addAttribute("developersRoles",
                projectAccessManagementService.getRolesForMembersInProject(project));
        model.addAttribute("perResourcePermissions",
                List.of("READ",
                        "WRITE"));
        model.addAttribute("globalPermissions",
                List.of("CREATE",
                        "DELETE"));
        model.addAttribute("errMsg",
                null);
        model.addAttribute("overrideTypeDefault",
                "INCLUDE");
        return "project_pages/project-roles";
    }

    //projects/Project 1 Thread/roles/Admin/delete
    @PostMapping("{project_title}/roles/{role_name}/delete")
    public String deleteRole(
            @PathVariable(name = "project_title") Project project,
            @PathVariable(name = "role_name") String roleName
                            ){
        projectAccessManagementService.deleteByRoleNameAndProjectTitle(project,roleName);
        return String.format("redirect:/projects/%s/roles", project.getTitle());
    }


    @GetMapping("{title}/topics/add")
    public String getAddTopicPage(
            @PathVariable String title,
            Model model
                                 ) {
        model.addAttribute("project_title",
                title);
        return "create-topic";
    }

    @GetMapping("/{pr-title}/edit")
    public String getModifyPage(
            @PathVariable(name = "pr-title") Project project,
            Model model
                               ) {
        model.addAttribute("project",
                project);
        return "project_pages/project-create";
    }

    @GetMapping("/{pr-title}/members")
    public String getProjectMembersPage(
            Model model,
            @PathVariable(name = "pr-title") Project project
                                       ) {
        model.addAttribute("project",
                project);

        List<BBUser> developers = projectService.getAllDevelopersForProject(project);

        List<DeveloperWithRolesForProjectDTO> devWrapper = developers
                .stream()
                .map(dev -> new DeveloperWithRolesForProjectDTO(dev,projectAccessManagementService.getRolesForDeveloperInProject(dev,project))).toList();

        System.out.println("MAPPED" + " " + devWrapper);

        model.addAttribute("developers", devWrapper);
        model.addAttribute("projectRoles",projectAccessManagementService.getRolesForMembersInProject(project));


        return "project_pages/members";
    }

    @PreAuthorize("#project.getUser().equals(#user)")
    @PostMapping("/{pr-title}/members/{mem-id}/kick")
    public String kickMember(@PathVariable(name = "pr-title") @P("project") Project project, @PathVariable(name = "mem-id") int memberId, @SessionAttribute @P("user") BBUser user) {
        projectService.kickMember(project,
                memberId);
        return "redirect:/projects/" + project.getTitle() + "/members";
    }

    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/{title}/edit")
    public String modifyProject(
            @PathVariable(name = "title") @P("project") Project project,
            @RequestParam(name = "title") String newTitle,
            @RequestParam(name = "repo_url") String repoUrl,
            @RequestParam @P("username") String username,
            @RequestParam String description,
            RedirectAttributes attributes
                               ) {
        String oldTitle = project.getTitle();
        try {
            return "redirect:/projects/" + projectService
                    .update(project,
                            repoUrl,
                            description,
                            newTitle)
                    .getTitle();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            if (e
                    .getMessage()
                    .contains(DUPLICATED_TITLE_MSG)) {
                attributes.addAttribute("duplicateTitle",
                        "y");
                return "redirect:/projects/" + oldTitle;
            }
            throw e;
        }
    }

    @PostMapping("/add")
    public String createProject(
            @RequestParam String title,
            @RequestParam(required = false, name = "repo_url") String repoUrl,
            @RequestParam(required = false) String description,
            @SessionAttribute BBUser user,
            RedirectAttributes redirectAttributes
                               ) {
        try {
            projectService.create(title,
                    repoUrl,
                    description,
                    user);
            return "redirect:/";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            if (e
                    .getMessage()
                    .contains(DUPLICATED_TITLE_MSG)) {
                redirectAttributes.addAttribute("duplicateTitle",
                        "y");
                return "redirect:/projects/create";
            }
            throw e;
        }
    }

    @PostMapping("{title}/add-role/{userId}")
    public String assignRolesToUser(@PathVariable(name = "title") @P("project") Project project, @PathVariable Integer userId,@RequestParam(name = "roles") List<String> roleNames){
        BBUser user = userDetailsService.loadUserById(userId);
        projectAccessManagementService.addRolesToUser(user,project,roleNames);
        return "redirect:/projects/" + project.getTitle() + "/members";
    }
    @PostMapping("{title}/remove-role/{userId}")
    public String deleteRoleForUser(@PathVariable(name = "title") @P("project") Project project, @PathVariable Integer userId,String roleName){
        BBUser user = userDetailsService.loadUserById(userId);
        projectAccessManagementService.deleteRoleForUser(user,project,roleName);
        return "redirect:/projects/" + project.getTitle() + "/members";
    }



    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/topics/add")
    public String addTopic(
            @RequestParam(name = "project_title") @P("project") Project project,
            @RequestParam(name = "title") String topicsTitle,
            @RequestParam String description,
            @RequestParam @P("username") String username,
            @SessionAttribute("user") BBUser user
                          ) {
        projectService.createTopic(project,
                topicsTitle,
                description,
                user);
        return "redirect:/projects/" + project.getTitle();
    }

    @PreAuthorize("#project.getUser().username.equals(#username)")
    @PostMapping("/{title}/delete")
    public String delete(
            @PathVariable(name = "title") @P("project") Project project,
            @RequestParam @P("username") String username
                        ) {
        projectService.delete(project);
        return "redirect:/";
    }


}
