package com.db.finki.www.build_board.entity.thread.discussion_thread;

import com.db.finki.www.build_board.entity.thread.BBThread;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "discussion_thread")
@Getter
@Setter
public class Discussion extends BBThread {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private BBThread parent;
}
