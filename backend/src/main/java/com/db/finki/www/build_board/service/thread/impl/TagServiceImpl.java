package com.db.finki.www.build_board.service.thread.impl;

import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.bb_users.BBUser;
import com.db.finki.www.build_board.repository.thread.TagRepository;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.findAll();
    }

    public Tag create(String tagName, BBUser user) {
        return tagRepository.save(new Tag(tagName, user));
    }

    @Override
    public List<Tag> getAllNotUsed(Topic t) {
        return tagRepository.findAll().stream().filter(tag -> !t.getTags().contains(tag)).toList();
    }
}
