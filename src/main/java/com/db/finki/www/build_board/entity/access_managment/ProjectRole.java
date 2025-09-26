package com.db.finki.www.build_board.entity.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.entity.thread.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Table(name = "project_role")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_role_id_gen")
    @SequenceGenerator(name = "project_role_id_gen",sequenceName = "project_role_id_seq",allocationSize=1)
    private Integer id;

    String name;

    @ManyToOne
    @JoinColumn(name = "valid_in",referencedColumnName = "id")
    Project project;

    @Column(name = "override_type",nullable = false)
    private String overrideType = "EXCLUDE";

    public ProjectRole(Project project, String name) {
        this.project = project;
        this.name = name;
    }
    public ProjectRole(Project project, String name, String overrideType) {
        this.project = project;
        this.name = name;
        this.overrideType = overrideType;
    }

    public ProjectResourcePermissionOverrideType getOverrideType() {
        return overrideType.equals(ProjectResourcePermissionOverrideType.INCLUDE.name()) ?
                ProjectResourcePermissionOverrideType.INCLUDE
                : ProjectResourcePermissionOverrideType.EXCLUDE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProjectRole that = (ProjectRole) o;
        return Objects.equals(name, that.name) && Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, project);
    }
}
