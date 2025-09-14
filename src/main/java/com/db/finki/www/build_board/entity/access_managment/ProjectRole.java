package com.db.finki.www.build_board.entity.access_managment;

import com.db.finki.www.build_board.entity.compositeId.ProjectRoleId;
import com.db.finki.www.build_board.entity.thread.Project;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "project_role")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRole {
    @EmbeddedId
    private ProjectRoleId id;

    public String getName(){
        return id.getName();
    }
    public Project getProject(){
        return id.getProject();
    }
}
