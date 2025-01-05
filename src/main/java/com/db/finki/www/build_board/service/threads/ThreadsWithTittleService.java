package com.db.finki.www.build_board.service.threads;

import com.db.finki.www.build_board.entity.threads.interfaces.ThreadsWithTittle;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ThreadsWithTittleService {
    //TODO: Add project support also
     private final TopicsService topicsService;

    public ThreadsWithTittleService(TopicsService topicsService) {
        this.topicsService = topicsService;
    }

    public List<ThreadsWithTittle> findAll(){
        List<ThreadsWithTittle> results = (List<ThreadsWithTittle>) (List<?>) topicsService.findAll();

        Collections.shuffle(results);
        return results;
    }
}
