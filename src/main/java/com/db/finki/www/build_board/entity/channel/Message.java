package com.db.finki.www.build_board.entity.channel;

import com.db.finki.www.build_board.entity.compositeId.MessageId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.Developer;
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
    @ManyToOne
    @JoinColumn(name = "sent_in",referencedColumnName = "id")
    private Channel channel;

    @Id
    @ManyToOne
    @JoinColumn(name = "sent_by",referencedColumnName = "id",nullable = false)
    private Developer sentBy;

    @Id
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    private String content;
}
