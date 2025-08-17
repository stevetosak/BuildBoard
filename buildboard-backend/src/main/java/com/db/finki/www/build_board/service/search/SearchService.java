package com.db.finki.www.build_board.service.search;

//spored title, content, type

import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchService {
    Page<NamedThread> search(String query, List<String> filters, String type, Pageable page);
}
