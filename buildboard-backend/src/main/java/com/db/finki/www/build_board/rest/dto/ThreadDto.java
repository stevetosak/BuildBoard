package com.db.finki.www.build_board.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ThreadDto {
    @JsonProperty
    int id;
    @JsonProperty
    String title = null;
    @JsonProperty
    String content;
    @JsonProperty
    UserDto user;
    @JsonProperty
    String createdAt;
    @JsonProperty
    int level;
    public ThreadDto(int id, String content, UserDto user, int level,String createdAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.level = level;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ThreadDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", createdAt='" + createdAt + '\'' +
                ", level=" + level +
                '}';
    }
}
