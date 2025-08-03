package com.db.finki.www.build_board.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class TopicResponseDto {
    @JsonProperty
    ThreadDto topic;
    @JsonProperty
    List<ThreadDto> replies;

    @Override
    public String toString() {
        return "TopicResponseDto{" +
                "topic=" + topic +
                ", replies=" + replies +
                '}';
    }
}
