package com.db.finki.www.build_board.service.access_managment;

import com.db.finki.www.build_board.common.enums.ProjectResourcePermissionOverrideType;
import com.db.finki.www.build_board.dto.AddRoleDTO;
import com.db.finki.www.build_board.dto.PermissionResourceDTO;
import com.db.finki.www.build_board.dto.PermissionResourceWrapper;
import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.repository.channel.ChannelRepository;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AddRoleDTOEntitiesMapper {
    private final ProjectService projectService;
    private final ChannelService channelService;

    public AddRoleDTOEntitiesMapper(ProjectService projectService, ChannelService channelService) {
        this.projectService = projectService;
        this.channelService = channelService;
    }

    private Project findProject(String projectTitle){
        return projectService.getByTitle(projectTitle) ;
    }

    private List<PermissionResourceWrapper> findPermissionResource(List<PermissionResourceDTO> resourceDTOS) {
        List<UUID> channelResourceIds = resourceDTOS.stream()
                .map(PermissionResourceDTO::getChannelId)
                .toList();

        List<Channel> channels = channelService.getAllByIds(channelResourceIds);

        Map<UUID, Channel> channelMap = channels.stream()
                .collect(Collectors.toMap(Channel::getId, Function.identity()));

        return resourceDTOS.stream()
                .map(dto -> new PermissionResourceWrapper(
                        new Permission(dto.getPermissionName().toUpperCase()),
                        channelMap.get(dto.getChannelId())
                ))
                .toList();
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
