package com.db.finki.www.build_board.service.thread.impl;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.repository.thread.TagRepository;
import com.db.finki.www.build_board.repository.thread.TopicRepository;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;

    public TopicServiceImpl(TopicRepository topicRepository, TagRepository tagRepository) {
        this.topicRepository = topicRepository;
        this.tagRepository = tagRepository;
    }


    @Override
    public Topic create(String title, String description, BBUser user) {
        Topic topic = new Topic();
        
        topic.setTitle(title);
        topic.setContent(description);
        topic.setUser(user);
        
        return topicRepository.save(topic);
    }

    public Topic create(String title, String description, BBUser user, Project parent){
        Topic topic = new Topic();
        
        topic.setTitle(title);
        topic.setContent(description);
        topic.setUser(user);
        topic.setParent(parent);

        return topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getByTitle(String title) {
        return topicRepository.findByTitle(title);
    }

    @Override
    public void deleteTopicById(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void deleteTopicByTitle(String title) {
        topicRepository.deleteByTitle(title);
    }

    @Override
    public Topic getById(Long id) {
        return topicRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addTagToTopic(Topic topic, String tagName, BBUser user) {
        tagRepository.findByName(tagName).ifPresentOrElse(tag -> {
            topic.getTags().add(tag);
            tag.getThreads().add(topic);
            topicRepository.save(topic);
            tagRepository.save(tag);
        },() -> {
            Tag tag = new Tag(tagName,user);
            tagRepository.save(tag);
            topic.getTags().add(tag);
            tag.getThreads().add(topic);
            topicRepository.save(topic);
        });
    }

    @Override
    public Topic edit(Topic t, String title, String description) {
        t.setTitle(title);
        t.setContent(description);
        return topicRepository.save(t);
    }

    @Override
    @Transactional
    public Topic deleteTagFromTopic(long id, String tagName) {
        Topic t = getById(id);
        boolean removed = t.getTags().removeIf(tag -> tag.getName().equals(tagName));
        if(!removed) throw new IllegalArgumentException("Tag not found");
        return topicRepository.save(t);
    }

}
