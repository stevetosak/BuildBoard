package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermission;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermissionResourceOverride;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRolePermissionResourceOverrideRepository extends JpaRepository<ProjectRolePermissionResourceOverride, ProjectRolePermissionResourceOverrideId> {

    @Query(value = """
            SELECT COALESCE(
                           EXISTS (
                               SELECT 1
                               FROM users_project_roles upr
                                        JOIN role_permissions rp
                                             ON upr.role_name = rp.role_name
                                                 AND upr.project_id = rp.project_id
                                        LEFT JOIN role_permissions_overrides rpo
                                                  ON rpo.role_name = rp.role_name
                                                      AND rpo.project_id = rp.project_id
                                                      AND rpo.permission_name = rp.permission_name
                                                      AND rpo.project_resource_id = :resourceId
                               WHERE upr.user_id = :userId
                                 AND rp.project_id = :projectId
                                 AND rp.permission_name = :permissionName
                                 AND (
                                   (rp.override_type = 'INCLUDE' AND rpo.project_resource_id IS NOT NULL)
                                       OR (rp.override_type = 'EXCLUDE' AND rpo.project_resource_id IS NULL)
                                   )
                           ), FALSE
                   ) AS has_access;
            """, nativeQuery = true)
    boolean hasPermissionForResource(int projectId,int userId,String permissionName,int resourceId);

    List<ProjectRolePermissionResourceOverride> findAllByIdProjectRolePermissionIdProjectRole(ProjectRole projectRole);
}