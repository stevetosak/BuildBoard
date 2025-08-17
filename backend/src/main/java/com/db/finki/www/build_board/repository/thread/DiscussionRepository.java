package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.discussion_thread.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Discussion findDiscussionById(int discussionId);

    @Modifying
    @Query(
            value = """
                    delete from discussion_thread dt where id=:id
                """, nativeQuery = true
    )
    void deleteById(@Param("id") long discussionId);
    Optional<List<Discussion>> findAllByParentIdAndLevel(int parentId, int level);
}
