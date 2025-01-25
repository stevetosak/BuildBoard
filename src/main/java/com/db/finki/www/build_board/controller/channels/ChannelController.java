package com.db.finki.www.build_board.controller.channels;

import com.db.finki.www.build_board.entity.channels.Channel;
import com.db.finki.www.build_board.entity.threads.Project;
import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.service.channel.ChannelService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/project/{title}/channels")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping()
    public String getChannels(@PathVariable("title") Project project, Model model) {
        List<Channel> channels = channelService.getAllChannelsForProject(project);
        model.addAttribute("channels", channels);
        return "channels/list-channels";
    }

    @PreAuthorize("#project.getDevelopers().contains(#user)")
    @GetMapping("/{channelName}")
    public String getChannel(@PathVariable String channelName, @PathVariable("title") @P("project") Project project,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @SessionAttribute @P("user") BBUser user) {
        Channel c = (Channel) redirectAttributes.getAttribute("channel");
        if (c == null) {
            c = channelService.getByNameAndProject(channelName, project);
            model.addAttribute("channel", c);
        } else {
            model.addAttribute("channel", c);
        }

        return "channels/view-channel";
    }

    @PreAuthorize("@channelService.getByNameAndProject(#channelName,#project).getDeveloper().equals(#user)")
    @PostMapping("/{channelName}/delete")
    public String deleteChannel(@PathVariable @P("channelName") String channelName, @PathVariable("title") @P("project") Project project,
                                @SessionAttribute @P("user") BBUser user, RedirectAttributes redirectAttributes) {
        channelService.deleteChannel(channelName, project);
        return "redirect:/project/" + project.getTitle();
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

        return "redirect:/project/" + project.getTitle();
    }

}
