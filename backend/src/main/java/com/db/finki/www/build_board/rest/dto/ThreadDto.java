package com.db.finki.www.build_board.rest.dto;

import com.db.finki.www.build_board.entity.thread.ThreadView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class ThreadDto {
    @JsonProperty
    int id;
    @JsonProperty
    String content;
    @JsonProperty
    UserDto user;
    @JsonProperty
    LocalDateTime createdAt;
    @JsonProperty
    int level;
    @JsonProperty
    String type;
    @JsonProperty
    int numLikes;
    @JsonProperty
    int numReplies;
    @JsonProperty
    Integer parentId;

    public ThreadDto(int id, String content, UserDto user, Timestamp createdAt, int level, String type, int numLikes, int numReplies,Integer parentId) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt.toLocalDateTime();
        this.level = level;
        this.type = type;
        this.numLikes = numLikes;
        this.numReplies = numReplies;
        this.parentId = parentId;
    }

    public ThreadDto(ThreadDto threadDto) {
        this.id = threadDto.id;
        this.content = threadDto.content;
        this.user = threadDto.user;
        this.createdAt = threadDto.createdAt;
        this.level = threadDto.level;
        this.type = threadDto.type;
        this.numLikes = threadDto.numLikes;
        this.numReplies = threadDto.numReplies;
        this.parentId = threadDto.parentId;
    }


}
