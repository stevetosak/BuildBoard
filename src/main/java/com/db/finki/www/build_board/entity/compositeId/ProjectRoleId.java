package com.db.finki.www.build_board.entity.compositeId;

import com.db.finki.www.build_board.entity.thread.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRoleId {
    String name;
    @ManyToOne
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    Project project;
}
