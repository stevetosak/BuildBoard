package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermissionResourceOverride;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRolePermissionResourceOverrideRepository extends JpaRepository<ProjectRolePermissionResourceOverride, ProjectRolePermissionResourceOverrideId> {

    @Query(value = """
            SELECT COALESCE((
                                SELECT
                                    CASE
                                        WHEN rp.override_type = 'INCLUDE'
                                            THEN EXISTS (
                                            SELECT 1
                                            FROM role_permissions_overrides rpo
                                            WHERE rpo.role_name = rp.role_name
                                              AND rpo.project_id = rp.project_id
                                              AND rpo.permission_name = rp.permission_name
                                              AND rpo.project_resource_id = :resource_id
                                        )
                                        WHEN rp.override_type = 'EXCLUDE'
                                            THEN NOT EXISTS (
                                            SELECT 1
                                            FROM role_permissions_overrides rpo
                                            WHERE rpo.role_name = rp.role_name
                                              AND rpo.project_id = rp.project_id
                                              AND rpo.permission_name = rp.permission_name
                                              AND rpo.project_resource_id = :resource_id
                                        )
                                        END
                                FROM users_project_roles upr
                                         JOIN role_permissions rp
                                              ON upr.role_name = rp.role_name
                                                  AND upr.project_id = rp.project_id
                                WHERE upr.user_id = :user_id
                                  AND rp.project_id = :project_id
                                  AND rp.permission_name = :permission_name
                                LIMIT 1
                            ), FALSE) AS has_access;
            """, nativeQuery = true)
    boolean hasPermissionForResource(int projectId,int userId,String permissionName,int resourceId);
}