package com.db.finki.www.build_board.entity.threads;

import com.db.finki.www.build_board.entity.BBUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    protected List<Tag> tags;

    public Integer getId() {return id;}

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setUser(BBUser user) {
        this.user = user;
    }
}
