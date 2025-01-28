package com.db.finki.www.build_board.service.thread.impl;

import java.util.List;

import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.repository.thread.ProjectRepository;
import com.db.finki.www.build_board.service.BBUserDetailsService;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import jakarta.transaction.Transactional;
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

    public Project getByTitle(String title) {
        return projectRepository.findByTitleStartingWith(title);
    }

    public void createTopic(Project project, String title, String description, String username) {
        BBUser user = ((BBUser) userDetailsService.loadUserByUsername(username));
        Topic topic = topicService.create(title, description, user);
        topic.setParent(project);
        project.getTopics().add(topic);
        projectRepository.save(project);
    }

    @Transactional
    public void deleteMember(Project project, int memberId) {
        BBUser user = userDetailsService.loadUserById(memberId);
        boolean removed = project.getDevelopers().remove(user);
        System.out.println("REMOVED: " + removed);
    }

    public void addTag(Project project, String tagName) {
        Tag tag = null ;
        try{
            tag=tagService.getByName(tagName);
        }catch (IllegalArgumentException ignore){
             tag=tagService.create(tagName);
        }
        project.getTags().add(tag);
        projectRepository.save(project);
    }

    public Project update(Project project, String repoUrl, String description, String newTitle) {
        project.setRepoUrl(repoUrl);
        project.setDescription(description);
        project.setTitle(newTitle);
        return projectRepository.save(project);
    }

    public Project getById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }

    public void deleteTag(Project project, String tagName) {
        project.getTags().remove(tagService.getByName(tagName));
        projectRepository.save(project);
    }
}
