package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.rest.dto.ThreadTreeResponse;
import com.db.finki.www.build_board.service.thread.impl.ThreadService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TopicRestController {

    private final TopicService topicService;
    private final ThreadService threadService;

    public TopicRestController(TopicService topicService, ThreadService threadService) {
        this.topicService = topicService;
        this.threadService = threadService;
    }

    @GetMapping("/topics/{topicName}")
    public ResponseEntity<ThreadTreeResponse> getTopic(@PathVariable String topicName) {
        Topic t = topicService.getByTitle(topicName);
        System.out.println(t);
        ThreadTreeResponse threads = threadService.getTopicResponse(t,0,10);
        return ResponseEntity.ok(threads);
    }
}
