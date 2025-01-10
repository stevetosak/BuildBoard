package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.service.threads.impl.ProjectService;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping("/topic/add-tag/{id}")
    public String addTagToTopic(@PathVariable(name = "id") long id,
                         @RequestParam String tagName,
                         HttpSession session,
                         Model model) {
        Topic t = topicService.getById(id);
        topicService.addTagToTopic(t, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PostMapping("/topic/delete-tag/{topicId}/{tagName}")
    public String deleteTagTopic(@PathVariable long topicId, @PathVariable String tagName, Model model) {
        Topic t = topicService.deleteTagFromTopic(topicId, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PostMapping("/project/{title}/add-tag")
    public String addTagToProject(
            @PathVariable String title,
            @RequestParam(name = "tagName") String tagName
    )
    {
        projectService.addTagToProjectWithTitle(title,tagName);
        return "redirect:/project/" + title;
    }

    @PostMapping("/project/delete-tag/{projectTitle}/{tagName}")
    public String deleteTagProject(
            @PathVariable String projectTitle,
            @PathVariable String tagName
    ){
        projectService.removeTagFromProjectWithTitle(projectTitle,tagName);
        return "redirect:/project/" + projectTitle;
    }
}
