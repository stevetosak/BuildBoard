package com.db.finki.www.build_board.entity.thread.discussion_thread;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.EmbdedableThread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "discussion_thread")
@Getter
@Setter
public class Discussion extends EmbdedableThread {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private EmbdedableThread parent;
}
