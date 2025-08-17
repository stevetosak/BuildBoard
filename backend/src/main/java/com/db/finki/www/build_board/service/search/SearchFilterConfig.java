package com.db.finki.www.build_board.service.search;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchFilterConfig {

    @Bean
    public FilterMap<Project> ProjectFilterMap() {
        return new FilterMap<>();
    }

    @Bean
    public FilterMap<Topic> TopicFilterMap() {
        return new FilterMap<>();
    }
}
