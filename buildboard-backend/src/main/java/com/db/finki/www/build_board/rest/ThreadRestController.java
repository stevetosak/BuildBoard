package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import com.db.finki.www.build_board.rest.dto.ThreadDto;
import com.db.finki.www.build_board.service.thread.impl.ThreadService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ThreadRestController {
    private final BBThreadRepository threadRepository;
    private final ThreadService threadService;

    public ThreadRestController(BBThreadRepository threadRepository, ThreadService threadService) {
        this.threadRepository = threadRepository;
        this.threadService = threadService;
    }
    @GetMapping("/replies")
    public ResponseEntity<List<ThreadDto>> getRepliesForThread(@RequestParam int threadId) {
        BBThread thread = threadRepository.findById(threadId);
        List<ThreadDto> replies = threadService.getDirectRepliesForThread(thread);
        if (replies.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(replies);
    }
}
