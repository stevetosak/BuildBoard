package com.db.finki.www.build_board.entity.thread.discussion_thread;


import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name="v_discussion_thread")
@Getter
@Setter
@NoArgsConstructor
public class VDiscussion {
    @Id
    private Integer id;

    @MapsId
    @OneToOne
    @JoinColumn(name="id")
    private Discussion discussion;

    private int depth;

    @ManyToOne
    @JoinColumn(name="user_id")
    private BBUser user;

    public String getAvatarUrl(){
        return user.getAvatarUrl();
    }

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Topic parentTopic;

    private LocalDateTime createdAt;
}
