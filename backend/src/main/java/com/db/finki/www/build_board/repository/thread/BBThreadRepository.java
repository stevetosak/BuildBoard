package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.ThreadView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BBThreadRepository extends JpaRepository<BBThread, Long> {
    BBThread findById(long id);
    @Query(value = """
            WITH RECURSIVE tree AS (
                        SELECT id,content,created_at,level,parent_id,user_id,type
                        FROM thread
                        WHERE id = :rootId
            
                        UNION ALL
            
                        SELECT child.id,child.content,child.created_at,child.level,child.parent_id,child.user_id,child.type
                        FROM thread child
                                 JOIN tree parent ON child.parent_id = parent.id
                    ),
                    thread_data AS (
                        SELECT
                            tc.*,u.id as userId,u.username,
                            (SELECT COUNT(*) FROM tree row WHERE tc.id = row.parent_id) AS numReplies,
                            (SELECT COUNT(*) FROM likes l WHERE l.thread_id = tc.id) AS numLikes
                        FROM tree tc join users u on u.id = tc.user_id
                    )
                    SELECT id,content,created_at as createdAt,level,parent_id as parentId,type,userId,username,numReplies,numLikes
                    FROM thread_data td
                    ORDER BY
                        CASE WHEN parent_id IS NULL THEN 0 ELSE 1 END,
                        level,
                        0.3 * numReplies + 0.7 * numLikes DESC OFFSET :page * :size LIMIT :size;
            """,
            nativeQuery = true)
    List<ThreadView> getThreadTree(int rootId, int page, int size);
}
