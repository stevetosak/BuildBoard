package com.db.finki.www.build_board.entity.channels;

import com.db.finki.www.build_board.entity.compositeId.MessageId;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.Developer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "messages")
@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(MessageId.class)
@AllArgsConstructor
public class Message {
    @Id
    @Column(name = "channel_name")
    private String name;
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id",referencedColumnName = "id",nullable = false)
    private Project project;
    @Id
    @ManyToOne
    @JoinColumn(name = "sent_by",referencedColumnName = "id",nullable = false)
    private Developer sentBy;
    @Id
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    private String content;
}
