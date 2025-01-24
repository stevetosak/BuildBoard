package com.db.finki.www.build_board.entity.compositeId;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Getter
@Service
public class ChannelId {
    private int project;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelId channelId = (ChannelId) o;
        return project == channelId.project && Objects.equals(name, channelId.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(project, name);
    }
}
