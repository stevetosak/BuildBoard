package com.db.finki.www.build_board.service.channel;

import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.repository.channel.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public List<Message> getAllMessagesForProjectChannel(Integer projectId,String channelName) {
        return messageRepository.findAllByNameAndProjectIdOrderBySentAtAsc(channelName,projectId);
    }
    public Message save(MessageDTO messageDTO) {
        Message message = messageMapper.fromDTO(messageDTO);
        return messageRepository.save(message);
    }

}
