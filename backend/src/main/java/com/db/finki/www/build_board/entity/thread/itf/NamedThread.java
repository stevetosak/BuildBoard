package com.db.finki.www.build_board.entity.thread.itf;

import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.user_type.BBUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface NamedThread {
    String getTitle();
    String getTypeName();
    Integer getId();
    String getContent();
    BBUser getUser();
    LocalDateTime getCreatedAt();
    Set<Tag> getTags();
}
