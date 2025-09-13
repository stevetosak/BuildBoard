package com.db.finki.www.build_board.entity.blacklisted_user;

import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.user_type.Moderator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@IdClass(BlacklistedUserId.class)
public class BlacklistedUser {
    @ManyToOne
    @Id
    @JoinColumn(name = "topic_id")
    Topic topic;

    @ManyToOne
    @Id
    @JoinColumn(name = "moderator_id")
    Moderator moderator;

    @Id
    @Column(name = "start_date")
    LocalDateTime startTime;

    @ManyToOne
    @Id
    @JoinColumn(name = "user_id")
    BBUser refersTo;

    String reason;

    @Column(name = "end_date")
    LocalDateTime endTime;

    public BlacklistedUser(
            Topic topic,
            Moderator moderator,
            LocalDateTime startTime,
            BBUser refersTo,
            String reason
                          ) {
        setTopic(topic);
        setModerator(moderator);
        setStartTime(startTime);
        setRefersTo(refersTo);
        setReason(reason);
    }

    public BlacklistedUser(){}
}
