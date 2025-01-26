package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.threads.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRepository extends JpaRepository<Topic,Long>, JpaSpecificationExecutor<Topic> {
    Topic findByTitle(String title);
    void deleteByTitle(String title);
    Topic findById(long id);;
}
