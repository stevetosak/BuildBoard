package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.service.search.SearchService;
import com.db.finki.www.build_board.service.threads.impl.NamedThreadService;
import com.db.finki.www.build_board.service.threads.itfs.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomePageController {
    private final SearchService searchService;
    private final TagService tagService;

    public HomePageController(SearchService searchService, TagService tagService) {
        this.searchService = searchService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String search( @RequestParam(required = false)String query, @RequestParam(required = false) List<String> filters,  @RequestParam(required = false) String type , Model model) {
        if(filters == null || filters.isEmpty()) {
            filters = new ArrayList<>();
            filters.add("all");
        }

        model.addAttribute("threads", searchService.search(query,filters,type));

        System.out.println("TAGS: " + tagService.findAll());
        model.addAttribute("tags",tagService.findAll());
        return "home";
    }
}
