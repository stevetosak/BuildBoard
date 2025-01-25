package com.db.finki.www.build_board.controller.thread_controllers.requests;

import com.db.finki.www.build_board.entity.threads.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/project/{pr-title}/requests")
public class ProjectRequestController {

    @GetMapping("")
    public String getRequestPage(
            Model model,
            @PathVariable(name = "pr-title") Project project
    ) {
        model.addAttribute("requests", project.getRequests());
        return "project_pages/requests/show-requests";
    }

    @PostMapping("/{req-id}/accept")
    public RedirectView acceptRequest(@PathVariable(name = "req-id") Integer reqId, @PathVariable(name = "pr-title") String projectTitle){
        return new RedirectView(
                String.format("/project/%s/requests", projectTitle)
        );
    }

    @PostMapping("/{req-id}/deny")
    public RedirectView denyRequest(@PathVariable(name = "req-id") Integer reqId, @PathVariable(name = "pr-title") String projectTitle){
        return new RedirectView(
                String.format("/project/%s/requests", projectTitle)
        );
    }
}
