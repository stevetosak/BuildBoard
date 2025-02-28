package com.db.finki.www.build_board.service.util;

import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.thread.impl.TopicServiceImpl;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamedThreadService {
    private final TopicService topicService;
    private final ProjectService projectService;

    public NamedThreadService(TopicServiceImpl topicService, ProjectService projectService) {
        this.topicService = topicService;
        this.projectService = projectService;
    }

    public List<NamedThread> getAll(){
        List<NamedThread> results = (List<NamedThread>) (List<?>) topicService.getAll();
        results.addAll((List<NamedThread>)(List<?>) projectService.getAll());
        return results;
    }
}
