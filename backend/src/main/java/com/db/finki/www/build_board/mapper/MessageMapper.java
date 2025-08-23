package com.db.finki.www.build_board.mapper;

import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.Developer;
import com.db.finki.www.build_board.repository.DeveloperRepository;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
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
        return new MessageDTO(message.getName(),message.getContent(),message.getSentBy().getUsername(),message.getSentAt(),message.getProject().getTitle(),message.getSentBy().getAvatarUrl());
    }

    @Override
    public List<MessageDTO> toDTO(List<Message> messages) {
        return messages.stream().map(this::toDTO).toList();
    }

    @Override
    public Message fromDTO(MessageDTO dto) {
        Developer d = developerRepository.findByUsername(dto.getSenderUsername());
        System.out.println("DEVELOPER," + d);
        Project p =  projectService.getByTitle(dto.getProjectName());
        return new Message(dto.getChannelName(),p,d,dto.getSentAt(), dto.getContent());
    }

    @Override
    public List<Message> fromDTO(List<MessageDTO> dtos) {
        return dtos.stream().map(this::fromDTO).toList();
    }
}
