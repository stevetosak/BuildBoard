package com.db.finki.www.build_board.service.threads.itfs;

import com.db.finki.www.build_board.entity.threads.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TagService {
    Tag findByName(String name);
    List<Tag> findAll();
    Tag create(String name);
}
