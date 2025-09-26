package com.db.finki.www.build_board.entity.channel;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.Developer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "channel")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Channel {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "project_has",referencedColumnName = "id",nullable = false)
    private Project project;

    private String description;

    @ManyToOne
    @JoinColumn(name = "constructed_by",referencedColumnName = "id",nullable = false)
    private Developer developer;


    public Channel(String name, Project project, String description, Developer developer) {
        this.name = name;
        this.project = project;
        this.description = description;
        this.developer = developer;
    }
}
