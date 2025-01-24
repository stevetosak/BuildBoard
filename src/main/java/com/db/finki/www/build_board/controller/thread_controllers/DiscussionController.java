package com.db.finki.www.build_board.controller.thread_controllers;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.threads.impl.DiscussionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiscussionController {
    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping("/topic/{topic-name}/reply/add")
    public String addReply(@PathVariable(name = "topic-name") String topicName, @RequestParam int parentId, @RequestParam String content, Model model, HttpSession session) {

        BBUser user = (BBUser) session.getAttribute("user");
        try {
            discussionService.create(content, parentId, user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/topic/" + topicName;
    }

    @PreAuthorize("@discussionService.findDiscussionById(replyId).user.username==username")
    @PostMapping("/topic/{topic-name}/reply/edit")
    public String editReply(@PathVariable(name = "topic-name") String topicName, @RequestParam int replyId, @RequestParam String content, Model model, HttpSession session
    ,String username) {
        discussionService.edit(replyId, content);
        return "redirect:/topic/" + topicName;
    }

    @PreAuthorize("@discussionService.findDiscussionById(threadId).user.username==username")
    @PostMapping("/topic/{topic-name}/discussion/delete")
    public String deleteDiscussion(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session, @RequestParam String username) {
        BBUser user = (BBUser) session.getAttribute("user");
        discussionService.delete(threadId);
        return "redirect:/topic/" + topicName;
    }

}
