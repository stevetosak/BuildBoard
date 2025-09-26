package com.db.finki.www.build_board.entity.request;

import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "report")
public class Report extends Submission {
    @ManyToOne
    @JoinColumn(name = "about")
    BBUser user;

    @ManyToOne
    @JoinColumn(name = "for_misconduct_in")
    Topic topic;

    public Report(Topic topic, BBUser creator, String description, BBUser misconductedUser) {
        setDescription(description);
        setCreator(creator);
        setTopic(topic);
        setStatus(Status.PENDING);
        setCreatedAt(LocalDateTime.now());
        setUser(misconductedUser);
    }
}
