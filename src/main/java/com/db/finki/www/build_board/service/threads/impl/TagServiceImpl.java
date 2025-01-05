package com.db.finki.www.build_board.service.threads.impl;

import com.db.finki.www.build_board.entity.threads.Tag;
import com.db.finki.www.build_board.repository.threads.TagRepository;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
