package com.db.finki.www.build_board.entity.threads.discussion_threads;

import com.db.finki.www.build_board.entity.threads.BBThread;
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
