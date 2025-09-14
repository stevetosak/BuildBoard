package com.db.finki.www.build_board.service.thread.itf;

import com.db.finki.www.build_board.entity.blacklisted_user.BlacklistedUser;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.service.BlacklistedUserType;

import java.util.List;
import java.util.Map;

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
    Map<BlacklistedUserType,List<BlacklistedUser>> getBlacklistedUsersForTopicById(long id);
}
