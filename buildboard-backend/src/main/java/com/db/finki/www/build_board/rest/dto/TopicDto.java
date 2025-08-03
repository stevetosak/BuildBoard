package com.db.finki.www.build_board.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    int id;
    String title;
    String content;
    UserDto user;
    String createdAt;
    int level;
}
