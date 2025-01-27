package com.db.finki.www.build_board.dto.channel;

import com.db.finki.www.build_board.entity.channels.Message;
import com.db.finki.www.build_board.mappers.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String channelName;
    private String content;
    private String senderUsername;
    private LocalDateTime sentAt;
    private Integer projectId;
    private String avatarUrl;
}
