package com.db.finki.www.build_board.rest;

import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ChannelService channelService;

    public ProjectController(ProjectService projectService, ChannelService channelService) {
        this.projectService = projectService;
        this.channelService = channelService;
    }

    @GetMapping("{id}/channels/{channelName}")
    public void getChannel(@PathVariable Long id, @PathVariable String channelName) {
        Project project = projectService.getById(id);
        Channel channel = channelService.getByNameAndProject(channelName, project);
    }
}
