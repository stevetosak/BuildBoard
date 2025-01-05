package com.db.finki.www.build_board.service.threads;

import com.db.finki.www.build_board.entity.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;

import java.util.List;

public interface TopicService {
    void create(String title, String description, BBUser user);
    List<Topic> getAll();
}
