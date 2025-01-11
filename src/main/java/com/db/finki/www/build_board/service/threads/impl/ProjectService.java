package com.db.finki.www.build_board.service.threads.impl;

import java.util.List;

import com.db.finki.www.build_board.entity.threads.BBThread;
import com.db.finki.www.build_board.entity.threads.Tag;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.repository.threads.ProjectRepository;
import com.db.finki.www.build_board.service.BBUserDetailsService;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TopicService topicService;
    private final TagService tagService;
    private final BBUserDetailsService userDetailsService;

    public ProjectService(ProjectRepository projectRepository, TopicServiceImpl topicService, TagServiceImpl tagService, BBUserDetailsService userDetailsService) {
        this.projectRepository = projectRepository;
        this.topicService = topicService;
        this.tagService = tagService;
        this.userDetailsService = userDetailsService;
    }

    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    public void create(String title, String repoUrl, String description, BBUser user) {
        projectRepository.save(
                new Project(title, repoUrl, description, user)
        );
    }

    public Project findByTitle(String title) {
        return projectRepository.findByTitleStartingWith(title);
    }

    public void addToProjecNewTopic(Project project, String title, String description, String username) {
        BBUser user = ((BBUser) userDetailsService.loadUserByUsername(username));
        Topic topic = topicService.create(title, description, user);
        topic.setParent(project);
        project.getTopics().add(topic);
        projectRepository.save(project);
    }

    public void addTagToProject(Project project, String tagName) {
        Tag tag = null ;
        try{
            tag=tagService.findByName(tagName);
        }catch (IllegalArgumentException ignore){
             tag=tagService.create(tagName);
        }
        project.getTags().add(tag);
        projectRepository.save(project);
    }

    public Project updateProject(Project project, String repoUrl, String description, String newTitle) {
        project.setRepoUrl(repoUrl);
        project.setDescription(description);
        project.setTitle(newTitle);
        return projectRepository.save(project);
    }

    public Project findById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public void removeTagFromProject(Project project, String tagName) {
        project.getTags().remove(tagService.findByName(tagName));
        projectRepository.save(project);
    }
}
