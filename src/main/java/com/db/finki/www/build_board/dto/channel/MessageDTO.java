package com.db.finki.www.build_board.dto.channel;

import com.db.finki.www.build_board.entity.channels.Message;
import com.db.finki.www.build_board.mappers.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String channelName;
    private String content;
    private String senderUsername;
    private LocalDateTime sentAt;
    private Integer projectId;
    private String avatarUrl;

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getChannelName() {
        return channelName;
    }

    public String getContent() {
        return content;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
