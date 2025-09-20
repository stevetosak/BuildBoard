package com.db.finki.www.build_board.repository;

import com.db.finki.www.build_board.entity.view.RoleChannelPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleChannelPermissionsRepository extends JpaRepository<RoleChannelPermissions, Integer> {
    List<RoleChannelPermissions> findByRoleNameAndProjectId(String roleName, Integer projectId);
}
