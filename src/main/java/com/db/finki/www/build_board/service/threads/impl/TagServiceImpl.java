package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.threads.Tag;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.repository.threads.TagRepository;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name).orElseThrow(() -> new IllegalArgumentException("Tag not found"));
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findAllNotUsed(Topic t) {
        return tagRepository.findAll().stream().filter(tag -> !t.getTags().contains(tag)).toList();
    }
}
