package com.db.finki.www.build_board.namedThread;

import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/threads")
public class NamedThreadsController {
    private final SearchService searchService;

    public NamedThreadsController(
            SearchService searchService
                                 ) {
        this.searchService = searchService;
    }

    private String truncateContent(String content) {
        double percentageToDisplayOnHomePage = .6;
        int shortDescriptionLength = (int) (content.length() * percentageToDisplayOnHomePage);
        return content.substring(0,
                shortDescriptionLength);
    }

    @GetMapping()
    public ResponseEntity<Page<NamedThreadDTO>> search(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) List<String> filters,
            @RequestParam(required = false, name = "threadType") String type,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) List<String> tag
                                                      ) {
        if (filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
            filters.add("all");
        }
        query = URLDecoder.decode(query,
                StandardCharsets.UTF_8);

        Page<NamedThread> namedThreads = searchService.search(query,
                filters,
                type,
                PageRequest.of(page,
                        5));
        NamedThreadDTO.NamedThreadDTOBuilder builder = NamedThreadDTO.builder();

        return ResponseEntity.ok(
                namedThreads.map(namedThread -> builder
                        .createdAt(namedThread.getCreatedAt())
                        .threadType(namedThread.getTypeName())
                        .creator(new NamedThreadDTO.Creator(namedThread
                                .getUser()
                                .getUsername(),
                                namedThread
                                        .getUser()
                                        .getAvatarUrl()))
                        .content(new NamedThreadDTO.Content(namedThread.getTitle(),
                                truncateContent(namedThread.getContent()),
                                namedThread
                                        .getTags()
                                        .stream()
                                        .map(Tag::getName)
                                        .toList()))
                        .build()));
    }
}
