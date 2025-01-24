package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "thread")
@Inheritance(strategy = InheritanceType.JOINED)
public class BBThread {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thread_gen")
    @SequenceGenerator(name = "thread_gen", sequenceName = "thread_id_seq", allocationSize = 1)
    protected Integer id;

    protected String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    protected BBUser user;

    @ManyToMany
    @JoinTable(
            name = "tag_threads",
            joinColumns = @JoinColumn(name = "thread_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    protected Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "thread_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    protected Set<BBUser> likes = new HashSet<>();

    public int getNumLikes(){
        return likes.size();
    }
}
