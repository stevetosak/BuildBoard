package com.db.finki.www.build_board.controller.thread_controller.request;

import com.db.finki.www.build_board.entity.entity_enum.Status;
import com.db.finki.www.build_board.entity.thread.Project;
import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.service.request.ProjectRequestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

//TODO: filter za najnov request spored user i po mozhnost po user da se searchvit
//TODO: da se prikazvit date na requestot

@Controller
@RequestMapping("/projects/{pr-title}/requests")
public class ProjectRequestController {

    private final ProjectRequestService projectRequestService;

    public ProjectRequestController(ProjectRequestService projectService) {
        this.projectRequestService = projectService;
    }

    @GetMapping("")
    public String getRequestPage(
            Model model,
            @PathVariable(name = "pr-title") Project project,
            @RequestParam(required = false) Status status
    ) {
        model.addAttribute("project", project);
        model.addAttribute("requests", projectRequestService.getByStatusAndProject(status,project));
        model.addAttribute("status", Status.values());
        return "project_pages/requests/show-requests";
    }

    @PostMapping("/{req-id}/accept")
    @PreAuthorize("#project.getUser().getId()==#user.getId()")
    public RedirectView acceptRequest(@PathVariable(name = "req-id") Integer reqId,
                                      @PathVariable(name = "pr-title") @P("project") Project project,
                                      @RequestParam(name = "feedback-desc") String feedbackDesc,
                                      @SessionAttribute @P("user") BBUser user
    ) {
        projectRequestService.accept(feedbackDesc,user,reqId);
        return new RedirectView(
                String.format("/projects/%s/requests", project.getTitle())
        );
    }

    @PostMapping("/{req-id}/deny")
    @PreAuthorize("#project.getUser().getId()==#user.getId()")
    public RedirectView denyRequest(@PathVariable(name = "req-id") Integer reqId,
                                    @PathVariable(name = "pr-title") @P("project") Project project,
                                    @RequestParam(name = "feedback-desc") String feedbackDesc,
                                    @SessionAttribute @P("user") BBUser user
    ) {
        projectRequestService.deny(reqId, feedbackDesc, user);
        return new RedirectView(
                String.format("/projects/%s/requests", project.getTitle())
        );
    }

    @PostMapping("/create")
    public RedirectView createRequestToJoin(
            @PathVariable(name = "pr-title") Project project,
            @RequestParam(name = "reason-desc") String reason,
            @SessionAttribute BBUser user
    ){
        projectRequestService.createRequestFor(project,reason,user);
        return new RedirectView("/projects/"+project.getTitle());
    }
}
