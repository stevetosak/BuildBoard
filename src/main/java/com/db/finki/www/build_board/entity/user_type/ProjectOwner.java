package com.db.finki.www.build_board.entity.user_type;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project_manager")
public class ProjectOwner extends Developer{
    @Override
    public Collection<GrantedAuthority> getAuthority(){
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_PROJECT_OWNER"));
    }
}
