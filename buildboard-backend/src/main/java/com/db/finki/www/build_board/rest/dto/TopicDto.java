package com.db.finki.www.build_board.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto extends ThreadDto{
    String title;

    public TopicDto(int id, String content, UserDto user, Timestamp createdAt, int level, String type, int numLikes, int numReplies, String title,Integer parentId) {
        super(id, content, user, createdAt, level, type, numLikes, numReplies,parentId);
        this.title = title;
    }

    public TopicDto(ThreadDto threadDto, String title) {
        super(threadDto);
        this.title = title;
    }
}
