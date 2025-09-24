package com.db.finki.www.build_board.entity.view;

import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.util.Objects;
import java.util.UUID;

@Entity
@Immutable
@Table(name = "role_channel_permissions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleChannelPermissions {
    @Id
    @Column(name = "channel_id")
    private UUID channelId;
    @Column(name = "name")
    private String channelName;
    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "id")
    private ProjectRole role;
    @Column(name = "permissions")
    private String permissions; // comma seperated PERMISSIONS

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RoleChannelPermissions that = (RoleChannelPermissions) o;
        return Objects.equals(channelId, that.channelId) && Objects.equals(channelName, that.channelName) && Objects.equals(role, that.role) && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelId, channelName, role, permissions);
    }
}
