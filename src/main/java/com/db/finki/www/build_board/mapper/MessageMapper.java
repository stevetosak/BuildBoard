package com.db.finki.www.build_board.mapper;

import com.db.finki.www.build_board.dto.MessageDTO;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.Developer;
import com.db.finki.www.build_board.repository.DeveloperRepository;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper implements Mapper<Message,MessageDTO>{

    private final ProjectService projectService;
    private final DeveloperRepository developerRepository;
    private final ChannelService channelService;

    public MessageMapper(ProjectService projectService, DeveloperRepository developerRepository, ChannelService channelService) {
        this.projectService = projectService;
        this.developerRepository = developerRepository;
        this.channelService = channelService;
    }

    @Override
    public MessageDTO toDTO(Message message) {
        return new MessageDTO(message.getChannel().getName(),message.getContent(),message.getSentBy().getUsername(),message.getSentAt(),message.getChannel().getProject().getId(),message.getSentBy().getAvatarUrl());
    }

    @Override
    public List<MessageDTO> toDTO(List<Message> messages) {
        return messages.stream().map(this::toDTO).toList();
    }

    @Override
    public Message fromDTO(MessageDTO dto) {
        Developer d =  developerRepository.findByUsername(dto.getSenderUsername());
        Project p =  projectService.getById((long)dto.getProjectId());
        Channel c = channelService.getByNameAndProject(dto.getChannelName(),p);

        return new Message(c,d,dto.getSentAt(), dto.getContent());
    }

    @Override
    public List<Message> fromDTO(List<MessageDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
