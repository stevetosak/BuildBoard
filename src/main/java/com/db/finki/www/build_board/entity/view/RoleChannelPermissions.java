package com.db.finki.www.build_board.entity.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.Objects;

@Entity
@Immutable
@Table(name = "role_channel_permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleChannelPermissions {
    @Id
    @Column(name = "project_resource_id")
    private Integer resourceId;
    @Column(name = "project_id")
    private Integer projectId;
    @Column(name = "name")
    private String channelName;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "permissions")
    private String permissions; // comma seperated PERMISSIONS

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleChannelPermissions that = (RoleChannelPermissions) o;
        return Objects.equals(resourceId, that.resourceId) && Objects.equals(projectId, that.projectId) && Objects.equals(channelName, that.channelName) && Objects.equals(roleName, that.roleName) && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceId, projectId, channelName, roleName, permissions);
    }
}
