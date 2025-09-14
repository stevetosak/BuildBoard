package com.db.finki.www.build_board.repository.access_managment;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermission;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRolePermissionRepository extends JpaRepository<ProjectRolePermission, ProjectRolePermissionId> {
    List<ProjectRolePermission> findByIdProjectRole(ProjectRole projectRole);

    @Query(nativeQuery = true,value = """
            SELECT EXISTS (
                            SELECT upr.role_name,upr.project_id FROM users_project_roles upr
                            JOIN role_permissions prp
                            ON prp.project_id = upr.project_id AND prp.role_name = upr.role_name
                            WHERE upr.user_id = :userId
                                AND prp.project_id = :projectId
                                AND prp.permission_name = :permissionName
                                AND prp.project_resource_id = :resourceId
                        )
            """)
    boolean isAuthorizedToPerformActionOnResource(int projectId,int userId,String permissionName,int resourceId);

}
