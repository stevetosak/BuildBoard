package com.db.finki.www.build_board.controller.channel;

import com.db.finki.www.build_board.dto.MessageDTO;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.service.channel.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ChannelWebSocketController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public ChannelWebSocketController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @MessageMapping("/{projectName}/channels/{channelName}")
    @Transactional
    MessageDTO chatMessage(MessageDTO messageDTO) {
        messageDTO.setSentAt(LocalDateTime.now());
        Message m = messageService.save(messageDTO);
        MessageDTO rabotaj = messageMapper.toDTO(m);
        rabotaj.setAvatarUrl(m.getSentBy().getAvatarUrl());
        return rabotaj;
    }
}
