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
public class BlacklistedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "blacklisted_user_id_gen")
    @SequenceGenerator(name = "blacklisted_user_id_gen",sequenceName = "blacklisted_user_id_seq",allocationSize=1)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    Topic topic;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    Moderator moderator;

    @Column(name = "start_date")
    LocalDateTime startTime;

    @ManyToOne
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
