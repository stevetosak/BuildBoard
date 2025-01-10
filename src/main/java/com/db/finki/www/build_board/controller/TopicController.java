package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.entity.user_types.BBUser;
import com.db.finki.www.build_board.entity.threads.Topic;
import com.db.finki.www.build_board.service.threads.impl.DiscussionService;
import com.db.finki.www.build_board.service.threads.impl.ThreadService;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;
    private final TagService tagService;
    private final DiscussionService discussionService;
    private final ThreadService threadService;

    public TopicController(TopicService topicService, TagService tagService, DiscussionService discussionService, ThreadService threadService) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.discussionService = discussionService;
        this.threadService = threadService;
    }

    @GetMapping("/create")
    public String getCreateTopicPage() {
        return "create-topic";
    }

    @GetMapping("/{topic-name}")
    public String showTopic(@PathVariable(name = "topic-name") String topicName, Model model) {
        Topic t = topicService.getByTitle(topicName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        model.addAttribute("replies", discussionService.findByTopic(t.getId()));
        return "show-topic";
    }

    @PostMapping("/add")
    public String createTopic(@RequestParam String title, @RequestParam String description, HttpSession session) {
        BBUser user = (BBUser) session.getAttribute("user");
        topicService.create(title, description, user);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTopic(@PathVariable(name = "id") long id, HttpSession session) {
        topicService.deleteTopicById(id);
        return "redirect:/";
    }

    @PostMapping("/add-tag/{id}")
    public String addTag(@PathVariable(name = "id") long id,
                         @RequestParam String tagName,
                         HttpSession session,
                         Model model) {
        Topic t = topicService.getById(id);
        topicService.addTagToTopic(t, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PostMapping("/edit/{id}")
    public String editTopic(@PathVariable long id, @RequestParam String title, @RequestParam String content, Model model) {
        Topic t = topicService.save(id, title, content);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PostMapping("/delete-tag/{topicId}/{tagName}")
    public String deleteTag(@PathVariable long topicId, @PathVariable String tagName, Model model) {
        Topic t = topicService.deleteTagFromTopic(topicId, tagName);
        model.addAttribute("topic", t);
        model.addAttribute("tags", tagService.findAllNotUsed(t));
        return "redirect:/topic/" + t.getTitle();
    }

    @PostMapping("/{topic-name}/reply/add")
    public String addReply(@PathVariable(name = "topic-name") String topicName, @RequestParam int parentId, @RequestParam String content, Model model, HttpSession session) {

        BBUser user = (BBUser) session.getAttribute("user");
        try {
            discussionService.create(content, parentId, user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/topic/" + topicName;
    }

    @PostMapping("/{topic-name}/reply/edit")
    public String editReply(@PathVariable(name = "topic-name") String topicName, @RequestParam int replyId, @RequestParam String content, Model model, HttpSession session) {
        discussionService.edit(replyId, content);
        return "redirect:/topic/" + topicName;
    }

    @PostMapping("/{topic-name}/discussion/like")
    public String like(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {

        BBUser user = (BBUser) session.getAttribute("user");
        threadService.rate(threadId,user.getId(),true);

        return "redirect:/topic/" + topicName;
    }
    @PostMapping("/{topic-name}/discussion/dislike")
    public String dislike(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {

        BBUser user = (BBUser) session.getAttribute("user");
        threadService.rate(threadId,user.getId(),false);

        return "redirect:/topic/" + topicName;
    }

    @PostMapping("/{topic-name}/discussion/delete")
    public String deleteDiscussion(@PathVariable(name = "topic-name") String topicName, @RequestParam int threadId, HttpSession session) {
        BBUser user = (BBUser) session.getAttribute("user");
        discussionService.delete(threadId);
        return "redirect:/topic/" + topicName;
    }


}
