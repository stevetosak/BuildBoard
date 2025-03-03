package com.db.finki.www.build_board.entity.user_type;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "moderator")
public class Moderator extends BBUser {
    @Override
    public List<GrantedAuthority> getAuthority(){
        return List.of(new SimpleGrantedAuthority("ROLE_MODERATOR"));
    }
}
