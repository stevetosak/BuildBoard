package com.db.finki.www.build_board.service.thread.impl;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.discussion_thread.Discussion;
import com.db.finki.www.build_board.entity.thread.discussion_thread.VDiscussion;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.thread.BBThreadRepository;
import com.db.finki.www.build_board.repository.thread.DiscussionRepository;
import com.db.finki.www.build_board.repository.thread.VDiscussRepo;
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

    public List<VDiscussion> getByTopic(int topicId){
        return vDiscussRepo.findVDiscussionByParentTopicIdOrderByCreatedAtDesc(topicId);
    }

    public VDiscussion getVDiscussionById(int discussionId){
        return vDiscussRepo.findVDiscussionByDiscussionId(discussionId);
    }
    public Discussion getDiscussionById(int discussionId){
        return discussionRepository.findDiscussionById(discussionId);
    }

    public List<VDiscussion> getAll(){
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
