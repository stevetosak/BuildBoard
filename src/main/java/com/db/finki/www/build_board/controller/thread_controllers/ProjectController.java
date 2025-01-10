package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.threads.impl.ProjectService;
import com.db.finki.www.build_board.service.threads.impl.TagServiceImpl;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;
    private final TagService topicService;

    public ProjectController(ProjectService projectService, TagServiceImpl topicService) {
        this.projectService = projectService;
        this.topicService = topicService;
    }

    @GetMapping("/{title}")
    public String getProjectPage(@PathVariable String title, Model model) {
        Project project = projectService.findByTitle(title);

        model.addAttribute("project", project);
        model.addAttribute("tags", topicService.findAll());
        Hibernate.initialize(project.getTags());

        return "project_pages/show-project";
    }

    @GetMapping("/create")
    public String getCreateProjectPage(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("isCreatingProject", topicService.findAll());
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
            @PathVariable(name = "pr_title") String title,
            Model model
    ) {
        model.addAttribute("project", projectService.findByTitle(title));
        return "project_pages/project-create";
    }

    @PostMapping("/{title}/modify")
    public String modifyProject(
            @PathVariable String title,
            @RequestParam(name = "title") String newTitle,
            @RequestParam(name = "repo_url") String repoUrl,
            @RequestParam String description
    ){
        return "redirect:/project/" +  projectService.updateProject(title,repoUrl,description,newTitle).getTitle();
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

    @PostMapping("/topic/add")
    public String addTopic(
            @RequestParam(name = "project_title") String projectTitle,
            @RequestParam(name = "title") String topicsTitle,
            @RequestParam String description,
            @SessionAttribute BBUser user
    ){
        projectService.addToProjectWithIdNewTopic(projectTitle, topicsTitle,description,user);
        return "redirect:/project/" + projectTitle;
    }

    @PostMapping("/delete/*")
    public String delete(@RequestParam Long id) {
        projectService.deleteById(id);
        return "redirect:/" ;
    }
}
///projects/topics/add
