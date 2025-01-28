package com.db.finki.www.build_board.controller.channels;

import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.db.finki.www.build_board.entity.channels.Message;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.mappers.MessageMapper;
import com.db.finki.www.build_board.service.channel.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

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
        Message m = messageService.saveMessage(messageDTO);
        MessageDTO rabotaj = messageMapper.toDTO(m);
        rabotaj.setAvatarUrl(m.getSentBy().getAvatarUrl());
        return rabotaj;
    }
}
