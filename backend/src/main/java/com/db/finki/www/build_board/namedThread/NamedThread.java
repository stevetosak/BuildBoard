package com.db.finki.www.build_board.namedThread;

import com.db.finki.www.build_board.service.util.FileUploadService;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity
@Immutable
@Table(name = "v_named_threads")
public class NamedThread {
    @Id
    private Integer id;
    private Integer parent;
    private String content;
    private String title;
    private String username;
    private Integer userId;
    private LocalDateTime createdAt;
    private String type;
    private List<String> tags;

    public String getUsersAvatarUrl() {
        Path path =
                Path.of(FileUploadService.USER_AVATAR_DIR + File.separator + "avatar-" + userId);
        return Files.exists(path) ? File.separator + "avatars" + File.separator + "avatar-"+userId:
                File.separator + "default-avatar.jpg";
    }
}