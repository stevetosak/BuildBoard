package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.bb_users.types.Developer;
import com.db.finki.www.build_board.bb_users.types.repos.DeveloperRepository;
import com.db.finki.www.build_board.constants.enums.ChannelMessageEventType;
import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.channel.Message;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.project.ProjectService;
import com.db.finki.www.build_board.rest.dto.ChannelMessageEvent;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.channel.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/projects/{projectName}")
public class ChannelController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChannelService channelService;
    private final ProjectService projectService;
    private final DeveloperRepository developerRepository;
    private final ConcurrentHashMap<String, ScheduledFuture<?>> usernameTypingFutureStore = new ConcurrentHashMap<>();

    public ChannelController(MessageService messageService, MessageMapper messageMapper, SimpMessagingTemplate messagingTemplate, ChannelService channelService, ProjectService projectService, DeveloperRepository developerRepository) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.messagingTemplate = messagingTemplate;
        this.channelService = channelService;
        this.projectService = projectService;
        this.developerRepository = developerRepository;
    }

    @MessageMapping("/chat/send")
    @Transactional
    void sendMessage(MessageDTO messageDTO) {
        Message m = messageService.save(messageDTO);
        MessageDTO msgDto = messageMapper.toDTO(m);
        msgDto.setAvatarUrl(m.getSentBy().getAvatarUrl());
        String destination = "/topic/" + messageDTO.getProjectName() + "/" + messageDTO.getChannelName();

        messagingTemplate.convertAndSend(destination, new ChannelMessageEvent(ChannelMessageEventType.SEND, msgDto));
    }

    @MessageMapping("/chat/edit")
    @Transactional
    void editMessage(MessageDTO messageDTO) {
        Developer d = developerRepository.findByUsername(messageDTO.getSenderUsername());
        Project p = projectService.getByTitle(messageDTO.getProjectName());
        messageService.edit(messageDTO, p.getId(), d.getId());
        String destination = "/topic/" + messageDTO.getProjectName() + "/" + messageDTO.getChannelName();
        messagingTemplate.convertAndSend(destination, new ChannelMessageEvent(ChannelMessageEventType.EDIT, messageDTO));
    }

    @MessageMapping("/chat/delete")
    @Transactional
    void deleteMessage(MessageDTO messageDTO) {
        messageService.delete(messageDTO);
        String destination = "/topic/" + messageDTO.getProjectName() + "/" + messageDTO.getChannelName();
        messagingTemplate.convertAndSend(destination, new ChannelMessageEvent(ChannelMessageEventType.DELETE, messageDTO));
    }

    @MessageMapping("/chat/type")
    void typeNotification(MessageDTO messageDTO) {
        ScheduledFuture<?> storedFuture = usernameTypingFutureStore.get(messageDTO.getSenderUsername());
        if (storedFuture != null) {
            storedFuture.cancel(true);
        }
        String destination = "/topic/" + messageDTO.getProjectName() + "/" + messageDTO.getChannelName();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> future = executor.schedule(() -> {

            messagingTemplate.convertAndSend(destination, new ChannelMessageEvent(ChannelMessageEventType.TYPE_END, messageDTO));
        }, 1, TimeUnit.SECONDS);
        usernameTypingFutureStore.put(messageDTO.getSenderUsername(), future);
        messagingTemplate.convertAndSend(destination, new ChannelMessageEvent(ChannelMessageEventType.TYPE_START, messageDTO));
    }

    //TODO morat da sa odlucime dali ke koristime iminja ko keys ili ids poso izmesano e skros sega, primer kaj message imat projectId a pustame po name
    // i morat za dzabe fetch do baza da pram

    @GetMapping("/channels/{channelName}")
    public List<MessageDTO> getAllMessagesForChannelInProject(@PathVariable String projectName, @PathVariable String channelName) {
        Channel c = channelService.getByNameAndProject(channelName, projectName);
        Project p = projectService.getByTitle(projectName);
        return messageMapper.toDTO(messageService.getAllMessagesForProjectChannel(p.getId(), c.getName()));
    }
}
