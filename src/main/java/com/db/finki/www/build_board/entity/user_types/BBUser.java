package com.db.finki.www.build_board.entity.user_types;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.service.util.FileUploadService;
import jakarta.persistence.*;
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
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class BBUser implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private int id;

    private String username;
    private String password;
    private String description;

    private String name;
    private String email;

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

    public String getAvatarUrl() {
        Path path = Path.of(FileUploadService.USER_AVATAR_DIR + File.separator + "avatar-" + id);
        return Files.exists(path) ? File.separator + "avatars" + File.separator + "avatar-"+id : File.separator + "default-avatar.jpg";
    }



    @Override
    public boolean equals(Object other){
        System.out.println("VLEZE EQUALS");
        if(!(other instanceof BBUser)){
            return false;
        }
        BBUser otherCasted = (BBUser) other;
        return otherCasted.getId() == this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public BBUser(
            String username, String email, String name, String password, String description, String sex
    ){
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.description = description;
        this.sex=sex;
        this.isEnabled=true;
    }
}
