package com.db.finki.www.build_board.entity.threads.discussion_threads;

import com.db.finki.www.build_board.entity.threads.BBThread;
import jakarta.persistence.*;

@Entity
@Table(name = "discussion_thread")
public class Discussion extends BBThread {
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private BBThread parent;
}
