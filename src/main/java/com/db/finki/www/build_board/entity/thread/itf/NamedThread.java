package com.db.finki.www.build_board.entity.thread.itf;

import com.db.finki.www.build_board.entity.user_type.BBUser;

import java.time.LocalDateTime;

public interface NamedThread {
    String getTitle();
    String getTypeName();
    Integer getId();
    String getContent();
    BBUser getUser();
    LocalDateTime getCreatedAt();
}
