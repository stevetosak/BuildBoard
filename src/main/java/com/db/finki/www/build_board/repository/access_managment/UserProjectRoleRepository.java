package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.UsersProjectRoles;
import com.db.finki.www.build_board.entity.compositeId.UsersProjectRolesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProjectRoleRepository extends JpaRepository<UsersProjectRoles, UsersProjectRolesId> {
    List<UsersProjectRoles> findByIdRoleProjectIdAndIdUserId(int projectId, int userId);
    void deleteByIdRoleIdAndIdUserId(int roleId, int userId);
}
