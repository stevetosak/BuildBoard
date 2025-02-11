package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicRepository extends JpaRepository<Topic,Long>, JpaSpecificationExecutor<Topic> {
    Topic findByTitle(String title);
    void deleteByTitle(String title);
    Topic findById(long id);

    @Modifying
    @Query(value="""
            delete from topic_thread where id=:id
            """,
            nativeQuery = true
    )
    void deleteById(@Param("id") Long id); 
}
