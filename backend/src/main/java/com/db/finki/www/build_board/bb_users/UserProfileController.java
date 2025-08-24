package com.db.finki.www.build_board.bb_users;

import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.user.BBUserDetailsService;
import com.db.finki.www.build_board.service.request.ProjectRequestService;
import com.db.finki.www.build_board.service.util.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("api/users")
public class UserProfileController {
    private final BBUserDetailsService userService;
    private final FileUploadService fileUploadService;
    private final ProjectRequestService projectRequestService;
    private final BBUserProfile.BBUserProfileBuilder bbUserProfileBuilder;

    public UserProfileController(BBUserDetailsService userService, FileUploadService fileUploadService, ProjectRequestService projectRequestService) {
        this.userService = userService;
        this.fileUploadService = fileUploadService;
        this.projectRequestService = projectRequestService;
        this.bbUserProfileBuilder = BBUserProfile.builder();
    }

    @GetMapping("{username}")
    public ResponseEntity<BBUserProfile> getUserProfile(@PathVariable("username") String username) {
        BBUser user = (BBUser) userService.loadUserByUsername(username);
        List<BBUserProfile.ProjectDto> projectDtos = new ArrayList<>();
        List<BBUserProfile.TopicDto> topicDtos = new ArrayList<>();

        user.getInterested().forEach(interested -> {
            if (interested.getClass().equals(Topic.class)) {
                Topic topic = (Topic) interested;
                topicDtos.add(new BBUserProfile.TopicDto(topic.getTitle()));
            } else if (interested.getClass().equals(Project.class)) {
                Project project = (Project) interested;
                projectDtos.add(new BBUserProfile.ProjectDto(project.getTitle()));
            }
        });

        return ResponseEntity.ok(
                bbUserProfileBuilder.username(user.getUsername()).interested(
                        new BBUserProfile.Interested(topicDtos, projectDtos)
                                                                            ).friends(user.getFriends().stream().map(f -> new BBUserProfile.BBUserMinProfile(f.getUsername(), f.getAvatarUrl())).toList()).build()
                                );
    }
}
