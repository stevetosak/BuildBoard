package com.db.finki.www.build_board.repository.thread;

import com.db.finki.www.build_board.entity.thread.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Project findByTitleStartingWith(String title);
    void deleteByTitle(String title);
}
