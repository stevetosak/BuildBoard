package com.db.finki.www.build_board.controller.thread_controller;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.entity.thread.Topic;
import com.db.finki.www.build_board.service.ReportService;
import com.db.finki.www.build_board.service.thread.impl.DiscussionService;
import com.db.finki.www.build_board.service.thread.itf.TagService;
import com.db.finki.www.build_board.service.thread.itf.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final TagService tagService;
    private final DiscussionService discussionService;
    private final String DUPLICATE_TITTLE = "There already exists a topic with title";
    private final ReportService reportService;

    public TopicController(TopicService topicService, TagService tagService, DiscussionService discussionService, ReportService reportService) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.discussionService = discussionService;
        this.reportService = reportService;
    }

    private String bootstartTopic(long topicId, Model model){
        Topic t = topicService.getById((long)topicId);

        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.getAllNotUsed(t));
        model.addAttribute("replies", discussionService.getByTopic(t.getId()));

        return "show-topic";
    }

    @GetMapping("/create")
    public String getCreateTopicPage(Model model, @RequestParam(required = false) Boolean duplicateTittle) {
        if (duplicateTittle != null)
            model.addAttribute("errMsg", "There already exists a thread with the same title in that parent");
        return "create-topic";
    }

    @GetMapping("/{topic-id}")
    public String showTopic(@PathVariable(name = "topic-id") int topicId, Model model,
            @RequestParam(required = false) Boolean duplicateTittle) {
        if (duplicateTittle != null) {
            model.addAttribute("errMsg", "There already exists a thread with the same title in that parent");
        }

        return bootstartTopic(topicId, model);
    }

    @PostMapping("/add")
    public String createTopic(@RequestParam String title, @RequestParam String description, HttpSession session,
            RedirectAttributes reddAttributes) {
        try {
            title = title.strip();
            BBUser user = (BBUser) session.getAttribute("user");
            topicService.create(title, description, user);
            return "redirect:/";
        } catch (org.springframework.orm.jpa.JpaSystemException e) {
            return handleDuplicatedTitle(e, reddAttributes, "/topics/create");
        }
    }

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/{id}/delete")
    public String deleteTopic(@PathVariable(name = "id") @P("id") long id, HttpSession session,
            @RequestParam @P("username") String username) {
        topicService.deleteTopicById(id);
        return "redirect:/";
    }

    @PreAuthorize("@topicServiceImpl.getById(#id).getUser().getUsername().equals(#username)")
    @PostMapping("/{id}/edit")
    public String editTopic(@PathVariable @P("id") long id, @RequestParam String title, @RequestParam String content,
            Model model, @RequestParam @P("username") String username, RedirectAttributes redirectAttributes) {
        Topic t = topicService.getById(id);
        String oldTitle = t.getTitle();
        try {
            title = title.strip();
            topicService.edit(t, title, content);
            model.addAttribute("topic", t);
            model.addAttribute("tags", tagService.getAllNotUsed(t));
            return "redirect:/topics/" + t.getId();
        } catch (org.springframework.orm.jpa.JpaSystemException e) {
            return handleDuplicatedTitle(e, redirectAttributes, "/topics/" + oldTitle);
        }
    }

    @PostMapping("{id}/report")
    public String reportUser(@PathVariable(name = "id") long topicId, @RequestParam String reason
            , @SessionAttribute BBUser user,
            @RequestParam(name = "report-username") String reportingUser, Model model){
        reportService.createReport(topicId,reason,user, reportingUser);

        return bootstartTopic(topicId, model);
    }

    public String handleDuplicatedTitle(org.springframework.orm.jpa.JpaSystemException e,
            RedirectAttributes attr, String redirectPath) {
        if (e.getMessage().contains(DUPLICATE_TITTLE)) {
            attr.addAttribute("duplicateTittle", true);
            return "redirect:" + redirectPath;
        }
        throw e;
    }

}
