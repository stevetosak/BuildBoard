package com.db.finki.www.build_board.dto;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.thread.Project;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddRoleDTO {
    String name;
    Project project;
    List<Permission> permissions;
    ProjectResourcePermissionOverrideType projectResourcePermissionOverrideType;
}
