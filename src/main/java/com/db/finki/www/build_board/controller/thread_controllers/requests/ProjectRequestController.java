package com.db.finki.www.build_board.controller.thread_controllers.requests;

import com.db.finki.www.build_board.entity.threads.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/{pr_title}/requests")
public class ProjectRequestController {
    @GetMapping("/")
    public String getRequestPage(
            Model model,
            @PathVariable(name = "pr_title") Project project
    ) {
        model.addAttribute("requests", project.getRequests());
        return "project_pages/requests/show-requests";
    }
}
