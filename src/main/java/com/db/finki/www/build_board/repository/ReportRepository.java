package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.request.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query(value = """
            select *
            from report r
            join submission s
            on s.id = r.id
            where (:latest is null or (s.created_by,s.created_at) IN ( select created_by,max(created_at) from submission r  group by created_by))
                        and r.thread_id =:topicId
                        and (:status is null or s.status=:status)
            """,
            nativeQuery = true
    )
    List<Report> getLatestRequestByTopicAndStatus(
            @Param("topicId") Integer topicId,
            @Param("status") String status,
            @Param("latest") String forLatest
    );

    @Query(
            nativeQuery = true,
            value = """
                        select *
                        from report r
                        join submission s
                        on s.id = r.id
                        where s.created_by = :user_id and (:status is null or s.status= :status)
                    """
    )
    List<Report> findAllBySendByUsernameAndStatus(@Param("user_id") long userId,
                                                  @Param("status") String status);
}
