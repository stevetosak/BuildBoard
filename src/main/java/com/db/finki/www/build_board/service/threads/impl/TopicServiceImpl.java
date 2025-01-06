package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.BBUser;
import com.db.finki.www.build_board.entity.threads.Tag;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.repository.threads.TagRepository;
import com.db.finki.www.build_board.repository.threads.TopicRepository;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
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
    public void create(String title, String description, BBUser user) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(description);
        topic.setUser(user);
        topicRepository.save(topic);
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
    public void addTagToTopic(Topic topic, String tagName) {
        tagRepository.findByName(tagName).ifPresentOrElse(tag -> {
            topic.getTags().add(tag);
            tag.getThreads().add(topic);
            topicRepository.save(topic);
            tagRepository.save(tag);
        },() -> {
            Tag tag = new Tag(tagName);
            tagRepository.save(tag);
            topic.getTags().add(tag);
            tag.getThreads().add(topic);
            topicRepository.save(topic);
        });
    }

    @Override
    public Topic save(long id, String title, String description) {
        Topic t = getById(id);
        t.setTitle(title);
        t.setContent(description);
        return topicRepository.save(t);
    }

    @Override
    @Transactional
    public Topic deleteTagFromTopic(long id, String tagName) {
        Topic t = getById(id);
        boolean removed = t.getTags().remove(new Tag(tagName));
        if(!removed) throw new IllegalArgumentException("Tag not found");
        return topicRepository.save(t);
    }

}
