package com.db.finki.www.build_board.entity.thread;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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
    @JoinColumn(name="is_created_by")
    protected BBUser user;

    @ManyToMany
    @JoinTable(
            name = "tag_assigned_to_thread",
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

    private LocalDateTime createdAt = LocalDateTime.now();

    public int getNumLikes(){
        return likes.size();
    }
}
