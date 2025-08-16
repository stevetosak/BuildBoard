package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.entity.thread.ThreadView;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import com.db.finki.www.build_board.rest.dto.ThreadTreeResponse;
import com.db.finki.www.build_board.service.thread.impl.ThreadService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ThreadTreeResponse> getRepliesForThread(@RequestParam int threadId) {
        ThreadTreeResponse threads = threadService.getRepliesResponse(threadId,0,20);
        return ResponseEntity.ok(threads);
    }
}
