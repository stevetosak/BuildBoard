package com.db.finki.www.build_board.controller;

import com.db.finki.www.build_board.service.threads.NamedThreadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final NamedThreadService namedThreadService;

    public HomePageController(NamedThreadService namedThreadService) {
        this.namedThreadService = namedThreadService;
    }

    @GetMapping
    public String homePage(Model model) {
        model.addAttribute("threads", namedThreadService.findAll());
        return "home";
    }
}
