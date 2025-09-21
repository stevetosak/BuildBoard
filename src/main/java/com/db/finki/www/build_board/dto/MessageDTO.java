package com.db.finki.www.build_board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MessageDTO {
    private String channelName;
    private String content;
    private String senderUsername;
    private LocalDateTime sentAt;
    private Integer projectId;
    private String avatarUrl;
}
