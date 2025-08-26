package com.db.finki.www.build_board.bb_users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.util.List;


@Builder
public class BBUserProfileDTO {
    public String username;
    public Interested interested;
    public List<BBUserMinProfile> friends;

    @AllArgsConstructor
    public static class BBUserMinProfile{
        public String username;
        public String logo;
    }

    @AllArgsConstructor
    public static class ProjectDto{
        public String name;
    }

    @AllArgsConstructor
    public static class TopicDto{
        public String name;
    }

    @Setter
    @AllArgsConstructor
    public static class Interested {
       public  List<TopicDto> topics;
       public  List<ProjectDto> projects;
    }
}
