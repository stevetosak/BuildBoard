package com.db.finki.www.build_board.service.util;

import com.db.finki.www.build_board.entity.view.NamedThread;
import com.db.finki.www.build_board.repository.NamedThreadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NamedThreadService {
    private final NamedThreadRepository namedThreadRepository;

    public NamedThreadService(NamedThreadRepository namedThreadRepository) {
        this.namedThreadRepository = namedThreadRepository;
    }

    public List<NamedThread> getAll(
            String title,
            String content,
            String type,
            List<String> tagList
            ) {
        String tag = tagList == null || tagList.isEmpty() ? null : String.join(",",tagList);

        return namedThreadRepository.findAll(
                title,
                type,
                content,
                tag
                                            ) ;
    }
}
