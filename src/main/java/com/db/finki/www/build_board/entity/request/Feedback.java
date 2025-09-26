package com.db.finki.www.build_board.entity.request;

import com.db.finki.www.build_board.entity.entity_enum.FeedbackFor;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    @Id
    @Column(name = "submission_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "submitted_for")
    @MapsId
    private Submission forSubmission;

    private String description;

    @Enumerated(EnumType.STRING)
    private FeedbackFor submissionType;

    @ManyToOne
    @JoinColumn(name = "written_by")
    private BBUser creator;

    private LocalDateTime createdAt;

    public Feedback(FeedbackFor submissionType, BBUser creator, String description, Submission sub) {
        setDescription(description);
        setSubmissionType(submissionType);
        setCreator(creator);
        setForSubmission(sub);
        setCreatedAt(LocalDateTime.now());
    }

    public Feedback(FeedbackFor feedbackFor, BBUser creator, Submission sub) {
        setSubmissionType(feedbackFor);
        setCreator(creator);
        setForSubmission(sub);
        setCreatedAt(LocalDateTime.now());
    }
}
