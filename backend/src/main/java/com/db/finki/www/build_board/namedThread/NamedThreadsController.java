package com.db.finki.www.build_board.namedThread;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false, name = "threadType") String type,
            @RequestParam(required = false) List<String> tag,
            @RequestParam(required = false, defaultValue = "0") int page
                                                      ) {

        title=URLDecoder.decode(title, StandardCharsets.UTF_8);
        content=URLDecoder.decode(content, StandardCharsets.UTF_8);

        Page<NamedThread> namedThreads = searchService.search(
               title,
                content,
                type,
                tag,
                PageRequest.of(page,
                        5),null
                                                             );
        NamedThreadDTO.NamedThreadDTOBuilder builder = NamedThreadDTO.builder();

        return ResponseEntity.ok(
                namedThreads.map(namedThread -> builder
                        .createdAt(namedThread.getCreatedAt())
                        .threadType(namedThread.getType())
                        .creator(new NamedThreadDTO.Creator(
                                namedThread.getUsername(),
                                namedThread.getUsersAvatarUrl()
                        ))
                        .content(new NamedThreadDTO.Content(namedThread.getTitle(),
                                truncateContent(namedThread.getContent()),
                                 namedThread.getTags()
                        )).build()));
    }
}
