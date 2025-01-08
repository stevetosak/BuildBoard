package com.db.finki.www.build_board.repository.threads;

import com.db.finki.www.build_board.entity.threads.discussion_threads.VDiscussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VDiscussRepo extends JpaRepository<VDiscussion,Long> {
    List<VDiscussion> findVDiscussionByParentTopicId(Long topicId);
}