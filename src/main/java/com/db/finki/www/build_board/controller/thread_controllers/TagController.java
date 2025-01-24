package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.service.threads.impl.ProjectService;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TagController {
    private final TopicService topicService;
    private final TagService tagService;
    private final ProjectService projectService;

    public TagController(TopicService topicService, TagService tagService, ProjectService projectService) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.projectService = projectService;
    }

    @PreAuthorize("@topicServiceImpl.getById(id).user.username==username")
    @PostMapping("/topic/add-tag/{id}")
    public String addTagToTopic(@PathVariable(name = "id") long id,
                         @RequestParam String tagName,
                         HttpSession session,
                         String username,
                         Model model) {
        Topic t = topicService.getById(id);
        topicService.addTagToTopic(t, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PreAuthorize("@topicServiceImpl.getById(topicId).user.username==username")
    @PostMapping("/topic/delete-tag/{topicId}/{tagName}")
    public String deleteTagTopic(@PathVariable long topicId, @PathVariable String tagName, Model model, @RequestParam String username) {
        Topic t = topicService.deleteTagFromTopic(topicId, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PreAuthorize("project.getUser().username==username")
    @PostMapping("/project/{title}/add-tag")
    public String addTagToProject(
            @PathVariable(name="title") Project project,
            @RequestParam(name = "tagName") String tagName,
            @RequestParam String username
    )
    {
        projectService.addTagToProject(project,tagName);
        return "redirect:/project/" + project.getTitle();
    }

    @PreAuthorize("project.getUser().getUsername().equals(username)")
    @PostMapping("/project/delete-tag/{projectTitle}/{tagName}")
    public String deleteTagProject(
            @PathVariable(name = "projectTitle") Project project,
            @PathVariable String tagName,
            @RequestParam String username
    ){
        projectService.removeTagFromProject(project,tagName);
        return "redirect:/project/" + project.getTitle();
    }
}
