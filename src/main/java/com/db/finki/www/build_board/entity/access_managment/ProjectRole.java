package com.db.finki.www.build_board.entity.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.entity.compositeId.ProjectRoleId;
import com.db.finki.www.build_board.entity.thread.Project;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "project_role")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectRole {
    @EmbeddedId
    private ProjectRoleId id;
    @Column(name = "override_type",nullable = false)
    private String overrideType = "EXCLUDE";

    public ProjectRole(ProjectRoleId id) {
        this.id = id;
    }

    public String getName(){
        return id.getName();
    }
    public Project getProject(){
        return id.getProject();
    }
    public ProjectResourcePermissionOverrideType getOverrideType() {
        return overrideType.equals(ProjectResourcePermissionOverrideType.INCLUDE.name()) ?
                ProjectResourcePermissionOverrideType.INCLUDE
                : ProjectResourcePermissionOverrideType.EXCLUDE;
    }
}
