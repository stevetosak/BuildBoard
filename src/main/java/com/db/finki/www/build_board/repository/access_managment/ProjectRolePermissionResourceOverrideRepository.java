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

@Repository
public interface ProjectRolePermissionResourceOverrideRepository extends JpaRepository<ProjectRolePermissionResourceOverride, ProjectRolePermissionResourceOverrideId> {

    @Query(value = """
            SELECT COALESCE(
                  EXISTS (SELECT 1
                          FROM users_project_roles upr
                                   JOIN project_role pr
                               ON upr.role_id = pr.id
                                   JOIN role_permissions rp
                                    ON pr.id = rp.role_id
                                   LEFT JOIN role_permissions_overrides rpo
                                             ON pr.id = rpo.role_id
                                                 AND rpo.permission_name = rp.permission_name
                                                 AND rpo.project_resource_id = :resourceId
   
                          WHERE upr.user_id = :userId
                            AND pr.project_id = :projectId
                            AND rp.permission_name = :permissionName
                            AND (
                              (pr.override_type = 'INCLUDE' AND rpo.project_resource_id IS NOT NULL)
                                  OR (pr.override_type = 'EXCLUDE' AND rpo.project_resource_id IS NULL)
                              )), FALSE
          ) AS has_access;
          """, nativeQuery = true)
    boolean hasPermissionForResource(int projectId,int userId,String permissionName,int resourceId);
    List<ProjectRolePermissionResourceOverride> findAllByIdProjectRolePermissionIdRole(ProjectRole role);

    @Modifying(clearAutomatically = true)
    void deleteAllByIdProjectRolePermissionIdRole(ProjectRole role);
}