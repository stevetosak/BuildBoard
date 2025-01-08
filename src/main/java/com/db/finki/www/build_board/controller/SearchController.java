package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.entity.threads.interfaces.NamedThread;
import com.db.finki.www.build_board.service.search.SearchService;
import com.db.finki.www.build_board.service.threads.itfs.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private final TopicService topicService;
    private final SearchService searchService;

    public SearchController(TopicService topicService, SearchService searchService) {
        this.topicService = topicService;
        this.searchService = searchService;
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, @RequestParam(required = false) List<String> filters, @RequestParam String type , Model model, RedirectAttributes redirectAttributes) {
        if(filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
            filters.add("all");
        }
        model.addAttribute("threads", searchService.search(query,filters,type));
        return "/?";
    }
}
