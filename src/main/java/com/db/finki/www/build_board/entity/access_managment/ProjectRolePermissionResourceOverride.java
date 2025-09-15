package com.db.finki.www.build_board.entity.access_managment;

import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role_permissions_overrides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRolePermissionResourceOverride {

    @EmbeddedId
    private ProjectRolePermissionResourceOverrideId projectRolePermissionResourceOverrideId;


    public ProjectRolePermission getProjectRolePermission() {
        return projectRolePermissionResourceOverrideId.getProjectRolePermission();
    }
    public ProjectResource getProjectResource() {
        return projectRolePermissionResourceOverrideId.getProjectResource();
    }
}
