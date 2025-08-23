package com.db.finki.www.build_board.rest.dto;

import com.db.finki.www.build_board.constants.enums.ChannelMessageEventType;
import com.db.finki.www.build_board.dto.channel.MessageDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChannelMessageEvent {
    @JsonProperty(required = true)
    ChannelMessageEventType type;
    @JsonProperty(required = true)
    MessageDTO payload;
}
