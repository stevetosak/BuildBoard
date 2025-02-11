package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;


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
    @PostMapping("/topics/{id}/tags/add")
    public String addTagToTopic(
            @PathVariable(name = "id") @P("id") long id,
            @RequestParam String tagName,
            @P("username") String username,
            Model model,
            @SessionAttribute("user") BBUser user 
    ) {
        Topic t = topicService.getById(id);
        topicService.addTagToTopic(t, tagName,user);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.getAllNotUsed(t));
        return "redirect:/topics/" + t.getId();
    }

    @PreAuthorize("@topicServiceImpl.getById(#topicId).getUser().getUsername()==#username")
    @PostMapping("/topics/{topicId}/tags/{tag-name}/delete")
    public String deleteTagTopic(@PathVariable @P("topicId") long topicId, @PathVariable(name = "tag-name") String tagName, Model model, @RequestParam @P("username") String username) {
        Topic t = topicService.deleteTagFromTopic(topicId, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.getAllNotUsed(t));
        return "redirect:/topics/" + t.getId();
    }

    @PreAuthorize("#project.getUser().getUsername()==#username")
    @PostMapping("/projects/{title}/tags/add")
    public String addTagToProject(
            @PathVariable(name = "title") @P("project") Project project,
            @RequestParam(name = "tagName") String tagName,
            @RequestParam @P("username") String username,
            @SessionAttribute("user") BBUser user
    ) {
        projectService.addTag(project, tagName,user);
        return "redirect:/projects/" + project.getTitle();
    }

    @PreAuthorize("#project.getUser().getUsername().equals(#username)")
    @PostMapping("/projects/{projectTitle}/tags/{tagName}/delete")
    public String deleteTagProject(
            @PathVariable(name = "projectTitle") @P("project") Project project,
            @PathVariable String tagName,
            @RequestParam @P("username") String username
    ) {
        projectService.deleteTag(project, tagName);
        return "redirect:/projects/" + project.getTitle();
    }
}
