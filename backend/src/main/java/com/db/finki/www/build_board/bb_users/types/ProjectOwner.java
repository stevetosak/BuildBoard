package com.db.finki.www.build_board.bb_users.types;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project_manager")
public class ProjectOwner extends Developer{
    @Override
    public List<GrantedAuthority> getAuthority(){
        return List.of(new SimpleGrantedAuthority("ROLE_PROJECT_OWNER"));
    }
}
