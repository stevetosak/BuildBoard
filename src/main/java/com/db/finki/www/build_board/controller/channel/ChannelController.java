package com.db.finki.www.build_board.controller.channel;

import com.db.finki.www.build_board.entity.access_managment.Permission;
import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.service.access_managment.ProjectAccessManagementService;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.channel.MessageService;
import com.db.finki.www.build_board.service.thread.impl.ProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/projects/{title}/channels")
public class ChannelController {
    private final ChannelService channelService;
    private final MessageMapper messageMapper;
    private final MessageService messageService;
    private final ProjectService projectService;
    private final ProjectAccessManagementService projectAccessManagementService;

    public ChannelController(
            ChannelService channelService, MessageMapper messageMapper,
            MessageService messageService, ProjectService projectService,
            ProjectAccessManagementService projectAccessManagementService
                            ) {
        this.channelService = channelService;
        this.messageMapper = messageMapper;
        this.messageService = messageService;
        this.projectService = projectService;
        this.projectAccessManagementService = projectAccessManagementService;
    }

    private void checkIfAuthorized(
            Model model,
            Channel channel,
            int userId,
            int projectId,
            String permission
                                  ) {
        if (!projectAccessManagementService.hasPermissionToAccessResource(userId,
                permission,
                channel
                        .getProjectResource()
                        .getId(),
                projectId
                                                                         )) {
            System.out.println("vleze deny access");
            model.addAttribute("error",
                    "You dont have permission to access this channel");
            throw new RuntimeException("Unauthorized");
        }
    }

    @GetMapping()
    public String getChannels(@PathVariable("title") Project project, Model model) {
        List<Channel> channels = channelService.getAllChannelsForProject(project);
        model.addAttribute("channels",
                channels);
        return "channels/list-channels";
    }

    @PreAuthorize("@projectService.getAllDevelopersForProject(#project).contains(#user)")
    @GetMapping("/{channelName}")
    public String getChannel(
            @PathVariable String channelName,
            @PathVariable("title") @P("project") Project project,
            Model model,
            RedirectAttributes redirectAttributes,
            @SessionAttribute @P("user") BBUser user
                            ) {
        Channel c = (Channel) redirectAttributes.getAttribute("channel");

        if (c == null) {
            c = channelService.getByNameAndProject(channelName,
                    project);
            model.addAttribute("channel",
                    c);
            model.addAttribute("messages",
                    messageMapper.toDTO(
                            messageService.getAllMessagesForProjectChannel(project.getId(),
                                    channelName)));
            model.addAttribute("developers",
                    projectService.getAllDevelopersForProject(project));
        } else {
            model.addAttribute("channel",
                    c);
        }

        try {
            checkIfAuthorized(model,
                    c,
                    user.getId(),
                    project.getId(),
                    Permission.READ
                             );
            boolean canWrite = projectAccessManagementService
                    .hasPermissionToAccessResource(user.getId(),
                            Permission.WRITE,
                            c
                                    .getProjectResource()
                                    .getId(),
                            project.getId());
            model.addAttribute("canWrite",
                    canWrite);

            return "channels/show-channel";
        } catch (RuntimeException e) {
            if(e.getMessage().contains("Unauthorized")) {
                return "redirect:/projects/" + project.getId();
            }
            throw e;
        }
    }

    @PreAuthorize("@channelService.getByNameAndProject(#channelName,#project).getDeveloper()" +
            ".equals(#user)")
    @PostMapping("/{channelName}/delete")
    public String deleteChannel(
            @PathVariable @P("channelName") String channelName, @PathVariable("title") @P(
                    "project") Project project,
            @SessionAttribute @P("user") BBUser user,
            RedirectAttributes redirectAttributes, Model model
                               ) {
        Channel c = channelService.getByNameAndProject(channelName,
                project);

        try{
            checkIfAuthorized(model,c,user.getId(),project.getId(),Permission.DELETE);

            channelService.deleteChannel(channelName,
                    project);
            return "redirect:/projects/" + project.getTitle();
        }catch (RuntimeException e) {
            if(e.getMessage().contains("Unauthorized")) {
                return "redirect:/projects/" + project.getId();
            }
            throw e;
        }
    }

    @PreAuthorize("@projectService.getAllDevelopersForProject(#project).contains(#user)")
    @PostMapping("/add")
    public String add(
            @PathVariable("title") @P("project") Project project,
            @RequestParam String channelName, @RequestParam String channelDescription,
            @SessionAttribute @P("user") BBUser user, RedirectAttributes redirectAttributes,
            Model model
                     ) {
        try {
            Channel channel = channelService.create(project,
                    channelName,
                    channelDescription,
                    user);

            try{
                checkIfAuthorized(model,channel,user.getId(),project.getId(),Permission.CREATE);
                redirectAttributes.addFlashAttribute("channel",
                        channel);
            }catch (RuntimeException e) {
                if(e.getMessage().contains("Unauthorized")) {
                    return "redirect:/projects/" + project.getId();
                }
                throw e;
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    e.getMessage());
        }

        return "redirect:/projects/" + project.getTitle();
    }

}
