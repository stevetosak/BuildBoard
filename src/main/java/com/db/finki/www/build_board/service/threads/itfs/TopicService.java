package com.db.finki.www.build_board.service.threads.itfs;

import com.db.finki.www.build_board.entity.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;

import java.util.List;
public interface TopicService {
    void create(String title, String description, BBUser user);
    List<Topic> getAll();
    Topic getByTitle(String title);
    void deleteTopicById(Long id);
    void deleteTopicByTitle(String title);
    Topic getById(Long id);

    void addTagToTopic(Topic topic, String tagName);
}
