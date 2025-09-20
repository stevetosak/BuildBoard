package com.db.finki.www.build_board.entity.compositeId;


import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRolePermissionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "permission_name", referencedColumnName = "name")
    private Permission permission;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private ProjectRole role;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectRolePermissionId that)) return false;
        return Objects.equals(permission, that.permission) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission, role);
    }
}

