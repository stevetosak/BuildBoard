package com.db.finki.www.build_board.service.user;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.UserRepository;
import com.db.finki.www.build_board.repository.thread.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStatisticsService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public UserStatisticsService(UserRepository userRepository, ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public List<Project> getAllActiveProjectsForUser(BBUser user) {
        return projectRepository.findAllByUserId(user.getId());
    }
}
