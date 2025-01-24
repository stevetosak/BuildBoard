package com.db.finki.www.build_board.entity.requests;

import com.db.finki.www.build_board.entity.Status;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "project_request")
public class ProjectRequests {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_req_gen")
    @SequenceGenerator(name = "project_req_gen", sequenceName = "project_request_id_seq", initialValue = 1, allocationSize = 1)
    private Integer id;
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id")
    Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    BBUser creator;

    @Enumerated(EnumType.STRING)
    private Status status;
}
