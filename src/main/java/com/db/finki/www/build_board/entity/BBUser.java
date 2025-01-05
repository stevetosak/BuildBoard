package com.db.finki.www.build_board.entity;

import com.db.finki.www.build_board.entity.threads.BBThread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@Entity
@Table(name = "users")
@Getter @Setter
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
    public String getUsername() {
        return username;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public String getSex() {
        return sex;
    }

    public List<BBThread> getThreads() {
        return threads;
    }
}
