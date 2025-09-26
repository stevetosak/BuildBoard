package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.view.NamedThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NamedThreadRepository extends JpaRepository<NamedThread, Integer> {
    @Query(nativeQuery = true,
            value = """
                    SELECT * 
                    FROM v_named_threads t 
                    WHERE (:title IS NULL OR t.title LIKE :title || '%') 
                    AND (CAST(:type as varchar) IS NULL OR t.type like :type || '%') 
                    AND (CAST(:content as varchar) IS NULL OR t.content LIKE :content || '%')
                    AND (CAST(:tags as varchar) IS NULL OR t.tags @> string_to_array(:tags,',')::varchar[])
                 """
    )
    List<NamedThread> findAll(
            @Param("title") String title,
            @Param("type") String type,
            @Param("content") String content,
            @Param("tags") String tags
                             );
}