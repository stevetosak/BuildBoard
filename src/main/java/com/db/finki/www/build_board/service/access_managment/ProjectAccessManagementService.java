package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.dto.PermissionResourceWrapper;
import com.db.finki.www.build_board.entity.access_managment.*;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionId;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import com.db.finki.www.build_board.entity.compositeId.UsersProjectRolesId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionResourceOverrideRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRoleRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionRepository;
import com.db.finki.www.build_board.repository.access_managment.UserProjectRoleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<ProjectRole> getRolesForDeveloperInProject(BBUser user, Project project) {
        return userProjectRoleRepository.findByIdRoleProjectIdAndIdUserId(project.getId(), user.getId()).stream().map(UsersProjectRoles::getProjectRole).toList();
    }

    public List<ProjectRole> getRolesForMembersInProject(Project project) {
        return projectRoleRepository.findByProject(project);
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
                        )
                ))
                .toList();
    }

    private List<ProjectRolePermission> mapPerResourceToProjectRolePermissions(
            ProjectRole role,
            List<PermissionResourceWrapper> permissions
    ) {
        return permissions
                .stream()
                .map(p -> new ProjectRolePermission(
                        new ProjectRolePermissionId(
                                p.getPermission(),
                                role)
                ))
                .toList();
    }

    private List<ProjectRolePermissionResourceOverride> mapToResourceOverrides(
            List<ProjectResource> resources,
            List<ProjectRolePermission> rolePermissions

    ) {
        List<ProjectRolePermissionResourceOverride> overrides = new ArrayList<>();

        for (int i = 0; i < resources.size(); i++) {
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
    public void updateRole(Integer id, AddRoleDTOEntities addRoleDTO) {

        ProjectRole existingRole = projectRoleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("bad role id"));
        projectRolePermissionRepository.deleteAllByIdRole(existingRole);

        existingRole.setName(addRoleDTO.getName());
        existingRole.setOverrideType(addRoleDTO.getProjectResourcePermissionOverrideType().toString());

        projectRoleRepository.save(existingRole);

        List<ProjectRolePermission> newGlobalPermissions = mapGlobalsToProjectRolePermissions(existingRole, addRoleDTO.getGlobalPermissions());
        List<ProjectRolePermission> newPerResourcePermissions = mapPerResourceToProjectRolePermissions(existingRole,addRoleDTO.getPermissionsResourceWrappers());
        List<ProjectRolePermissionResourceOverride> resourceOverrides = mapToResourceOverrides(getResources(addRoleDTO.getPermissionsResourceWrappers()),newPerResourcePermissions);


        projectRolePermissionRepository.saveAll(newGlobalPermissions);
        projectRolePermissionRepository.saveAll(newPerResourcePermissions);
        projectRolePermissionResourceOverrideRepository.saveAll(resourceOverrides);

    }

    @Transactional
    public void addRole(AddRoleDTOEntities addRoleDTO) {
        ProjectRole role = projectRoleRepository.save(
                new ProjectRole(
                        addRoleDTO.getProject(),
                        addRoleDTO.getName(),
                        addRoleDTO.getProjectResourcePermissionOverrideType().toString()
                )
        );

        List<ProjectRolePermission> entities = mapPerResourceToProjectRolePermissions(role, addRoleDTO.getPermissionsResourceWrappers());
        projectRolePermissionRepository.saveAll(mapGlobalsToProjectRolePermissions(role, addRoleDTO.getGlobalPermissions()));
        projectRolePermissionRepository.saveAll(entities);
        projectRolePermissionResourceOverrideRepository.saveAll(mapToResourceOverrides(
                getResources(addRoleDTO.getPermissionsResourceWrappers()),
                entities
        ));
    }

    public void deleteRole(ProjectRole role) {
        projectRoleRepository.deleteById(role.getId());
    }

    public void addRolesToUser(BBUser user, List<ProjectRole> roles) {
        List<UsersProjectRoles> usersProjectRoles = roles.stream()
                .map(role -> new UsersProjectRoles(new UsersProjectRolesId(role, user)))
                .toList();

        userProjectRoleRepository.saveAll(usersProjectRoles);


    }

    public void deleteRoleForUser(BBUser user, Project project, String roleName) {
        ProjectRole role = new ProjectRole(project, roleName);
        userProjectRoleRepository.deleteById(new UsersProjectRolesId(role, user));
    }

    public List<ProjectRolePermission> getRolePermissionsForRoleInProject(ProjectRole role) {
        return projectRolePermissionRepository.findAllByIdRole(role);
    }

    public List<ProjectRolePermissionResourceOverride> getResourceOverridesForRole(ProjectRole role) {
        return projectRolePermissionResourceOverrideRepository.findAllByIdProjectRolePermissionIdRole(role);
    }
}
