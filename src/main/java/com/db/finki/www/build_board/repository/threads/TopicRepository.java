package com.db.finki.www.build_board.repository.threads;

import com.db.finki.www.build_board.entity.threads.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {
}
