package com.db.finki.www.build_board.entity.user_types;

import com.db.finki.www.build_board.entity.threads.BBThread;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class BBUser implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String description;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "is_activate")
    private boolean isEnabled;
    private String sex;

    @OneToMany(mappedBy = "user")
    private List<BBThread> threads;

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


}
