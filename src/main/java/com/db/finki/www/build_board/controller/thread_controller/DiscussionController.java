package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.thread.impl.DiscussionService;
import com.db.finki.www.build_board.service.thread.impl.TopicServiceImpl;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
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

    @PostMapping("/topics/{topic-name}/discussions/add")
    public String addReply(
            @PathVariable(name = "topic-name") String topicName,
            @RequestParam int parentId, @RequestParam String content, Model model,
            @SessionAttribute @P("user") BBUser user) {
        try {
            discussionService.create(content, parentId, user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/topics/" + topicName;
    }

    @PreAuthorize("@discussionService.discussionById(#replyId).user.username==#username")
    @PostMapping("/topics/{topic-name}/discussions/{replyId}/edit")
    public String editReply(@PathVariable(name = "topic-name") String topicName, @PathVariable @P("replyId") int replyId, @RequestParam String content, Model model, HttpSession session
    , @P("username") String username) {
        discussionService.edit(replyId, content);
        return "redirect:/topics/" + topicName;
    }

    @PreAuthorize("@discussionService.discussionById(#discussionId).getUser().getId()==#user.getId()")
    @PostMapping("/topics/{topic-name}/discussions/{discussionId}/delete")
    public String deleteDiscussion(@PathVariable(name = "topic-name") String topicName, @PathVariable @P("discussionId") int discussionId, @SessionAttribute @P("user") BBUser user, @RequestParam @Param("username") String username) {
        discussionService.delete(discussionId);
        return "redirect:/topics/" + topicName;
    }

}
