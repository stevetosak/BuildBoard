package com.db.finki.www.build_board.project;

import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @GetMapping("{projectName}/threads")
    public Page<NamedThread> getThreadsForProject(
            @PathVariable String projectName,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) List<String> filters,
            @RequestParam(required = false, name = "threadType") String type,
            @RequestParam(required = false, defaultValue = "0") int page
                                                 ) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @GetMapping("{projectName}")
    public ProjectDTO getProject() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
