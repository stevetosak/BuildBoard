package com.db.finki.www.build_board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    @GetMapping()
    public String getProjectsPage(Model model) {
        return "project";
    }
    @GetMapping("{pid}")
    public String getProjectPage(@PathVariable Long pid, Model model) {
        return "project";
    }

}
