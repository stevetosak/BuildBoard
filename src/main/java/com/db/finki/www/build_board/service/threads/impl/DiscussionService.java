package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.threads.discussion_threads.Discussion;
import com.db.finki.www.build_board.entity.threads.discussion_threads.VDiscussion;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.repository.threads.VDiscussRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DiscussionService {
    private final VDiscussRepo vDiscussRepo;

    public DiscussionService(VDiscussRepo vDiscussRepo) {
        this.vDiscussRepo = vDiscussRepo;
    }

    public List<VDiscussion> findByTopic(Long topicId){
        return vDiscussRepo.findVDiscussionByParentTopicId(topicId);
    }

    public List<VDiscussion> findAll(){
        return vDiscussRepo.findAll();
    }
    public Discussion create(String content, Long parentId, BBUser user){
        return null;
    }
}
