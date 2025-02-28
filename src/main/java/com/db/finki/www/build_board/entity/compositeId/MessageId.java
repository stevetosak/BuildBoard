package com.db.finki.www.build_board.entity.compositeId;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class MessageId {
   private String name;
   private int project;
   private int sentBy;
   private LocalDateTime sentAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageId messageId = (MessageId) o;
        return project == messageId.project && sentBy == messageId.sentBy && Objects.equals(name, messageId.name) && Objects.equals(sentAt, messageId.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, project, sentBy, sentAt);
    }
}
