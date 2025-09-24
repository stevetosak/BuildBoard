package com.db.finki.www.build_board.entity.compositeId;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class MessageId {
    private UUID channel;
    private int sentBy;
    private LocalDateTime sentAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MessageId messageId = (MessageId) o;
        return sentBy == messageId.sentBy && Objects.equals(channel, messageId.channel) && Objects.equals(sentAt, messageId.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channel, sentBy, sentAt);
    }
}
