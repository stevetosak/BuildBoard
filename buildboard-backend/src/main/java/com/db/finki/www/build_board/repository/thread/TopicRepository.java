package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.Topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TopicRepository extends PagingAndSortingRepository<Topic,Long>, JpaSpecificationExecutor<Topic> {
    Page<Topic> findAll(Specification<Topic> spec, Pageable pageable);
    List<Topic> findAll();
    Topic findByTitle(String title);
    void deleteByTitle(String title);
    Topic findById(long id);
    Topic save(Topic topic);
    Optional<Topic> findById(Long id);

    @Modifying
    @Query(value="""
            delete from topic_thread where id=:id
            """,
            nativeQuery = true
    )
    void deleteById(@Param("id") Long id); 
}
