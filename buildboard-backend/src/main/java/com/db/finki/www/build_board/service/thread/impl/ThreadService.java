package com.db.finki.www.build_board.service.thread.impl;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.discussion_thread.Discussion;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.repository.thread.DiscussionRepository;
import com.db.finki.www.build_board.repository.thread.TopicRepository;
import com.db.finki.www.build_board.rest.dto.DiscussionThreadDto;
import com.db.finki.www.build_board.rest.dto.ThreadDto;
import com.db.finki.www.build_board.rest.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThreadService {
    private final DiscussionRepository discussionRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public ThreadService(DiscussionRepository discussionRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.discussionRepository = discussionRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public List<ThreadDto> getDirectRepliesForThread(BBThread parent) {
        return discussionRepository.findAllByParentIdAndLevel(parent.getId(), parent.getLevel() + 1).orElse(List.of())
                .stream()
                .map(disc -> new ThreadDto(
                        disc.getId(),
                        disc.getContent(),
                        new UserDto(
                                disc.getUser().getId(),
                                disc.getUser().getUsername(),
                                disc.getUser().getAvatarUrl()
                        ),
                        disc.getLevel(),
                        disc.getCreatedAt().toString()
                )).collect(Collectors.toList());

    }
}
