package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.ThreadView;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import com.db.finki.www.build_board.rest.dto.ThreadTreeResponse;
import com.db.finki.www.build_board.service.thread.impl.ThreadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/replies/add")
    public ResponseEntity<ThreadView> addReply(@RequestParam int targetThreadId,@RequestBody ThreadView threadView) {
        BBThread parent = threadRepository.findById(targetThreadId);
//        BBThread newThread = new BBThread(null,threadView.getContent(),threadView.getLevel(),threadView.getParentId());
        return null;
    }
}
