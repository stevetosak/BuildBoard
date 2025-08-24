package com.db.finki.www.build_board.dto.channel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageDTO {
    @JsonProperty
    private String channelName;
    @JsonProperty
    private String content;
    @JsonProperty
    private String senderUsername;
    @JsonProperty
    private Instant sentAt;
    @JsonProperty
    private String projectName;
    @JsonProperty
    private String avatarUrl;
}
