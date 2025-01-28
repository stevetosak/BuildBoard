package com.db.finki.www.build_board.mappers;

import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.db.finki.www.build_board.entity.channels.Message;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.Developer;
import com.db.finki.www.build_board.repository.DeveloperRepository;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.repository.thread.ProjectRepository;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageMapper implements Mapper<Message,MessageDTO>{

    private final ProjectService projectService;
    private final DeveloperRepository developerRepository;

    public MessageMapper(ProjectService projectService, DeveloperRepository developerRepository) {
        this.projectService = projectService;
        this.developerRepository = developerRepository;
    }

    @Override
    public MessageDTO toDTO(Message message) {
        return new MessageDTO(message.getName(),message.getContent(),message.getSentBy().getUsername(),message.getSentAt(),message.getProject().getId(),message.getSentBy().getAvatarUrl());
    }

    @Override
    public List<MessageDTO> toDTO(List<Message> messages) {
        return messages.stream().map(this::toDTO).toList();
    }

    @Override
    public Message fromDTO(MessageDTO dto) {
        Developer d =  developerRepository.findByUsername(dto.getSenderUsername());
        Project p =  projectService.findById((long)dto.getProjectId());
        return new Message(dto.getChannelName(),p,d,dto.getSentAt(), dto.getContent());
    }

    @Override
    public List<Message> fromDTO(List<MessageDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
