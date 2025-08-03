package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.rest.dto.*;
import com.db.finki.www.build_board.service.thread.impl.DiscussionService;
import com.db.finki.www.build_board.service.thread.impl.ThreadService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<TopicResponseDto> getTopic(@PathVariable String topicName) {
        System.out.println("TopicRestController getTopic");
        System.out.println(topicName);
        Topic t = topicService.getByTitle(topicName);
        System.out.println(t);
        List<ThreadDto> replies = threadService.getDirectRepliesForThread(t);
        UserDto userDto = new UserDto(t.getUser().getId(), t.getUser().getUsername(), t.getUser().getAvatarUrl());
        ThreadDto topicDto = new ThreadDto(t.getId(),
                t.getTitle(),
                t.getContent(),
                userDto,
                t.getCreatedAt().toString(),
                t.getLevel());

        System.out.println(new TopicResponseDto(topicDto,replies));

        return ResponseEntity.ok(new TopicResponseDto(topicDto, replies));
    }
}
