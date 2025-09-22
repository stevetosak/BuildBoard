package com.db.finki.www.build_board.entity.compositeId;

import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermission;
import com.db.finki.www.build_board.entity.channel.Channel;
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
    @JoinColumn(name = "channel_id",referencedColumnName = "id")
    private Channel channel;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRolePermissionResourceOverrideId that = (ProjectRolePermissionResourceOverrideId) o;
        return Objects.equals(projectRolePermission, that.projectRolePermission) && Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectRolePermission, channel);
    }
}
