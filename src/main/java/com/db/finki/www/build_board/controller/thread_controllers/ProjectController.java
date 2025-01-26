package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.thread.impl.TagServiceImpl;
import com.db.finki.www.build_board.service.thread.itfs.TagService;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final TagService tagService;

    public ProjectController(ProjectService projectService, TagServiceImpl topicService) {
        this.projectService = projectService;
        this.tagService = topicService;
    }

    @GetMapping("/{title}")
    public String getProjectPage(@PathVariable(name = "title") Project project, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("project", project);
        model.addAttribute("tags", tagService.findAll());
        String error = (String) redirectAttributes.getAttribute("error");
        if(error != null){
            model.addAttribute("error", error);
        }
        
        Hibernate.initialize(project.getTags());

        return "project_pages/show-project";
    }

    @GetMapping("/create")
    public String getCreateProjectPage(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("isCreatingProject", tagService.findAll());
        return "project_pages/project-create";
    }


    @GetMapping("{title}/topic/add")
    public String getAddTopicPage(
            @PathVariable String title,
            Model model
    ){
        model.addAttribute("project_title",title);
        return "create-topic" ;
    }

    @GetMapping("/{pr_title}/modify")
    public String getModifyPage(
            @PathVariable(name = "pr_title") Project project,
            Model model
    ) {
        model.addAttribute("project", project);
        return "project_pages/project-create";
    }

    @GetMapping("/{pr_title}/members")
    public String getProjectMembersPage(
            Model model,
            @PathVariable(name = "pr_title") Project project
    )
    {
        model.addAttribute("project", project);
        return "project_pages/members";
    }

    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/{title}/modify")
    public String modifyProject(
            @PathVariable(name = "title") @P("project") Project project,
            @RequestParam(name = "title") String newTitle,
            @RequestParam(name = "repo_url") String repoUrl,
            @RequestParam @P("username") String username,
            @RequestParam String description
    ){
        return "redirect:/project/" +  projectService.updateProject(project,repoUrl,description,newTitle).getTitle();
    }

    @PostMapping("/add")
    public String createProject(
            @RequestParam String title,
            @RequestParam(required = false, name = "repo_url") String repoUrl,
            @RequestParam(required = false) String description,
            @SessionAttribute BBUser user
    ) {
        projectService.create(title,repoUrl,description,user);
        return "redirect:/";
    }

    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/topic/add")
    public String addTopic(
            @RequestParam(name = "project_title") @P("project") Project project,
            @RequestParam(name = "title") String topicsTitle,
            @RequestParam String description,
            @RequestParam @P("username") String username
    ){
        projectService.addToProjecNewTopic(project,topicsTitle,description,username);
        return "redirect:/project/" + project.getTitle();
    }

    @PreAuthorize("#project.getUser().username.equals(#username)")
    @PostMapping("/delete/*")
    public String delete(
            @RequestParam(name = "id") @P("project") Project project,
            @RequestParam @P("username") String username
    ) {
        projectService.delete(project);
        return "redirect:/" ;
    }

}
///projects/topics/add
