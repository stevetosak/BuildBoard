package com.db.finki.www.build_board.service.threads.impl;

import java.util.List;

import com.db.finki.www.build_board.entity.threads.Tag;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.repository.threads.ProjectRepository;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TopicService topicService;
    private final TagService tagService;

    public ProjectService(ProjectRepository projectRepository, TopicServiceImpl topicService, TagServiceImpl tagService) {
        this.projectRepository = projectRepository;
        this.topicService = topicService;
        this.tagService = tagService;
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

    public void addToProjectWithIdNewTopic(String projectTitle, String title, String description, BBUser user) {
        Project project = findByTitle(projectTitle);
        project.getTopics().add(
                topicService.create(title, description, user)
        );
        projectRepository.save(project);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public void addTagToProjectWithTitle(String title, String tagName) {
        Project project = findByTitle(title);
        Tag tag = null ;
        try{
            tag=tagService.findByName(tagName);
        }catch (IllegalArgumentException ignore){
             tag=tagService.create(tagName);
        }
        project.getTags().add(tag);
        projectRepository.save(project);
    }

    public Project updateProject(String projectTitle, String repoUrl, String description, String newTitle) {
        Project project = findByTitle(projectTitle);
        project.setRepoUrl(repoUrl);
        project.setDescription(description);
        project.setTitle(newTitle);
        return projectRepository.save(project);
    }

    public void removeTagFromProjectWithTitle(String projectTitle, String tagName) {
        final Project project = findByTitle(projectTitle);
        project.getTags().remove(tagService.findByName(tagName));
        projectRepository.save(project);
    }
}
