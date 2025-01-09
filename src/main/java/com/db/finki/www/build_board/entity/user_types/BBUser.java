package com.db.finki.www.build_board.entity.user_types;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.service.threads.impl.FileUploadService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

     @Transient
    private String avatarUrl;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getAvatarUrl() {
        Path path = Path.of(FileUploadService.USER_AVATAR_DIR + File.separator + "avatar-" + id);
        return Files.exists(path) ? File.separator + "avatars" + File.separator + "avatar-"+id : File.separator + "default-avatar.jpg";
    }

}
