package com.db.finki.www.build_board.namedThread;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class NamedThreadDTO {
    @JsonFormat(pattern = "mm:HH dd:MM:YYYY")
    public LocalDateTime createdAt;
    public Creator creator;
    public Content content;
    public String threadType;

    @Data
    @AllArgsConstructor
    public static class Creator{
        public String username;
        public String logo;
    }

    @Data
    @AllArgsConstructor
    public static class Content{
        public String title;
        public String content;
        public List<String> tags;
    }
}
