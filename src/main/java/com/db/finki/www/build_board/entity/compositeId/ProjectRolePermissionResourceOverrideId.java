package com.db.finki.www.build_board.entity.compositeId;

import com.db.finki.www.build_board.entity.access_managment.ProjectResource;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermission;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRolePermissionResourceOverrideId {
    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "role_id",referencedColumnName = "role_id"),
            @JoinColumn(name = "permission_name",referencedColumnName = "permission_name")
    })
    private ProjectRolePermission projectRolePermission;
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_resource_id",referencedColumnName = "id")
    private ProjectResource  projectResource;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRolePermissionResourceOverrideId that = (ProjectRolePermissionResourceOverrideId) o;
        return Objects.equals(projectRolePermission, that.projectRolePermission) && Objects.equals(projectResource, that.projectResource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectRolePermission, projectResource);
    }
}
