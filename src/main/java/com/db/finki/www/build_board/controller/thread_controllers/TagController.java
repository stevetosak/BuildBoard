package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.thread.itfs.TagService;
import com.db.finki.www.build_board.service.thread.itfs.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/topic/add-tag/{id}")
    public String addTagToTopic(@PathVariable(name = "id") @P("id") long id,
                         @RequestParam String tagName,
                         HttpSession session,
                         @P("username") String username,
                         Model model) {
        Topic t = topicService.getById(id);
        topicService.addTagToTopic(t, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PreAuthorize("@topicServiceImpl.getById(#topicId).getUser().getUsername()==#username")
    @PostMapping("/topic/delete-tag/{topicId}/{tagName}")
    public String deleteTagTopic(@PathVariable @P("topicId") long topicId, @PathVariable String tagName, Model model, @RequestParam @P("username") String username) {
        Topic t = topicService.deleteTagFromTopic(topicId, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PreAuthorize("#project.getUser().getUsername()==#username")
    @PostMapping("/project/{title}/add-tag")
    public String addTagToProject(
            @PathVariable(name="title") @P("project") Project project,
            @RequestParam(name = "tagName") String tagName,
            @RequestParam @P("username") String username
    )
    {
        projectService.addTagToProject(project,tagName);
        return "redirect:/project/" + project.getTitle();
    }

    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/project/delete-tag/{projectTitle}/{tagName}")
    public String deleteTagProject(
            @PathVariable(name = "projectTitle") @P("project") Project project,
            @PathVariable String tagName,
            @RequestParam @P("username") String username
    ){
        projectService.removeTagFromProject(project,tagName);
        return "redirect:/project/" + project.getTitle();
    }
}
