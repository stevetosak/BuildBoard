package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.compositeId.ProjectRoleId;
import com.db.finki.www.build_board.entity.thread.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRoleRepository  extends JpaRepository<ProjectRole, ProjectRoleId> {
    List<ProjectRole> findByIdProject(Project project);
}
