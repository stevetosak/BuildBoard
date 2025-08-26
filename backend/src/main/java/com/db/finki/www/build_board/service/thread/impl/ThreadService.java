package com.db.finki.www.build_board.service.thread.impl;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.ThreadView;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.bb_users.BBUser;
import com.db.finki.www.build_board.bb_users.types.repos.UserRepository;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import com.db.finki.www.build_board.repository.thread.DiscussionRepository;
import com.db.finki.www.build_board.repository.thread.TopicRepository;
import com.db.finki.www.build_board.rest.dto.ThreadDto;
import com.db.finki.www.build_board.rest.dto.TopicDto;
import com.db.finki.www.build_board.rest.dto.ThreadTreeResponse;
import com.db.finki.www.build_board.rest.dto.UserDto;
import com.db.finki.www.build_board.bb_users.BBUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThreadService {
    private final DiscussionRepository discussionRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final BBThreadRepository threadRepository;
    private final BBUserDetailsService userDetailsService;

    public ThreadService(DiscussionRepository discussionRepository, TopicRepository topicRepository, UserRepository userRepository, BBThreadRepository threadRepository, BBUserDetailsService userDetailsService) {
        this.discussionRepository = discussionRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.threadRepository = threadRepository;
        this.userDetailsService = userDetailsService;
    }

    private List<ThreadDto> mapToDto(List<ThreadView> discussionTree,int skipNum) {
        return discussionTree.stream().skip(skipNum).map(thread -> new ThreadDto(thread.getId(), thread.getContent(), new UserDto(thread.getUserId(), thread.getUsername(),
                "todo util funkcija getAvatarUrl ne vo entity"), thread.getCreatedAt(),
                thread.getLevel(), thread.getType(), thread.getNumLikes(),
                thread.getNumReplies(),thread.getParentId(),thread.getStatus())).toList();
    }

    public ThreadTreeResponse getTopicResponse(Topic topic, int page, int size) {
        List<ThreadView> discussionTree = threadRepository.getThreadTree(topic.getId(), page, size);
        ThreadView topicView = discussionTree.get(0);
        TopicDto topicDto = new TopicDto(topicView.getId(), topicView.getContent(), new UserDto(topicView.getUserId(), topicView.getUsername(),
                "todo util funkcija getAvatarUrl ne vo entity"), topicView.getCreatedAt(),
                topicView.getLevel(), topicView.getType(), topicView.getNumLikes(),
                topicView.getNumReplies(),topic.getTitle(),null,topicView.getStatus());

        List<ThreadDto> dtos = mapToDto(discussionTree,1);
        return new ThreadTreeResponse(topicDto,dtos,"topic");

    }
    public ThreadTreeResponse getRepliesResponse(int threadId, int page, int size) {
        List<ThreadView> discussionTree = threadRepository.getThreadTree(threadId, page, size);
        List<ThreadDto> dtos = mapToDto(discussionTree,0);
        return new ThreadTreeResponse(dtos.get(0),dtos,"discussion");
    }

    public ThreadDto addReply(BBUser principal, ThreadDto threadDto) {
        BBThread parent = threadDto.getParentId() == null ? null : threadRepository.findById(threadDto.getParentId());
        BBThread saved = threadRepository.save(new BBThread(threadDto.getContent(),threadDto.getLevel(),parent,"discussion",principal));
        return ThreadDto.from(saved);
    }
    public void deleteThread(int id){
        BBThread thread = threadRepository.findById(id);
        thread.setContent("DELETED");
        thread.setStatus("deleted");
        threadRepository.save(thread);
    }
}
