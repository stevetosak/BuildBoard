package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.dto.AddRoleDTO;
import com.db.finki.www.build_board.dto.PermissionResourceDTO;
import com.db.finki.www.build_board.dto.PermissionResourceWrapper;
import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.access_managment.ProjectResource;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddRoleDTOEntitiesMapper {
    private final ProjectService projectService;

    public AddRoleDTOEntitiesMapper(ProjectService projectService) {this.projectService = projectService;}

    private Project findProject(String projectTitle){
        return projectService.getByTitle(projectTitle) ;
    }

    private List<PermissionResourceWrapper> findPermissionResource(List<PermissionResourceDTO> resourceDTOS){
        return resourceDTOS.stream().map(d -> new PermissionResourceWrapper(
                new Permission(d.getPermissionName().toUpperCase()),
                new ProjectResource(d.getProjectResourceID())
        )).toList();
    }

    private List<Permission> findGlobalPermissions(List<String> global){
        return global.stream().map(String::toUpperCase).map(Permission::new).toList();
    }

    public AddRoleDTOEntities map(AddRoleDTO dto) {
        AddRoleDTOEntities dto2 = new AddRoleDTOEntities();

        dto2.setName(dto.getName());
        dto2.setProject(findProject(dto.getProjectTitle()));
        dto2.setPermissionsResourceWrappers(findPermissionResource(dto.getPermissionResourceDTOS()));
        dto2.setProjectResourcePermissionOverrideType(ProjectResourcePermissionOverrideType.valueOf(dto.getPermissionOverrideType()));
        dto2.setGlobalPermissions(findGlobalPermissions(dto.getGlobalPermissions()));

        return dto2;
    }
}
