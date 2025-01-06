package com.db.finki.www.build_board.service.threads.itfs;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;

import java.util.List;
public interface TopicService {
    Topic create(String title, String description, BBUser user);
    List<Topic> getAll();
    Topic getByTitle(String title);
    void deleteTopicById(Long id);
    void deleteTopicByTitle(String title);
    Topic getById(Long id);
    void addTagToTopic(Topic topic, String tagName);

    Topic save(long id, String title, String description);
    Topic deleteTagFromTopic(long id, String tagName);
}
