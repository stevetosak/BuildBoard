package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermission;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermissionResourceOverride;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


//todo override_type vo project_role
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRolePermissionResourceOverrideRepository extends JpaRepository<ProjectRolePermissionResourceOverride, ProjectRolePermissionResourceOverrideId> {

    @Query(value = """
            SELECT COALESCE(
                                        EXISTS (SELECT 1
                                                FROM project_role_is_assigned_to_developer upr
                                                         JOIN project_role pr
                                                              ON upr.role_id = pr.id
                                                         JOIN role_permissions rp
                                                              ON pr.id = rp.for_role
                                                         LEFT JOIN role_permissions_overrides rpo
                                                                   ON pr.id = rpo.for_role_permission_role_id
                                                                       AND rpo.for_role_permission_permission_name = rp.for_permission
                                                                       AND rpo.for_resource = :resourceId
                         
                                                WHERE upr.user_id = :userId
                                                  AND pr.valid_in = :projectId
                                                  AND rp.for_permission = :permissionName
                                                  AND (
                                                    (pr.override_type = 'INCLUDE' AND rpo.for_resource IS NOT NULL)
                                                        OR (pr.override_type = 'EXCLUDE' AND rpo.for_resource IS NULL)
                                                    )), FALSE
                                ) AS has_acces
          """, nativeQuery = true)
    boolean hasPermissionForResource(int projectId, int userId, String permissionName, UUID resourceId);
    List<ProjectRolePermissionResourceOverride> findAllByIdProjectRolePermissionIdRole(ProjectRole role);

    @Query(nativeQuery = true,value = """
  SELECT COALESCE(
                 EXISTS (SELECT 1
                         FROM project_role_is_assigned_to_developer upr
                                  JOIN project_role pr
                                       ON upr.role_id = pr.id
                                  LEFT JOIN role_permissions rp
                                            ON pr.id = rp.for_role AND rp.for_permission = :permissionName
                         WHERE upr.user_id = :userId
                           AND pr.valid_in = :projectId
                           AND (
                             (pr.override_type = 'INCLUDE' AND rp.for_permission IS NOT NULL)
                                 OR (pr.override_type = 'EXCLUDE' AND rp.for_permission IS NULL)
                             )), FALSE
         ) AS has_access;
""")
    boolean hasGlobalPermission(String permissionName,int projectId,int userId);

    @Modifying(clearAutomatically = true)
    void deleteAllByIdProjectRolePermissionIdRole(ProjectRole role);
}