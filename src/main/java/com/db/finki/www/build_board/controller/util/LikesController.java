package com.db.finki.www.build_board.controller.util;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.thread.discussion_thread.Discussion;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.util.ThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
public class LikesController {
    private final ThreadService threadService;

    public LikesController(ThreadService threadService) {
        this.threadService = threadService;
    }

    private BBThread rate(int threadId, BBUser user, boolean likes) {
        return threadService.rate(threadId, user.getId(), likes);
    }

    private String getUrlForThraed(BBThread thread, Integer id) {
        if (id != null)
            return "redirect:/topics/" + id;

        return "redirect:/projects/" + ((Project) thread).getTitle();
    }

    @PostMapping("/threads/{thread-id}/dislike")
    public String dislikeThread(@SessionAttribute BBUser user,
                                @PathVariable(name = "thread-id") int threadId,
                                @RequestParam(required = false, name = "topic-id") Integer topicId
    ) {
        return getUrlForThraed(rate(threadId, user, false), topicId);
    }

    @PostMapping("/threads/{thread-id}/like")
    public String likeThread(@SessionAttribute BBUser user,
                             @RequestParam(required = false, name = "topic-id") Integer topicId,
                             @PathVariable(name = "thread-id") int threadId) {
        return getUrlForThraed(rate(threadId, user, true), topicId);
    }

}
