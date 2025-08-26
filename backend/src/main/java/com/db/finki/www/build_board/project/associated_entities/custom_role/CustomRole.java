package com.db.finki.www.build_board.project.associated_entities.custom_role;

import com.db.finki.www.build_board.project.associated_entities.permissions.Permissions;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * Developer ke imat access da pishit vo site kanali i topici avtomatski
 * So roles ke mozhat slednive raboti da gi prat
 *  - TOPIC_MANAGER : da crud na topics vo dadeniot project
 *  - CHANNEL_MANAGER : da crud na channels vo dadeniot project
 *  - MEMBERS_MANAGER : da crud na members requests. Za kikvenje trebat admin da pristapit.
 */

@Setter
@Getter
@Entity
@Table(name = "project_roles")
@IdClass(CustomRoleID.class)
public class CustomRole implements GrantedAuthority {
    @Id
    @Column(name = "name")
    public String name;

    @Id
    @Column(name = "project_id")
    public int project;

    public String description;

    @ManyToMany
    @JoinTable(
            name = "project_roles_permissions",
            joinColumns = {
                    @JoinColumn(name = "role_name", referencedColumnName = "name"),
                    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
            },
            inverseJoinColumns = @JoinColumn(name = "permission_name", referencedColumnName =
                    "name")
    )
    public List<Permissions> permissions;

    @Override
    public String getAuthority() {
        return name;
    }
}
