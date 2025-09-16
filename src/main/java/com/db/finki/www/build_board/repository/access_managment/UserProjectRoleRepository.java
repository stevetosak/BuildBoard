package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.access_managment.UsersProjectRoles;
import com.db.finki.www.build_board.entity.compositeId.UsersProjectRolesId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRoleRepository extends JpaRepository<UsersProjectRoles, UsersProjectRolesId> {
    List<UsersProjectRoles> findByIdRoleIdProjectId(int projectId);
}
