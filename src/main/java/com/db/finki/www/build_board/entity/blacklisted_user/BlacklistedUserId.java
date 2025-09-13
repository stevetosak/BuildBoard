package com.db.finki.www.build_board.entity.blacklisted_user;

import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.user_type.Moderator;

import java.time.LocalDateTime;

public class BlacklistedUserId {
    Topic topic;
    Moderator moderator;
    LocalDateTime startTime;
    BBUser refersTo;
}
