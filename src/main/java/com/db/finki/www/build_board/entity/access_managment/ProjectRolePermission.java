package com.db.finki.www.build_board.entity.access_managment;

import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "role_permissions")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class ProjectRolePermission {
    @EmbeddedId
    private ProjectRolePermissionId id;

    public ProjectRolePermission(ProjectRolePermissionId projectRolePermissionId) {
        this.id = projectRolePermissionId;
    }

    public Permission getPermission() {
        return id.getPermission();
    }

    public ProjectRole getProjectRole() {
        return id.getRole();
    }


}
