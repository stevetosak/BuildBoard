package com.db.finki.www.build_board.controller.channel;

import com.db.finki.www.build_board.entity.channel.Channel;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.mapper.MessageMapper;
import com.db.finki.www.build_board.service.channel.ChannelService;
import com.db.finki.www.build_board.service.channel.MessageService;
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

    public ChannelController(ChannelService channelService, MessageMapper messageMapper, MessageService messageService) {
        this.channelService = channelService;
        this.messageMapper = messageMapper;
        this.messageService = messageService;
    }

    @GetMapping()
    public String getChannels(@PathVariable("title") Project project, Model model) {
        List<Channel> channels = channelService.getAllChannelsForProject(project);
        model.addAttribute("channels", channels);
        return "channels/list-channels";
    }

    @PreAuthorize("#project.getDevelopers().contains(#user)")
    @GetMapping("/{channelName}")
    public String getChannel(@PathVariable String channelName,
                             @PathVariable("title") @P("project") Project project,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @SessionAttribute @P("user") BBUser user
    ) {
        Channel c = (Channel) redirectAttributes.getAttribute("channel");
        if (c == null) {
            c = channelService.getByNameAndProject(channelName, project);
            model.addAttribute("channel", c);
            model.addAttribute("messages", messageMapper.toDTO(
                    messageService.getAllMessagesForProjectChannel(project.getId(), channelName)));
        } else {
            model.addAttribute("channel", c);
        }

        return "channels/show-channel";
    }

    @PreAuthorize("@channelService.getByNameAndProject(#channelName,#project).getDeveloper().equals(#user)")
    @PostMapping("/{channelName}/delete")
    public String deleteChannel(@PathVariable @P("channelName") String channelName, @PathVariable("title") @P("project") Project project,
                                @SessionAttribute @P("user") BBUser user, RedirectAttributes redirectAttributes) {
        channelService.deleteChannel(channelName, project);
        return "redirect:/projects/" + project.getTitle();
    }

    @PreAuthorize("#project.getDevelopers().contains(#user)")
    @PostMapping("/add")
    public String add(@PathVariable("title") @P("project") Project project, @RequestParam String channelName, @RequestParam String channelDescription, @SessionAttribute @P("user") BBUser user, RedirectAttributes redirectAttributes) {
        try {
            Channel channel = channelService.create(project, channelName, channelDescription, user);
            redirectAttributes.addFlashAttribute("channel", channel);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/projects/" + project.getTitle();
    }

}
