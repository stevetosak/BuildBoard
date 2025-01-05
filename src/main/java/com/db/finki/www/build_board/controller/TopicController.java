package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.entity.BBUser;
import com.db.finki.www.build_board.service.threads.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/create")
    public String getCreateTopicPage() {
        return "create-topic";
    }
    @GetMapping("/{topic-name}")
    public String showTopic(@PathVariable(name = "topic-name") String topicName) {
        return "show-topic";
    }
    @PostMapping("/add")
    public String createTopic(@RequestParam String title, @RequestParam String description, HttpSession session) {
        BBUser user = (BBUser) session.getAttribute("user");
        topicService.create(title, description, user);
        return "redirect:/";
    }
}
