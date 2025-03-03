package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Project findByTitle(String title);
    void deleteByTitle(String title);
    List<Project> findAllByUserId(int userId);

    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE developer_associated_with_project dap set ended_at=now() where dap.developer_id=:uid AND dap.project_id=:pid")
    void removeUserFromProject(int pid,int uid);

    @Modifying
    @Query(nativeQuery = true,
    value = "INSERT INTO developer_associated_with_project (project_id, developer_id, started_at, ended_at) VALUES (:pid,:uid,now(),null)")
    void addUserToProject(int pid,int uid);

}
