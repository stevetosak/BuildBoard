package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.dto.PermissionResourceWrapper;
import com.db.finki.www.build_board.entity.access_managment.*;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionId;
import com.db.finki.www.build_board.entity.compositeId.ProjectRolePermissionResourceOverrideId;
import com.db.finki.www.build_board.entity.compositeId.UsersProjectRolesId;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionResourceOverrideRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRoleRepository;
import com.db.finki.www.build_board.repository.access_managment.ProjectRolePermissionRepository;
import com.db.finki.www.build_board.repository.access_managment.UserProjectRoleRepository;
import com.db.finki.www.build_board.service.channel.ChannelService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ProjectAccessManagementService {

    private final ProjectRoleRepository projectRoleRepository;
    private final ProjectRolePermissionRepository projectRolePermissionRepository;
    private final ProjectRolePermissionResourceOverrideRepository projectRolePermissionResourceOverrideRepository;
    private final UserProjectRoleRepository userProjectRoleRepository;
    private final ChannelService channelService;

    public ProjectAccessManagementService(
            ProjectRoleRepository projectRoleRepository,
            ProjectRolePermissionRepository projectRolePermissionRepository,
            ProjectRolePermissionResourceOverrideRepository projectRolePermissionResourceOverrideRepository, UserProjectRoleRepository userProjectRoleRepository, ChannelService channelService
    ) {
        this.projectRoleRepository = projectRoleRepository;
        this.projectRolePermissionResourceOverrideRepository =
                projectRolePermissionResourceOverrideRepository;
        this.projectRolePermissionRepository = projectRolePermissionRepository;
        this.userProjectRoleRepository = userProjectRoleRepository;
        this.channelService = channelService;
    }

    public boolean hasPermissionToAccessResource(
            int userId, String permission, UUID resourceId,
            Project project
    ) {
        if(project.getUser().getId() == userId) return true;

        if(resourceId == null){
            return projectRolePermissionResourceOverrideRepository.hasGlobalPermission(permission,project.getId(),userId);
        }else {
            return projectRolePermissionResourceOverrideRepository.hasPermissionForResource(project.getId(), userId,permission,resourceId);
        }


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
                        new ProjectRolePermissionId(p, role)
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
            List<Channel> channels,
            List<ProjectRolePermission> rolePermissions

    ) {
        List<ProjectRolePermissionResourceOverride> overrides = new ArrayList<>();


        for (int i = 0; i < channels.size(); i++) {
            overrides.add(
                    new ProjectRolePermissionResourceOverride(
                            new ProjectRolePermissionResourceOverrideId(
                                    rolePermissions.get(i),
                                    channels.get(i)
                            )
                    )
            );
        }

        return overrides;
    }

    private List<Channel> getResources(List<PermissionResourceWrapper> rolePermissions) {
        return rolePermissions.stream().map(PermissionResourceWrapper::getChannel).toList();
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
        if(!role.getName().equals("Admin")) {
            projectRoleRepository.deleteById(role.getId());
        }
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
