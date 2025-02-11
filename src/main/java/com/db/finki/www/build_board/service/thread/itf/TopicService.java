package com.db.finki.www.build_board.service.thread.itf;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.Topic;

import java.util.List;
public interface TopicService {
    Topic create(String title, String description, BBUser user);
    Topic create(String title, String description, BBUser user, Project parent);
    List<Topic> getAll();
    Topic getByTitle(String title);
    void deleteTopicById(Long id);
    void deleteTopicByTitle(String title);
    Topic getById(Long id);
    void addTagToTopic(Topic topic, String tagName, BBUser user);
    Topic edit(Topic t, String title, String description);
    Topic deleteTagFromTopic(long id, String tagName);
}
