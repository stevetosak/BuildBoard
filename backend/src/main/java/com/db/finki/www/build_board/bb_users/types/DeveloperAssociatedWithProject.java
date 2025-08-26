package com.db.finki.www.build_board.bb_users.types;

import com.db.finki.www.build_board.bb_users.BBUser;
import com.db.finki.www.build_board.entity.compositeId.DeveloperAssociatedWithProjectId;
import com.db.finki.www.build_board.entity.thread.Project;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "developer_associated_with_project")
@IdClass(DeveloperAssociatedWithProjectId.class)
public class DeveloperAssociatedWithProject {

    @Id
    @ManyToOne
    private BBUser developer;
    @Id
    @ManyToOne
    private Project project;
    @Id
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "ended_at")
    private LocalDateTime endedAt;
}
