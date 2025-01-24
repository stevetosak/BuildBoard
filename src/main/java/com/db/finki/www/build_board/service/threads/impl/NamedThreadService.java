package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
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

    public List<NamedThread> findAll(){
        List<NamedThread> results = (List<NamedThread>) (List<?>) topicService.getAll();
        results.addAll((List<NamedThread>)(List<?>) projectService.getAll());
        return results;
    }
}
