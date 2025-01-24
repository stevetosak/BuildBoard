package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.service.threads.impl.DiscussionService;
import com.db.finki.www.build_board.service.threads.impl.ThreadService;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;
    private final TagService tagService;
    private final DiscussionService discussionService;
    private final ThreadService threadService;

    public TopicController(TopicService topicService, TagService tagService, DiscussionService discussionService, ThreadService threadService) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.discussionService = discussionService;
        this.threadService = threadService;
    }

    @GetMapping("/create")
    public String getCreateTopicPage() {
        return "create-topic";
    }

    @GetMapping("/{topic-name}")
    public String showTopic(@PathVariable(name = "topic-name") String topicName, Model model) {
        Topic t = topicService.getByTitle(topicName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        model.addAttribute("replies", discussionService.findByTopic(t.getId()));
        return "show-topic";
    }

    @PostMapping("/add")
    public String createTopic(@RequestParam String title, @RequestParam String description, HttpSession session) {
        BBUser user = (BBUser) session.getAttribute("user");
        topicService.create(title, description, user);
        return "redirect:/";
    }

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/delete/{id}")
    public String deleteTopic(@PathVariable(name = "id") @P("id") long id, HttpSession session, @RequestParam @P("username") String username) {
        topicService.deleteTopicById(id);
        return "redirect:/";
    }

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/edit/{id}")
    public String editTopic(@PathVariable @P("id") long id, @RequestParam String title, @RequestParam String content, Model model, @RequestParam @P("username")String username) {
        Topic t = topicService.save(id, title, content);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }


}
