package com.db.finki.www.build_board.bb_users.types;

import com.db.finki.www.build_board.bb_users.BBUser;
import com.db.finki.www.build_board.project.associated_entities.custom_role.CustomRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@Table(name = "developer")
@Getter
@Setter
@NoArgsConstructor
public class Developer extends BBUser {

    @ManyToMany
    @JoinTable(
            name = "users_project_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
                    @JoinColumn(name = "role_name", referencedColumnName = "name")
            }
    )
    private List<CustomRole> projectRoles;

    @Override
    public List<GrantedAuthority> getAuthority(){
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
