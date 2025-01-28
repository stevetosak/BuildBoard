package com.db.finki.www.build_board.repository.request;

import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.request.ProjectRequests;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRequestRepo extends JpaRepository<ProjectRequests,Long> {
    List<ProjectRequests> findByStatusOrderByCreatedAtDesc(Status status);
    List<ProjectRequests> findByStatusAndCreatorOrderByCreatedAtDesc(Status status, BBUser forUser);
    List<ProjectRequests> findByCreatorOrderByCreatedAtDesc(BBUser forUser);
    List<ProjectRequests> findByProjectOrderByCreatedAtDesc(Project project);
    List<ProjectRequests> findByStatusAndProjectOrderByCreatedAtDesc(Status status, Project project);
}
