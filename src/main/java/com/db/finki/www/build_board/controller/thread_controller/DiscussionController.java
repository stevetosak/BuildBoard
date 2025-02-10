package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.thread.impl.DiscussionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class DiscussionController {
    private final DiscussionService discussionService;

    public DiscussionController(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    @PostMapping("/topics/{topic-id}/discussions/add")
    public String addReply(
            @PathVariable(name = "topic-id") String topicId,
            @RequestParam int parentId, @RequestParam String content, Model model,
            @SessionAttribute @P("user") BBUser user) {
        try {
            discussionService.create(content, parentId, user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/topics/" + topicId;
    }

    @PreAuthorize("@discussionService.getDiscussionById(#replyId).user.username==#username")
    @PostMapping("/topics/{topic-id}/discussions/{replyId}/edit")
    public String editReply(@PathVariable(name = "topic-id") String topicId, @PathVariable @P("replyId") int replyId, @RequestParam String content, Model model, HttpSession session
    , @P("username") String username) {
        discussionService.edit(replyId, content);
        return "redirect:/topics/" + topicId;
    }

    @PreAuthorize("@discussionService.getDiscussionById(#discussionId).getUser().getId()==#user.getId()")
    @PostMapping("/topics/{topic-id}/discussions/{discussionId}/delete")
    public String deleteDiscussion(@PathVariable(name = "topic-id") String topicId, @PathVariable @P("discussionId") int discussionId, @SessionAttribute @P("user") BBUser user, @RequestParam @Param("username") String username) {
        discussionService.delete(discussionId);
        return "redirect:/topics/" + topicId;
    }

}
