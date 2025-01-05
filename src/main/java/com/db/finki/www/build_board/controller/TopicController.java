package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.entity.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;
    private final TagService tagService;

    public TopicController(TopicService topicService, TagService tagService) {
        this.topicService = topicService;
        this.tagService = tagService;
    }

    @GetMapping("/create")
    public String getCreateTopicPage() {
        return "create-topic";
    }
    @GetMapping("/{topic-name}")
    public String showTopic(@PathVariable(name = "topic-name") String topicName, Model model) {
        Topic t = topicService.getByTitle(topicName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAll().stream().filter(tag -> !t.getTags().contains(tag)));
        return "show-topic";
    }
    @PostMapping("/add")
    public String createTopic(@RequestParam String title, @RequestParam String description, HttpSession session) {
        BBUser user = (BBUser) session.getAttribute("user");
        topicService.create(title, description, user);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String deleteTopic(@PathVariable(name = "id") long id, HttpSession session) {
        topicService.deleteTopicById(id);
        return "redirect:/";
    }
    @PostMapping("/add-tag/{id}")
    public String addTag(@PathVariable(name = "id") long id,
                         @RequestParam String tagName,
                         HttpSession session,
                         Model model) {
        Topic t = topicService.getById(id);
        topicService.addTagToTopic(t,tagName);
        model.addAttribute("topic", t);
        return "redirect:/topic/" + t.getTitle();
    }
}
