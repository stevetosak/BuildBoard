package com.db.finki.www.build_board.service.search;

//spored title, content, type

import com.db.finki.www.build_board.entity.thread.itf.NamedThread;

import java.util.List;

public interface SearchService {
    List<NamedThread> search(String query, List<String> filters, String type);
}
