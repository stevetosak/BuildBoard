package com.db.finki.www.build_board.namedThread;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final NamedThreadRepository namedThreadRepository;

    public SearchService(NamedThreadRepository namedThreadRepository) {this.namedThreadRepository = namedThreadRepository;}

    public Page<NamedThread> search(
            String title,
            String content,
            String type,
            List<String> tagList,
            Pageable pageable,
            Integer parentId) {
       String tag = tagList == null || tagList.isEmpty() ? null : String.join(",",tagList);

       return namedThreadRepository.findAll(
               pageable,
               title,
               type,
               content,
               tag,
               parentId
        ) ;
    }
}
