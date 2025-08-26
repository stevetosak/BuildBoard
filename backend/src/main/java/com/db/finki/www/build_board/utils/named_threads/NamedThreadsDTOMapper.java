package com.db.finki.www.build_board.utils.named_threads;

import com.db.finki.www.build_board.namedThread.NamedThread;
import com.db.finki.www.build_board.namedThread.NamedThreadDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class NamedThreadsDTOMapper {

    private NamedThreadDTO.NamedThreadDTOBuilder builder = NamedThreadDTO.builder();

    private String truncateContent(String content) {
        double percentageToDisplayOnHomePage = .6;
        int shortDescriptionLength = (int) (content.length() * percentageToDisplayOnHomePage);
        return content.substring(0,
                shortDescriptionLength);
    }

    public Page<NamedThreadDTO> map(Page<NamedThread> namedThreads) {
        return
                namedThreads.map(namedThread -> builder
                        .createdAt(namedThread.getCreatedAt())
                        .threadType(namedThread.getType())
                        .creator(new NamedThreadDTO.Creator(
                                namedThread.getUsername(),
                                namedThread.getUsersAvatarUrl()
                        ))
                        .content(new NamedThreadDTO.Content(namedThread.getTitle(),
                                truncateContent(namedThread.getContent()),
                                namedThread.getTags()
                        ))
                        .build());
    }
}
