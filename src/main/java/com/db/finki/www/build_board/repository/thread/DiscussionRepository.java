package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.discussion_thread.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion,Long> {
    Discussion findDiscussionById(int discussionId);
}
