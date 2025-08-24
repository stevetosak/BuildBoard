package com.db.finki.www.build_board.project;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Page<Project> findAll(Specification<Project> spec, Pageable pageable);
    List<Project> findAll();
    Project save(Project project);
    Optional<Project> findById(Long id);
    void delete(Project project);

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
