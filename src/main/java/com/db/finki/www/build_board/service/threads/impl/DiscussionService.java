package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.entity.threads.discussion_threads.Discussion;
import com.db.finki.www.build_board.entity.threads.discussion_threads.VDiscussion;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.repository.threads.BBThreadRepository;
import com.db.finki.www.build_board.repository.threads.DiscussionRepository;
import com.db.finki.www.build_board.repository.threads.VDiscussRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiscussionService {
    private final VDiscussRepo vDiscussRepo;
    private final DiscussionRepository discussionRepository;
    private final BBThreadRepository threadRepository;

    public DiscussionService(VDiscussRepo vDiscussRepo, DiscussionRepository discussionRepository, BBThreadRepository threadRepository) {
        this.vDiscussRepo = vDiscussRepo;
        this.discussionRepository = discussionRepository;
        this.threadRepository = threadRepository;
    }

    public List<VDiscussion> findByTopic(int topicId){
        return vDiscussRepo.findVDiscussionByParentTopicId(topicId);
    }

    public VDiscussion findVDiscussionById(int discussionId){
        return vDiscussRepo.findVDiscussionByDiscussionId(discussionId);
    }
    public Discussion findDiscussionById(int discussionId){
        return discussionRepository.findDiscussionById(discussionId);
    }

    public List<VDiscussion> findAll(){
        return vDiscussRepo.findAll();
    }

    @Transactional
    public Discussion create(String content, int parentId, BBUser user){

        BBThread parent = threadRepository.findById(parentId);

        Discussion reply = new Discussion();
        reply.setContent(content);
        reply.setUser(user);
        reply.setParent(parent);

        return discussionRepository.save(reply);
    }

    public Discussion edit(int replyId, String content) {
        Discussion reply = discussionRepository.findDiscussionById(replyId);
        reply.setContent(content);
        return discussionRepository.save(reply);
    }

    public void delete(int threadId) {
        Discussion d = discussionRepository.findDiscussionById(threadId);
        discussionRepository.delete(d);
    }
}
