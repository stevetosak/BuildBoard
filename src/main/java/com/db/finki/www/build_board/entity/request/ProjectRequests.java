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
public class ProjectRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_req_gen")
    @SequenceGenerator(name = "project_req_gen", sequenceName = "project_request_id_seq", initialValue = 1, allocationSize = 1)
    private int id;
    private String description;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BBUser creator;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "submission_id")
    private Feedback feedback;

    public ProjectRequests(Project project, BBUser creator, String description) {
        setDescription(description);
        setCreator(creator);
        setProject(project);
        setStatus(Status.PENDING);
    }
}
