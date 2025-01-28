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
    Integer id;

    private String description;

    @Enumerated(EnumType.STRING)
    private FeedbackFor submissionType;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private BBUser creator;

    private LocalDateTime createdAt;

    public Feedback(FeedbackFor submissionType, BBUser creator, String description, Integer subId) {
        setDescription(description);
        setSubmissionType(submissionType);
        setCreator(creator);
        setId(subId);
        setCreatedAt(LocalDateTime.now());
    }

    public Feedback(FeedbackFor feedbackFor, BBUser creator, Integer subId) {
        setSubmissionType(feedbackFor);
        setCreator(creator);
        setId(subId);
        setCreatedAt(LocalDateTime.now());
    }
}
