package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.discussion_thread.VDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VDiscussRepo extends JpaRepository<VDiscussion,Long> {
    List<VDiscussion> findVDiscussionByParentTopicIdOrderByCreatedAtDesc(Integer topicId);
    VDiscussion findVDiscussionByDiscussionId(Integer discussionId);
}