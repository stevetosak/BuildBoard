package com.db.finki.www.build_board.controller.channel;

import com.db.finki.www.build_board.dto.MessageDTO;
import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.service.access_managment.ProjectAccessManagementService;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.channel.MessageService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChannelWebSocketController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ProjectAccessManagementService projectAccessManagementService;
    private final BBUserDetailsService bbUserDetailsService;
    private final ChannelService channelService;
    private final ProjectService projectService;

    public ChannelWebSocketController(MessageService messageService, MessageMapper messageMapper, ProjectAccessManagementService projectAccessManagementService, BBUserDetailsService bbUserDetailsService, ChannelService channelService, ProjectService projectService) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.projectAccessManagementService = projectAccessManagementService;
        this.bbUserDetailsService = bbUserDetailsService;
        this.channelService = channelService;
        this.projectService = projectService;
    }

    @MessageMapping("/{projectName}/channels/{channelName}")
    @Transactional
    MessageDTO chatMessage(MessageDTO messageDTO) {
        BBUser user = (BBUser) bbUserDetailsService.loadUserByUsername(messageDTO.getSenderUsername());
        Project project = projectService.getById(Long.valueOf(messageDTO.getProjectId()));
        Channel channel = channelService.getByNameAndProject(messageDTO.getChannelName(), project);
        if(!projectAccessManagementService.hasPermissionToAccessResource(user.getId(), Permission.WRITE,channel.getId(),project)){
            return null;
        }

        messageDTO.setSentAt(LocalDateTime.now());
        Message m = messageService.addMessage(messageDTO);
        MessageDTO newMessage = messageMapper.toDTO(m);
        newMessage.setAvatarUrl(m.getSentBy().getAvatarUrl());
        return newMessage;
    }
}
