package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.config.jwt.JWTAuthentication;
import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.ThreadView;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import com.db.finki.www.build_board.rest.dto.ThreadDto;
import com.db.finki.www.build_board.rest.dto.ThreadTreeResponse;
import com.db.finki.www.build_board.service.thread.impl.ThreadService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ThreadDto> addReply(@RequestBody ThreadDto threadDto,
                                               JWTAuthentication authentication) {
        ThreadDto saved = threadService.addReply(authentication.getPrincipal(),threadDto);
        return ResponseEntity.ok(saved);
    }
    @PostMapping("/replies/delete")
    public ResponseEntity<Void> deleteReply(@RequestParam int id) {
        threadService.deleteThread(id);
        return ResponseEntity.ok().build();
    }
}
