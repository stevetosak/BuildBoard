package com.db.finki.www.build_board.rest.dto;

import com.db.finki.www.build_board.entity.thread.BBThread;
import com.db.finki.www.build_board.entity.thread.ThreadView;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Getter
@Setter
public class ThreadDto {
    @JsonProperty
    int id;
    @JsonProperty
    String content;
    @JsonProperty
    UserDto user;
    @JsonProperty
    Timestamp createdAt;
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

    public ThreadDto(int id, String content, UserDto user, Timestamp createdAt, int level, String type, int numLikes, int numReplies, Integer parentId) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
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

    public static ThreadDto from(BBThread thread) {
        return new ThreadDto(
                thread.getId(),
                thread.getContent(),
                new UserDto(thread.getUser().getId(),
                        thread.getUser().getUsername(),
                        thread.getUser().getAvatarUrl()),
                Timestamp.from(thread.getCreatedAt().toInstant(ZoneOffset.of("UTC+2"))),
                thread.getLevel(), thread.getType(), 0, 0,
                thread.getParent().getId());
    }


}
