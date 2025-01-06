package com.db.finki.www.build_board.repository.threads;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long>, JpaSpecificationExecutor<Topic> {
    Topic findByTitle(String title);
    void deleteByTitle(String title);
    Topic findById(long id);;
}
