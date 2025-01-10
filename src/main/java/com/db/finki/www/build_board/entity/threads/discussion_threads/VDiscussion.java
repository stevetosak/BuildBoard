package com.db.finki.www.build_board.entity.threads.discussion_threads;


import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.entity.threads.discussion_threads.Discussion;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

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
}
