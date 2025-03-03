package com.db.finki.www.build_board.entity.channel;

import com.db.finki.www.build_board.entity.compositeId.ChannelId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.Developer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "channel")
@Entity
@Getter
@Setter
@IdClass(ChannelId.class)
@AllArgsConstructor
@NoArgsConstructor
public class Channel {

    @Id
    private String name;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "project_id",referencedColumnName = "id",nullable = false)
    private Project project;

    private String description;

    @ManyToOne
    @JoinColumn(name = "developer_id",referencedColumnName = "id",nullable = false)
    private Developer developer;
}
