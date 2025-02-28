package com.db.finki.www.build_board.service.search;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import com.db.finki.www.build_board.repository.thread.ProjectRepository;
import com.db.finki.www.build_board.repository.thread.TopicRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SearchServiceImpl implements SearchService {
    private final TopicRepository topicRepository;
    private final FilterMap<Topic> topicFilterMap;
    private final FilterMap<Project> projectFilterMap;
    private final ProjectRepository projectRepository;

    public SearchServiceImpl(TopicRepository topicRepository, FilterMap<Topic> TopicFilterMap, FilterMap<Project> ProjectFilterMap, ProjectRepository projectRepository) {
        this.topicRepository = topicRepository;
        this.topicFilterMap = TopicFilterMap;
        this.projectFilterMap = ProjectFilterMap;
        this.projectRepository = projectRepository;
    }



    private List<Topic> searchTopics(String query, List<String> filters) {
        Specification<Topic> spec = Specification.where(null);
        for (String filter : filters) {
            System.out.println("FILTER: " + filter);
            spec = spec.or(topicFilterMap.getFilter(filter).apply(query));
        }
        return topicRepository.findAll(spec);
    }

    private List<Project> searchProjects(String query, List<String> filters) {
        Specification<Project> spec = Specification.where(null);
        for (String filter : filters) {
            spec = spec.or(projectFilterMap.getFilter(filter).apply(query));
        }
        return projectRepository.findAll(spec);
    }

    public List<NamedThread> search(String query, List<String> filters,String type) {
        List<NamedThread> result = new ArrayList<>();
        if(type == null){
            type = "all";
        }
        if(filters == null || filters.isEmpty()){
            filters = new ArrayList<>();
            filters.add("all");
        }

        if(Objects.equals(type, "project")){
            System.out.println("PROJECT");
            result.addAll(searchProjects(query, filters));
        } else if(Objects.equals(type, "topic")){
            result.addAll(searchTopics(query, filters));
            System.out.println("TOPIC");
        } else {
            result.addAll(searchTopics(query, filters));
            result.addAll(searchProjects(query, filters));
            System.out.println("ALL");
        }



        return result;
    }
}
