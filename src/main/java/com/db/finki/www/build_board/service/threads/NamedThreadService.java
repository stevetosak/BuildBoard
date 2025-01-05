package com.db.finki.www.build_board.service.threads;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NamedThreadService {
    //TODO: Add project support also
     private final TopicServiceImpl topicsService;

    public NamedThreadService(TopicServiceImpl topicsService) {
        this.topicsService = topicsService;
    }

    public List<NamedThread> findAll(){
        List<NamedThread> results = (List<NamedThread>) (List<?>) topicsService.getAll();

        Collections.shuffle(results);
        return results;
    }
}
