package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.thread.impl.TagServiceImpl;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import org.hibernate.Hibernate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TagService tagService;
    private final String DUPLICATED_TITLE_MSG="could not execute statement [ERROR: duplicate key value violates unique constraint";

    public ProjectController(ProjectService projectService, TagServiceImpl topicService) {
        this.projectService = projectService;
        this.tagService = topicService;
    }

    @GetMapping("/{title}")
    public String getProjectPage(@PathVariable(name = "title") Project project, Model model, RedirectAttributes redirectAttributes,
    @RequestParam(required = false) String duplicateTitle) {
        model.addAttribute("project", project);
        model.addAttribute("tags", tagService.getAll());
        model.addAttribute("developers",projectService.getAllDevelopersForProject(project));
        String error = (String) redirectAttributes.getAttribute("error");
        
        if(error != null){
            model.addAttribute("error", error);
        }
        if(duplicateTitle!=null){
            model.addAttribute("errMsg","There already exists a project with the provided title" );
        }
        
        Hibernate.initialize(project.getTags());

        return "project_pages/show-project";
    }

    @GetMapping("/create")
    public String getCreateProjectPage(Model model, @RequestParam(required = false) String duplicateTitle) {
        if(duplicateTitle!=null){
            model.addAttribute("errMsg", "There already exists a project with the provided title"); 
        }
        model.addAttribute("project", new Project());
        model.addAttribute("isCreatingProject", tagService.getAll());
        return "project_pages/project-create";
    }


    @GetMapping("{title}/topics/add")
    public String getAddTopicPage(
            @PathVariable String title,
            Model model
    ){
        model.addAttribute("project_title",title);
        return "create-topic" ;
    }

    @GetMapping("/{pr-title}/edit")
    public String getModifyPage(
            @PathVariable(name = "pr-title") Project project,
            Model model
    ) {
        model.addAttribute("project", project);
        return "project_pages/project-create";
    }

    @GetMapping("/{pr-title}/members")
    public String getProjectMembersPage(
            Model model,
            @PathVariable(name = "pr-title") Project project
    )
    {
        model.addAttribute("project", project);
        model.addAttribute("developers", projectService.getAllDevelopersForProject(project));
        return "project_pages/members";
    }

    @PreAuthorize("#project.getUser().equals(#user)")
    @PostMapping("/{pr-title}/members/{mem-id}/kick")
    public String kickMember(@PathVariable(name = "pr-title") @P("project") Project project,@PathVariable(name = "mem-id") int memberId,@SessionAttribute @P("user") BBUser user){
        projectService.kickMember(project, memberId);
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
    ){
        String oldTitle = project.getTitle();
        try{
            return "redirect:/projects/" +  projectService.update(project, repoUrl, description, newTitle).getTitle();
        }catch(org.springframework.dao.DataIntegrityViolationException e){
            if(e.getMessage().contains(DUPLICATED_TITLE_MSG)){
                attributes.addAttribute("duplicateTitle", "y");
                return "redirect:/projects/" + oldTitle; 
            }
            throw e ; 
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
        try{
            projectService.create(title,repoUrl,description,user);
            return "redirect:/";
        }catch(org.springframework.dao.DataIntegrityViolationException e){
            if(e.getMessage().contains(DUPLICATED_TITLE_MSG)){
                redirectAttributes.addAttribute("duplicateTitle", "y");
                return "redirect:/projects/create"; 
            }
            throw e ; 
        }
    }

    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/topics/add")
    public String addTopic(
            @RequestParam(name = "project_title") @P("project") Project project,
            @RequestParam(name = "title") String topicsTitle,
            @RequestParam String description,
            @RequestParam @P("username") String username,
            @SessionAttribute("user") BBUser user 
    ){
        projectService.createTopic(project, topicsTitle, description, user);
        return "redirect:/projects/" + project.getTitle();
    }

    @PreAuthorize("#project.getUser().username.equals(#username)")
    @PostMapping("/{title}/delete")
    public String delete(
            @PathVariable(name = "title") @P("project") Project project,
            @RequestParam @P("username") String username
    ) {
        projectService.delete(project);
        return "redirect:/" ;
    }

}
