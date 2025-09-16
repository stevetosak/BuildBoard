package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.dto.PermissionResourceWrapper;
import com.db.finki.www.build_board.entity.access_managment.*;
import com.db.finki.www.build_board.entity.compositeId.ProjectRoleId;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionId;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionResourceOverrideRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRoleRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionRepository;
import com.db.finki.www.build_board.repository.access_managment.UserProjectRoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectAccessManagementService {

    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectRolePermissionRepository projectRolePermissionRepository;
    private final ProjectRolePermissionResourceOverrideRepository projectRolePermissionResourceOverrideRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;

    public ProjectAccessManagementService(
            ProjectRoleRepository projectRoleRepository,
            ProjectRolePermissionRepository projectRolePermissionRepository,
            ProjectRolePermissionResourceOverrideRepository projectRolePermissionResourceOverrideRepository, UserProjectRoleRepository userProjectRoleRepository
                                         ) {
        this.projectRoleRepository = projectRoleRepository;
        this.projectRolePermissionResourceOverrideRepository =
                projectRolePermissionResourceOverrideRepository;
        this.projectRolePermissionRepository = projectRolePermissionRepository;
        this.userProjectRoleRepository = userProjectRoleRepository;
    }

    public boolean hasPermissionToAccessResource(
            int userId, String permission, int resourceId,
            int projectId
                                                ) {
        return projectRolePermissionResourceOverrideRepository.hasPermissionForResource(projectId,
                userId,
                permission,
                resourceId);
    }

    public List<ProjectRole> getRolesForMembersInProject(Project project) {
        return projectRoleRepository.findByIdProjectId(project.getId());
    }

    private List<ProjectRolePermission> mapGlobalsToProjectRolePermissions(
            ProjectRole role,
            List<Permission> permissions
                                                                          ) {
        return permissions
                .stream()
                .map(p -> new ProjectRolePermission(
                        new ProjectRolePermissionId(
                                p,
                                role
                        ),
                        ProjectResourcePermissionOverrideType.EXCLUDE.name()
                ))
                .toList();
    }

    private List<ProjectRolePermission> mapPerResourceToProjectRolePermissions(
            ProjectRole role,
            List<PermissionResourceWrapper> permissions,
            ProjectResourcePermissionOverrideType overrideType
                                                                              ) {
        return permissions
                .stream()
                .map(p -> new ProjectRolePermission(
                        new ProjectRolePermissionId(
                                p.getPermission(),
                                role
                        ),
                        overrideType.name()
                ))
                .toList();
    }

    private List<ProjectRolePermissionResourceOverride> mapToResourceOverrides(
            List<ProjectResource> resources,
            List<ProjectRolePermission> rolePermissions

                                                                              ) {
       List<ProjectRolePermissionResourceOverride> overrides = new ArrayList<>();

       for(int i = 0 ; i < resources.size() ; i++){
           overrides.add(
                   new ProjectRolePermissionResourceOverride(
                           new ProjectRolePermissionResourceOverrideId(
                                  rolePermissions.get(i),
                                  resources.get(i)
                           )
                   )
                                                     );
       }

       return overrides;
    }

    private List<ProjectResource> getResources(List<PermissionResourceWrapper> rolePermissions) {
        return rolePermissions.stream().map(PermissionResourceWrapper::getResource).toList();
    }

    @Transactional
    public void addRole(AddRoleDTOEntities addRoleDTO) {
        ProjectRole role = projectRoleRepository.save(
                new ProjectRole(
                        new ProjectRoleId(
                                addRoleDTO.getName(),
                                addRoleDTO.getProject()
                        )
                )
                                                     );

        //GLOBALS
        projectRolePermissionRepository.saveAll(mapGlobalsToProjectRolePermissions(role,
                addRoleDTO.getGlobalPermissions()
                                                                                  ));

        //LOCALS
        //Bitno e po red posle vo role_permissions_overrides da gi klavash
        List<ProjectRolePermission> entities = mapPerResourceToProjectRolePermissions(
                role,
                addRoleDTO.getPermissionResource(),
                addRoleDTO.getProjectResourcePermissionOverrideType()
                                                                                     );
        projectRolePermissionRepository.saveAll(entities);

        //role_permissions_overrides
        projectRolePermissionResourceOverrideRepository.saveAll(mapToResourceOverrides(
                getResources(addRoleDTO.getPermissionResource()),
                entities
                                                                                      ));
    }

    public void deleteByRoleNameAndProjectTitle(Project project, String roleName) {
        projectRoleRepository.deleteById(new ProjectRoleId(roleName,project));
    }
}
