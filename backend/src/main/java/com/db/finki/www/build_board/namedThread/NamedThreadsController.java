package com.db.finki.www.build_board.namedThread;

import com.db.finki.www.build_board.utils.named_threads.NamedThreadsDTOMapper;
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
    private final NamedThreadsDTOMapper  namedThreadsDTOMapper;

    public NamedThreadsController(
            SearchService searchService, NamedThreadsDTOMapper namedThreadsDTOMapper
                                 ) {
        this.searchService = searchService;
        this.namedThreadsDTOMapper = namedThreadsDTOMapper;
    }



    @GetMapping()
    public ResponseEntity<Page<NamedThreadDTO>> search(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false, name = "threadType") String type,
            @RequestParam(required = false) List<String> tag,
            @RequestParam(required = false, defaultValue = "0") int page
                                                      ) {

        if(title!=null)
            title=URLDecoder.decode(title, StandardCharsets.UTF_8);
        if(content!=null)
            content=URLDecoder.decode(content, StandardCharsets.UTF_8);

        Page<NamedThread> namedThreads = searchService.search(
               title,
                content,
                type,
                tag,
                PageRequest.of(page,
                        5),null
                                                             );
        return ResponseEntity.ok(namedThreadsDTOMapper.map(namedThreads));
    }
}
