package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "thread")
@Inheritance(strategy = InheritanceType.JOINED)
public class BBThread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    protected List<Tag> tags = new ArrayList<>();
}
