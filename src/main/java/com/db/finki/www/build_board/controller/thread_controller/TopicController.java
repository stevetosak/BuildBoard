package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.service.thread.impl.DiscussionService;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//TODO: trgni go toj trigerot da developer sho brishit

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final TagService tagService;
    private final DiscussionService discussionService;

    public TopicController(TopicService topicService, TagService tagService, DiscussionService discussionService) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.discussionService = discussionService;
    }

    @GetMapping("/create")
    public String getCreateTopicPage() {
        return "create-topic";
    }

    @GetMapping("/{topic-name}")
    public String showTopic(@PathVariable(name = "topic-name") String topicName, Model model) {
        Topic t = topicService.getByTitle(topicName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.getAllNotUsed(t));
        model.addAttribute("replies", discussionService.getByTopic(t.getId()));
        return "show-topic";
    }

    @PostMapping("/add")
    public String createTopic(@RequestParam String title, @RequestParam String description, HttpSession session) {
        BBUser user = (BBUser) session.getAttribute("user");
        topicService.create(title, description, user);
        return "redirect:/";
    }

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/{id}/delete")
    public String deleteTopic(@PathVariable(name = "id") @P("id") long id, HttpSession session, @RequestParam @P("username") String username) {
        topicService.deleteTopicById(id);
        return "redirect:/";
    }

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/{id}/edit")
    public String editTopic(@PathVariable @P("id") long id, @RequestParam String title, @RequestParam String content, Model model, @RequestParam @P("username")String username) {
        Topic t = topicService.create(id, title, content);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.getAllNotUsed(t));
        return "redirect:/topics/" + t.getTitle();
    }


}
