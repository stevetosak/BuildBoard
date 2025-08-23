package com.db.finki.www.build_board.namedThread;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.thread.itf.NamedThread;
import com.db.finki.www.build_board.project.ProjectRepository;
import com.db.finki.www.build_board.repository.thread.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SearchService {
    private final TopicRepository topicRepository;
    private final FilterMap<Topic> topicFilterMap;
    private final FilterMap<Project> projectFilterMap;
    private final ProjectRepository projectRepository;

    public SearchService(TopicRepository topicRepository, FilterMap<Topic> TopicFilterMap, FilterMap<Project> ProjectFilterMap, ProjectRepository projectRepository) {
        this.topicRepository = topicRepository;
        this.topicFilterMap = TopicFilterMap;
        this.projectFilterMap = ProjectFilterMap;
        this.projectRepository = projectRepository;
    }

    private Page<Topic> searchTopics(String query, List<String> filters, Pageable pageable) {
        Specification<Topic> spec = Specification.where(null);
        for (String filter : filters) {
            System.out.println("FILTER: " + filter);
            spec = spec.or(topicFilterMap.getFilter(filter).apply(query));
        }
        return topicRepository.findAll(spec, pageable);
    }

    private Page<Project> searchProjects(String query, List<String> filters, Pageable pageable) {
        Specification<Project> spec = Specification.where(null);
        for (String filter : filters) {
            spec = spec.or(projectFilterMap.getFilter(filter).apply(query));
        }
        return projectRepository.findAll(spec, pageable);
    }

    public Page<NamedThread> search(String query, List<String> filters, String type, Pageable pageable) {
        List<NamedThread> results = new ArrayList<>();
        if(type == null){
            type = "all";
        }
        if(filters == null || filters.isEmpty()){
            filters = new ArrayList<>();
            filters.add("all");
        }
        long totalNumberOfElements = 0;

        if(Objects.equals(type, "project")){
            System.out.println("PROJECT");
            Page<Project> projects = searchProjects(query,
                    filters,
                    pageable);
            results.addAll(projects.getContent());
            totalNumberOfElements = projects.getTotalElements();
        } else if(Objects.equals(type, "topic")){
            Page<Topic> topics = searchTopics(query,
                    filters,
                    pageable);
            results.addAll(topics.getContent());
            totalNumberOfElements = topics.getTotalElements();
        } else {
            Page<Topic> topics = searchTopics(query,
                    filters,
                    pageable);

            totalNumberOfElements += topics.getTotalElements();
            results.addAll(topics.getContent());

            Page<Project> projects = searchProjects(query,
                    filters,
                    pageable);

            totalNumberOfElements+=projects.getTotalElements();
            results.addAll(projects.getContent());
        }

        return new PageImpl<>(results, pageable, totalNumberOfElements);
    }
}
