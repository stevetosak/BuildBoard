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

    @PostMapping("/{topic-name}/discussion/dislike")
    public String dislike(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {

        BBUser user = (BBUser) session.getAttribute("user");
        threadService.rate(threadId,user.getId(),false);

        return "redirect:/topic/" + topicName;
    }

    @PostMapping("/{topic-name}/discussion/like")
    public String like(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {

        BBUser user = (BBUser) session.getAttribute("user");
        threadService.rate(threadId,user.getId(),true);

        return "redirect:/topic/" + topicName;
    }
}
