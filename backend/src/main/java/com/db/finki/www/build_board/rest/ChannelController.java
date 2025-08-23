package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.service.channel.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class ChannelController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;

    public ChannelController(MessageService messageService, MessageMapper messageMapper, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat")
    @Transactional
    MessageDTO chatMessage(MessageDTO messageDTO) {
        messageDTO.setSentAt(LocalDateTime.now());
        Message m = messageService.save(messageDTO);
        MessageDTO msgDto = messageMapper.toDTO(m);
        msgDto.setAvatarUrl(m.getSentBy().getAvatarUrl());
        String destination = "/topic/" + messageDTO.getProjectName() + "/" + messageDTO.getChannelName();
        messagingTemplate.convertAndSend(destination,msgDto);
        return msgDto;
    }
}
