package com.db.finki.www.build_board.entity.request;

import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project_request")
public class ProjectRequests extends Submission {
    @ManyToOne
    @JoinColumn(name = "project_receives")
    private Project project;

    public ProjectRequests(Project project, BBUser creator, String description) {
        setDescription(description);
        setCreator(creator);
        setProject(project);
        setStatus(Status.PENDING);
        setCreatedAt(LocalDateTime.now());
    }
}
