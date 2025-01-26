package com.db.finki.www.build_board.repository.request;

import com.db.finki.www.build_board.entity.enums.Status;
import com.db.finki.www.build_board.entity.requests.ProjectRequests;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRequestRepo extends JpaRepository<ProjectRequests,Long> {
    List<ProjectRequests> findByStatus(Status status);
    List<ProjectRequests> findByStatusAndCreator(Status status, BBUser forUser);
    List<ProjectRequests> findByCreator(BBUser forUser);

    List<ProjectRequests> findByProject(Project project);

    List<ProjectRequests> findByStatusAndProject(Status status, Project project);
}
