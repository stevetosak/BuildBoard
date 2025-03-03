package com.db.finki.www.build_board.entity.thread.discussion_thread;

import com.db.finki.www.build_board.entity.thread.EmbeddableThread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "discussion_thread")
@Getter
@Setter
public class Discussion extends EmbeddableThread {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EmbeddableThread parent;
}
