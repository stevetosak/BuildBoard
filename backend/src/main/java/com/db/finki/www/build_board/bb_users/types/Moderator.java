package com.db.finki.www.build_board.bb_users.types;

import com.db.finki.www.build_board.bb_users.BBUser;
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
