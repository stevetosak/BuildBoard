package com.db.finki.www.build_board.service.thread.impl;

import com.db.finki.www.build_board.entity.thread.EmbeddableThread;
import com.db.finki.www.build_board.entity.thread.discussion_thread.Discussion;
import com.db.finki.www.build_board.entity.thread.discussion_thread.VDiscussion;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.thread.DiscussionRepository;
import com.db.finki.www.build_board.repository.thread.EmbeddableRepo;
import com.db.finki.www.build_board.repository.thread.VDiscussRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscussionService {
    private final VDiscussRepo vDiscussRepo;
    private final DiscussionRepository discussionRepository;
    private final EmbeddableRepo embeddableRepo;

    public DiscussionService(VDiscussRepo vDiscussRepo, DiscussionRepository discussionRepository, EmbeddableRepo embeddableRepo) {
        this.vDiscussRepo = vDiscussRepo;
        this.discussionRepository = discussionRepository;
        this.embeddableRepo = embeddableRepo;
    }

    public List<VDiscussion> getByTopic(int topicId){
        List<VDiscussion> discussions = vDiscussRepo.findVDiscussionByParentTopicIdOrderByCreatedAtDesc(topicId);
        List<VDiscussion> level0Discussions = new ArrayList<>(); 

        for(VDiscussion dis : discussions){
            if(dis.getDepth()==0){
                level0Discussions.add(dis);
            }else{
                VDiscussion parent = vDiscussRepo.findById((long) dis.getDiscussion().getParent().getId()).get();
                parent.getChildren().add(dis);
            }
        }

        return level0Discussions; 
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

        EmbeddableThread parent = embeddableRepo.findById((long) parentId).get();

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
        discussionRepository.deleteById((long) threadId);
    }

}
