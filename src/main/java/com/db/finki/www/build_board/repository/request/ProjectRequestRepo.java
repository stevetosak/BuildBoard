package com.db.finki.www.build_board.repository.request;

import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRequestRepo extends JpaRepository<ProjectRequests,Long> {
    List<ProjectRequests> findByStatusAndCreatorOrderByCreatedAtDesc(Status status, BBUser forUser);
    List<ProjectRequests> findByCreatorOrderByCreatedAtDesc(BBUser forUser);

    @Query(value = """
            select *
            from project_request pr
            where (:latest is null or (pr.user_id,pr.created_at) IN ( select user_id,max(created_at) from project_request pr group by user_id))
                        and pr.project_id=:projectId
                        and (:status is null or pr.status=:status)
            """,
            nativeQuery = true
    )
    List<ProjectRequests> getLatestRequestByProjectAndStatus(
            @Param("projectId") Integer projectId,
            @Param("status") String status,
            @Param("latest") String forLatest
    );
}
