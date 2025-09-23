package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.thread.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRoleRepository  extends JpaRepository<ProjectRole, Integer> {
    List<ProjectRole> findByProject(Project project);

    List<ProjectRole> findByProjectId(Integer projectId);

    List<ProjectRole> findAllByNameInAndProject(List<String> roleNames,Project project);
    ProjectRole findByNameAndProject(String roleName,Project project);
}
