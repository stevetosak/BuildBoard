package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.dto.AddRoleDTO;
import com.db.finki.www.build_board.dto.MembersPerRoleWrapper;
import com.db.finki.www.build_board.entity.access_managment.ProjectRole;
import com.db.finki.www.build_board.entity.access_managment.ProjectRolePermission;
import com.db.finki.www.build_board.entity.access_managment.UsersProjectRoles;
import com.db.finki.www.build_board.entity.compositeId.ProjectRoleId;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.repository.DeveloperRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionResourceOverrideRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRoleRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionRepository;
import com.db.finki.www.build_board.repository.access_managment.UserProjectRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectAccessManagementService {

    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectRolePermissionRepository projectRolePermissionRepository;
    private final ProjectRolePermissionResourceOverrideRepository projectRolePermissionResourceOverrideRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;

    public ProjectAccessManagementService(ProjectRoleRepository projectRoleRepository,
                                          ProjectRolePermissionRepository projectRolePermissionRepository,
                                          ProjectRolePermissionResourceOverrideRepository projectRolePermissionResourceOverrideRepository, UserProjectRoleRepository userProjectRoleRepository) {
        this.projectRoleRepository = projectRoleRepository;
        this.projectRolePermissionResourceOverrideRepository = projectRolePermissionResourceOverrideRepository;
        this.projectRolePermissionRepository = projectRolePermissionRepository;
        this.userProjectRoleRepository = userProjectRoleRepository;
    }

    public boolean hasPermissionToAccessResource(int userId, String permission, int resourceId, int projectId) {
        return projectRolePermissionResourceOverrideRepository.hasPermissionForResource(projectId,userId, permission, resourceId);
    }

    public List<UsersProjectRoles> getRolesForMembersInProject(Project project){
       return userProjectRoleRepository.findByIdRoleIdProject(project);

    }

    @Transactional
    public void addRole(AddRoleDTO addRoleDTO) {
        ProjectRole projectRole = new ProjectRole(new ProjectRoleId(addRoleDTO.getName(),addRoleDTO.getProject()));
        projectRoleRepository.save(projectRole);

        List<ProjectRolePermission> projectRolePermissions = addRoleDTO
                        .getPermissions()
                        .stream()
                        .map(permission ->
                                new ProjectRolePermission(new ProjectRolePermissionId(permission,projectRole),
                                        addRoleDTO.getProjectResourcePermissionOverrideType().name())).toList();

        projectRolePermissionRepository.saveAll(projectRolePermissions);

    }
}
