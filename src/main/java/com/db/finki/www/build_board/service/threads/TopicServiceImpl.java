package com.db.finki.www.build_board.service.threads;

import com.db.finki.www.build_board.entity.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.repository.threads.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    @Override
    public void create(String title, String description, BBUser user) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(description);
        topic.setUser(user);
        topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }
}
