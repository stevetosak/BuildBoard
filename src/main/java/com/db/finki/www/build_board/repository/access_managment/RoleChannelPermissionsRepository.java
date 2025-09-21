package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.view.RoleChannelPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleChannelPermissionsRepository extends JpaRepository<RoleChannelPermissions, Integer> {
    List<RoleChannelPermissions> findByRole(ProjectRole role);
}
