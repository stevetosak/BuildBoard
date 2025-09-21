package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.dto.PermissionResourceWrapper;
import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.thread.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddRoleDTOEntities {
    String name;
    Project project;
    List<PermissionResourceWrapper> permissionsResourceWrappers;
    List<Permission> globalPermissions;
    ProjectResourcePermissionOverrideType projectResourcePermissionOverrideType;
}
