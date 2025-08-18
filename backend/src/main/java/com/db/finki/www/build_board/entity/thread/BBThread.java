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
    protected int level;
    @ManyToOne()
    @JoinColumn(name = "parent_id")
    protected BBThread parent;
    protected String type;
    protected String status = "active";

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

    protected LocalDateTime createdAt = LocalDateTime.now();

    public int getNumLikes(){
        return likes.size();
    }

    public BBThread(Integer id, String content, int level, BBThread parent, String type, BBUser user, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.level = level;
        this.parent = parent;
        this.type = type;
        this.user = user;
        this.createdAt = createdAt;
    }

    public BBThread(String content, int level, BBThread parent, String type, BBUser user) {
        this.content = content;
        this.level = level;
        this.parent = parent;
        this.type = type;
        this.user = user;
    }
}
