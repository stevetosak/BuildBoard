package com.db.finki.www.build_board.controller.home_page;

import com.db.finki.www.build_board.dto.NamedThreadDTO;
import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import com.db.finki.www.build_board.service.search.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/threads")
public class NamedThreadsController {
    private final SearchService searchService;

    public NamedThreadsController(
            SearchService searchService
                                 ) {
        this.searchService = searchService;
    }


    @GetMapping()
    public ResponseEntity<Page<NamedThreadDTO>> search(@RequestParam(required = false) String query, @RequestParam(required = false) List<String> filters, @RequestParam(required = false) String type, @RequestParam(required = false, defaultValue = "0") int page) {
        if (filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
            filters.add("all");
        }

        Page<NamedThread> namedThreads = searchService.search(query,
                filters,
                type,
                PageRequest.of(page,
                        5));
        NamedThreadDTO.NamedThreadDTOBuilder builder = NamedThreadDTO.builder();

        return ResponseEntity.ok(namedThreads.map(namedThread -> builder
                .createdAt(namedThread.getCreatedAt())
                .threadType(namedThread.getTypeName())
                .creator(new NamedThreadDTO.Creator(namedThread
                        .getUser()
                        .getUsername(),
                        namedThread
                                .getUser()
                                .getAvatarUrl()))
                .content(new NamedThreadDTO.Content(namedThread.getTitle(),
                        namedThread.getContent(),
                        namedThread
                                .getTags()
                                .stream()
                                .map(Tag::getName)
                                .toList()))
                .build()));
    }
}
