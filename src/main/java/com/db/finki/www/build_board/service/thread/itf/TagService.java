package com.db.finki.www.build_board.service.thread.itf;

import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.Topic;

import java.util.List;

public interface TagService {
    Tag getByName(String name);
    List<Tag> getAll();
    List<Tag> getAllNotUsed(Topic t);
    Tag create(String name);
}
