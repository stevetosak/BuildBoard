package com.db.finki.www.build_board.service.threads;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.repository.threads.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.util.Pair;
import java.util.List;

@Service
public class TopicsService {
    private final TopicRepository topicRepository;

    public TopicsService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

}
