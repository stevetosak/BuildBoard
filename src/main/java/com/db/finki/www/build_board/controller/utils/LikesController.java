package com.db.finki.www.build_board.controller.utils;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.threads.impl.ThreadService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LikesController {
    private final ThreadService threadService;

    public LikesController(ThreadService threadService) {
        this.threadService = threadService;
    }

    private void rate(int threadId, HttpSession session, boolean likes) {
        BBUser user = (BBUser) session.getAttribute("user");
        threadService.rate(threadId,user.getId(),likes );
    }

    @PostMapping("/topic/{topic-name}/discussion/dislike")
    public String dislikeTopic(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {
        rate(threadId,session,false);
        return "redirect:/topic/" + topicName;
    }

    @PostMapping("/topic/{topic-name}/discussion/like")
    public String likeTopic(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {
        rate(threadId, session, true);
        return "redirect:/topic/" + topicName;
    }

    @PostMapping("/project/{thread-name}/discussion/like")
    public String likeProject(@PathVariable(name = "thread-name") String projectName, @RequestParam int threadId, HttpSession session) {
        rate(threadId,session,true);
        return "redirect:/project/" + projectName;
    }

    @PostMapping("/project/{thread-name}/discussion/dislike")
    public String dislikeProject(@PathVariable(name = "thread-name") String threadName, @RequestParam int threadId, HttpSession session) {
        rate(threadId,session,false);
        return "redirect:/project/" + threadName;
    }
}
