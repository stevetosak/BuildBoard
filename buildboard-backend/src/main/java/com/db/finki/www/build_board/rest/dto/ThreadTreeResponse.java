package com.db.finki.www.build_board.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ThreadTreeResponse {
    @JsonProperty
    ThreadDto root;
    @JsonProperty
    List<ThreadDto> children;
    @JsonProperty
    String type;

    @Override
    public String toString() {
        return "TopicResponseDto{" +
                "topic=" + root +
                ", replies=" + children +
                '}';
    }
}
