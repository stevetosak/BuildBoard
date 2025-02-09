package com.db.finki.www.build_board.service.thread.impl;

import java.util.List;

import com.db.finki.www.build_board.entity.thread.Tag;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.repository.thread.ProjectRepository;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
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
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, TopicServiceImpl topicService, TagServiceImpl tagService, BBUserDetailsService userDetailsService, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.topicService = topicService;
        this.tagService = tagService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
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

    public void addDeveloperToProject(Project project, BBUser user) {
        projectRepository.addUserToProject(project.getId(),user.getId());
    }

    @Transactional
    public void kickMember(Project project, int memberId) {
        projectRepository.removeUserFromProject(project.getId(),memberId);
    }
    public List<BBUser> getAllDevelopersForProject(Project project) {
        return userRepository.findAllActiveDevelopersForProject(project.getId());
    }

    public void addTag(Project project, String tagName, BBUser user) {
        Tag tag = null ;
        try{
            tag=tagService.getByName(tagName);
        }catch (IllegalArgumentException ignore){
             tag=tagService.create(tagName,user);
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
