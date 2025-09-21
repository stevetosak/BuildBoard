package com.db.finki.www.build_board.entity.channel;

import com.db.finki.www.build_board.entity.access_managment.ProjectResource;
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

    @ManyToOne
    @JoinColumn(name = "project_resource_id",referencedColumnName = "id")
    private ProjectResource projectResource;

    public Channel(String name, Project project, String description, Developer developer) {
        this.name = name;
        this.project = project;
        this.description = description;
        this.developer = developer;
    }
}
