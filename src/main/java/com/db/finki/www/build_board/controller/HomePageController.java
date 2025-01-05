package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.service.threads.ThreadsWithTittleService;
import com.db.finki.www.build_board.service.threads.TopicsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final ThreadsWithTittleService threadsWithTittleService;

    public HomePageController(ThreadsWithTittleService threadsWithTittleService) {
        this.threadsWithTittleService = threadsWithTittleService;
    }

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("threads",threadsWithTittleService.findAll());
        return "home";
    }
}
