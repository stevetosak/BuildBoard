package com.db.finki.www.build_board.entity.compositeId;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeveloperAssociatedWithProjectId {
    private BBUser developer;
    private Project project;
    private LocalDateTime startedAt;
}
